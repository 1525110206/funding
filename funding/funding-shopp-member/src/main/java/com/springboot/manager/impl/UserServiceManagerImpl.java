package com.springboot.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.springboot.api.BaseApiService;
import com.springboot.constants.MQInterfaceType;
import com.springboot.constants.TimeConstants;
import com.springboot.dao.QuestionMapper;
import com.springboot.dao.UserMapper;
import com.springboot.entity.Page;
import com.springboot.entity.UserEntity;
import com.springboot.manager.UserServiceManager;
import com.springboot.mq.producer.RegisterMailboxProducer;
import com.springboot.redis.BaseRedisService;
import com.springboot.token.TokenUtils;
import com.springboot.utils.DateUtils;
import com.springboot.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class UserServiceManagerImpl extends BaseApiService implements UserServiceManager {
    @Autowired
    private UserMapper userMapper;


    @Autowired
    private QuestionMapper questionMapper;

    @Value("${messages.queue}")
    private String MESSAGES_QUEUE;
    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;


    @Autowired
    private TokenUtils tokenUtils;

    public UserServiceManagerImpl() {
    }



    @Override
    public void regist(UserEntity userEntity) {
        userEntity.setCreated(DateUtils.getTimestamp());
        userEntity.setUpdated(DateUtils.getTimestamp());
        String phone = userEntity.getPhone();
        String password = userEntity.getPassword();
        userEntity.setPassword(md5PassSalt(phone, password));
        userMapper.saveUser(userEntity);
        //队列
        Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
        //组装报文格式
        String mailMessage = mailMessage(userEntity.getEmail(), userEntity.getUsername());
        log.info("####regist() 注册发送邮件报文mailMessage：{}", mailMessage);
        registerMailboxProducer.send(activeMQQueue, mailMessage);

    }


    @Override
    public String md5PassSalt(String phone, String password) {
        String newPass = MD5Util.MD5(phone + password);
        return newPass;
    }

    @Override
    public void sendauthcode(String authcode, String email) {
        //队列
        Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
        //组装报文格式
        String mailMessage = authcodeMessage(authcode, email);
        log.info("####regist() 注册发送邮件报文mailMessage：{}", mailMessage);
        registerMailboxProducer.send(activeMQQueue, mailMessage);
    }

    private String authcodeMessage(String authcode, String email){
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", MQInterfaceType.SMS_AUTH);
        JSONObject content = new JSONObject();
        content.put("mail", email);
        content.put("authcode", authcode);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }


    @Override
    public Map<String, Object> login(UserEntity userEntity) {

        String phone = userEntity.getPhone();
        String password = userEntity.getPassword();
        String newPassword = md5PassSalt(phone, password);
        UserEntity userPhoneAndPwd = userMapper.getUserPhoneAndPwd(phone, newPassword);
        System.out.println(newPassword);
        if(userPhoneAndPwd == null){
            return setResutError("账户或者密码错误");
        }
        //生成对应的token
        String token = tokenUtils.getToken();
        Long userId = userPhoneAndPwd.getId();
        baseRedisService.set(token, userId + "", TimeConstants.USER_TOKEN_TERMVALIDITY);

        return setResutSuccessData(token);

    }

    public Map<String, Object> getUser(String token) {
        //从redis中找到userid
        String userId = baseRedisService.get(token);

        if(StringUtils.isEmpty(userId)){
            return setResutError("用户已经过期");
        }

        Long newuserId1 = Long.parseLong(userId);
        UserEntity userInfo = userMapper.getUserInfo(newuserId1);
        userInfo.setPassword(null);
        return setResutSuccessData(userInfo);
    }



    private String mailMessage(String email, String userName){
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", MQInterfaceType.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("mail", email);
        content.put("userName", userName);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }

    public Page queryPage(Map<String, Object> paramMap) {
        Page page = new Page();

        Integer pageno = (Integer) paramMap.get("pageno");
        Integer pageSize = (Integer) paramMap.get("pageSize");



        if(pageno <= 0){
            page.setPageno(1);
        }else{
            page.setPageno(pageno);
        }
        if(pageSize <= 0){
            page.setPageSize(10);
        }else{
            page.setPageSize(pageSize);
        }
        Integer startIndex = (pageno-1)*pageSize;
        paramMap.put("startIndex", startIndex);
        List<UserEntity> datas = userMapper.queryList(paramMap);
        page.setDatas(datas);


        Integer count = userMapper.queryCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }

    @Override
    public int deleteByPrimaryKey(Map<String, Integer> paramMap) {
        Integer id = paramMap.get("id");
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateAcctType(UserEntity userEntity) {
        userMapper.updateAcctType(userEntity);
    }

    @Override
    public void updateBasicinfo(UserEntity userEntity) {
        userMapper.updateBasicinfo(userEntity);
    }

    @Override
    public void updateEmail(UserEntity userEntity) {
        userMapper.updateEmail(userEntity);
    }

    @Override
    public int queryPhoneCount(UserEntity userEntity) {
        return userMapper.queryPhoneCount(userEntity);
    }

    @Override
    public UserEntity getMember(Integer id) {
        return userMapper.getUserInfo(Long.valueOf(id));
    }


    @Override
    public void insert(UserEntity userEntity) {

        for(int i = 0; i < 100; i ++){
            userEntity.setCreated(DateUtils.getTimestamp());
            userEntity.setUpdated(DateUtils.getTimestamp());
            userEntity.setAuthstatus("0");
            userEntity.setUsertype("0");
            userEntity.setPhone("test0"+ i);
            userEntity.setPassword("123456");
            userEntity.setEmail("13450583789@163.com");
            userEntity.setUsername("test1" + i);
            userMapper.saveUser(userEntity);
        }
    }

    @Override
    public void updateEmailAndUsername(UserEntity userEntity) {
        userEntity.setUpdated(DateUtils.getTimestamp());
        userMapper.updateEmailAndUsername(userEntity);
    }

    @Override
    public UserEntity getMemberById(Integer memberid) {
        return userMapper.getMemberById(memberid);
    }

    @Override
    public List<Map<String, Object>> queryCertByMemberid(Integer memberid) {
        return userMapper.queryCertByMemberid(memberid);
    }

    @Override
    public Page getMemberByAuth(Map<String, Object> paramMap) {
        Page page = new Page();

        Integer pageno = (Integer) paramMap.get("pageno");
        Integer pageSize = (Integer) paramMap.get("pageSize");



        if(pageno <= 0){
            page.setPageno(1);
        }else{
            page.setPageno(pageno);
        }
        if(pageSize <= 0){
            page.setPageSize(10);
        }else{
            page.setPageSize(pageSize);
        }
        Integer startIndex = (pageno-1)*pageSize;
        paramMap.put("startIndex", startIndex);
        List<UserEntity> datas = userMapper.getMemberByAuth(paramMap);
        page.setDatas(datas);


        Integer count = userMapper.queryMemberByAuthCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }

    @Override
    public void updateAuthstatus(UserEntity userEntity) {
        userMapper.updateAuthstatus(userEntity);
    }

}
