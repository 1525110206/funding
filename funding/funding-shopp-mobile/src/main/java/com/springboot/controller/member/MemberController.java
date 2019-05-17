package com.springboot.controller.member;

import com.springboot.constants.TimeConstants;
import com.springboot.entity.*;
import com.springboot.feign.ManagerFeign;
import com.springboot.feign.TicketFeign;
import com.springboot.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
public class MemberController {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private TicketFeign ticketFeign;
    @Autowired
    private ManagerFeign managerFeign;


    @RequestMapping("/accttype.html")
    public String accttype(){
        return "member/accttype";
    }

    @RequestMapping("/member/basicinfo.html")
    public String basicinfo(){
        return "member/basicinfo";
    }

    @RequestMapping("/member/uploadCert.html")
    public String uploadCert(){
        return "member/uploadCert";
    }

    @RequestMapping("/member/checkemail.html")
    public String checkemail(){
        return "member/checkemail";
    }

    @RequestMapping("/member/checkauthcode.html")
    public String checkauthcode(){
        return "member/checkauthcode";
    }

    @RequestMapping("/member/tomemberanswer.html")
    public String tomemberanswer(){
        return "member/memberanswer";
    }

    @RequestMapping("/member/toMemberBaseInfo.html")
    public String toMemberBaseInfo(){
        return "member/memberBaseInfo";
    }



    @RequestMapping("/apply")
    public String apply(HttpSession session){

        UserEntity member = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);

        TicketEntity ticket = userFeign.getTicketByMemberId(member.getId()) ;

        if(ticket == null ){
            ticket  = new TicketEntity(); //封装数据
            ticket.setMemberid(member.getId());
            ticket.setPstep("apply");
            ticket.setStatus("0");

            userFeign.saveTicket(ticket);

        }else {
            String pstep = ticket.getPstep();

            if ("accttype".equals(pstep)) {

                return "redirect:/member/basicinfo.html";
            } else if ("basicinfo".equals(pstep)) {

                //根据当前用户查询账户类型,然后根据账户类型查找需要上传的资质
                List<CertEntity> queryCertByAccttype = managerFeign.queryCertByAccttype(member.getAccttype());
                session.setAttribute("queryCertByAccttype", queryCertByAccttype);

                List<CertEntity> abc = (List<CertEntity>) session.getAttribute("queryCertByAccttype");
                System.out.println(abc.size());

                return "redirect:/member/uploadCert.html";
            } else if ("uploadcert".equals(pstep)) {
                return "redirect:/member/checkemail.html";
            } else if ("checkemail".equals(pstep)) {
                return "redirect:/member/checkauthcode.html";
            }
        }
        return "member/accttype";
    }


    @ResponseBody
    @RequestMapping("/member/startProcess")
    public Object startProcess( HttpSession session, String email) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            UserEntity loginMember = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);

            // 如果用户输入新的邮箱,将旧的邮箱地址替换
            if(!loginMember.getEmail().equals(email)){
                loginMember.setEmail(email);
                userFeign.updateEmail(loginMember);
            }

            StringBuilder auth = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                auth.append(new Random().nextInt(10));
            }
            String authcode = auth.toString();
            Map<String, Object> sendAuthcode = userFeign.sendauthcode(authcode, email);

            //记录流程步骤:
            TicketEntity ticket = userFeign.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("checkemail");
            ticket.setAuthcode(authcode.toString());
            userFeign.updatePiidAndPstep(ticket);

            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }


    @ResponseBody
    @RequestMapping("/member/finishApply")
    public Object finishApply( HttpSession session, String authcode) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            UserEntity loginMember = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);


            //让当前系统用户完成:验证码审核任务.
            TicketEntity ticket = userFeign.getTicketByMemberId(loginMember.getId()) ;
            if(ticket.getAuthcode().equals(authcode)){
                //更新用户申请状态
                loginMember.setAuthstatus("1");
                userFeign.updateAuthstatus(loginMember);


                //记录流程步骤:
                ticket.setPstep("finishapply");
                userFeign.updatePstep(ticket);
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setMessage("验证码不正确,请重新输入!");
            }
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;

    }



    @ResponseBody
    @RequestMapping("/member/doUploadCert")
    public Object doUploadCert( HttpSession session, Data ds) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            UserEntity loginMember = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);

//            String realPath = session.getServletContext().getRealPath("/pics");   //获取容器中pic目录的地址

            List<MemberCertEntity> certimgs = ds.getCertimgs();

            List<MemberCertDBEntity> memberCertDBEntities = new ArrayList<>();
            for (MemberCertEntity memberCert : certimgs) {

                MultipartFile fileImg = memberCert.getFileImg();
                String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
                String tmpName = UUID.randomUUID().toString() +  extName;
                String filename = "E:\\graduate\\funding\\funding-shopp-mobile\\src\\main\\resources\\static\\pics\\cert" +"/" + tmpName;



                fileImg.transferTo(new File(filename));	//资质文件上传.

                //准备数据
                MemberCertDBEntity temp = new MemberCertDBEntity();
                temp.setCertid(memberCert.getCertid());
                temp.setIconpath(tmpName); //封装数据,保存数据库
                temp.setMemberid(loginMember.getId());
                memberCertDBEntities.add(temp);
            }
            // 保存会员与资质关系数据.
            for(MemberCertDBEntity m : memberCertDBEntities){
                managerFeign .saveMemberCert(m);
            }


            //记录流程步骤:
            TicketEntity ticket = userFeign.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("uploadcert");
            userFeign.updatePstep(ticket);

            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }


    @ResponseBody
    @RequestMapping(value = "/member/updateAcct", method = RequestMethod.POST)
    public Object updateAccType(@RequestParam("accttype") String accttype, HttpSession session){
        AjaxResult result = new AjaxResult();
        try{
            UserEntity member = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);
            member.setAccttype(accttype);

            userFeign.updateAcctType(member);

            //记录流程步骤:
            TicketEntity ticket = userFeign.getTicketByMemberId(member.getId()) ;
            ticket.setPstep("accttype");
            userFeign.updatePstep(ticket);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/member/updateBasicinfomation", method = RequestMethod.POST)
    public Object updateBasicinfo( HttpSession session, UserEntity member) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            UserEntity loginMember = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());

            // 更新账户类型
            userFeign.updateBasicinfo(loginMember);

            //记录流程步骤:
            TicketEntity ticket = userFeign.getTicketByMemberId(loginMember.getId()) ;
            ticket.setPstep("basicinfo");
            userFeign.updatePstep(ticket);


            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }



    @ResponseBody
    @PostMapping(value = "/member/doIndex")
    public Object doIndex(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
                          @RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize,
                          @RequestParam(value="queryText", required=false) String queryText){

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pageSize", pageSize);
            if(StringUtil.isNotEmpty(queryText)){
                if(queryText.contains("%")){
                    queryText = queryText.replaceAll("%", "\\\\%");
                }
                paramMap.put("queryText", queryText);
            }
            Page page = userFeign.queryPage(paramMap);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @PostMapping(value = "/member/doDelete")
    public Object doIndex(@RequestParam(value="id") Integer id){

        AjaxResult result = new AjaxResult();

        try {

            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("id", id);
            int count = userFeign.deleteByPrimaryKey(paramMap);
            result.setSuccess(count==1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @PostMapping(value = "/member/doDeleteBatch")
    public Object doIndex(String idStr){

        AjaxResult result = new AjaxResult();

        try {

            if(idStr.contains("&")){
                List<Integer> del_ids = new ArrayList<>();
                String[] str_ids = idStr.split("&");
                //组装id的集合
                for(String string : str_ids){
                    String tempId = string.substring(string.lastIndexOf("=") + 1);
                    del_ids.add(Integer.parseInt(tempId));
                }
                Data data = new Data();
                data.setDel_ids(del_ids);
                boolean temp = userFeign.deleteBatch(data);

                if(!temp){
                    result.setSuccess(temp);
                    throw new IllegalArgumentException("批量删除失败");
                }

                result.setSuccess(true);
            }else{
                String tempId = idStr.substring(idStr.lastIndexOf("=") + 1);
                Integer id = Integer.parseInt(tempId);
                Map<String, Integer> paramMap = new HashMap<>();
                paramMap.put("id", id);
                userFeign.deleteByPrimaryKey(paramMap);
                result.setSuccess(true);
            }


//            int count = userFeign.(data);
//            result.setSuccess(count==data.getDatas().size());
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败!");
        }
        return result;
    }



    @ResponseBody
    @PostMapping(value = "/member/queryMemberQuestionPage")
    public Object queryMemberQuestionPage(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
                          @RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize,
                          HttpSession session){

        AjaxResult result = new AjaxResult();

        // 获取登录会员信息
        UserEntity loginMember = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pageSize", pageSize);
            paramMap.put("mid", loginMember.getId());

            Page page = userFeign.queryMemberQuestionList(paramMap);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/member/logout")
    public Object memberLogout(HttpSession session){

        session.removeAttribute(TimeConstants.LOGIN_MEMBER);

        return "redirect:/index.html";
    }


}
