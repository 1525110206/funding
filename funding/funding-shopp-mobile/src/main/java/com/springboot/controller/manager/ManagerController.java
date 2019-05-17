package com.springboot.controller.manager;

import com.springboot.constants.TimeConstants;
import com.springboot.controller.BaseController;
import com.springboot.entity.AjaxResult;
import com.springboot.entity.Page;
import com.springboot.entity.TicketEntity;
import com.springboot.entity.UserEntity;
import com.springboot.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ManagerController extends BaseController {

    @Autowired
    private UserFeign userFeign;


    @RequestMapping("/manager/index.html")
    public String index(Map<String, Object> map){
        return "manager/manager";
    }


    @RequestMapping("/manager/task.html")
    public String task(){
        return "manager/task";
    }

    @RequestMapping("/manager/toManagerQuestion.html")
    public String managerQuestion(){
        return "manager/managerQuestion";
    }


    //来到修改页面，查出当前员工，在页面回显
    @RequestMapping("/manager/add/{id}")
    public String toEditPage(@PathVariable("id") String id, Model model){
        String tempId = id.substring(id.lastIndexOf("=") + 1);
        Integer search = Integer.parseInt(tempId);

        System.out.println(search);
        UserEntity member = userFeign.getMember(search);
        //回到修改页面(add是一个修改添加二合一的页面);
        model.addAttribute("member",member);
        return "manager/add";
    }


    @RequestMapping("/manager/addTwo")
    public String toaddPage(){
        return "manager/add";
    }


    @PutMapping("/member/update")
    public String updateMember(UserEntity userEntity){
        System.out.println("修改的员工数据："+userEntity);
        userFeign.updateEmailAndUsername(userEntity);
        return "redirect:/manager/index.html";
    }


    @PostMapping("/member/update")
    public String addMember(UserEntity userEntity,HttpServletRequest request){
        Integer membercount = userFeign.queryPhoneCount(userEntity);
        if(membercount != 0){
            return setError(request, "该手机号已被注册", "manager/add");
        }else{
            Map<String, Object> registMap = userFeign.regist(userEntity);
        }
        System.out.println("保存的员工信息："+userEntity);
        //保存员工

        // redirect: 表示重定向到一个地址  /代表当前项目路径
        // forward: 表示转发到一个地址
        return "redirect:/manager/index.html";
    }


    @ResponseBody
    @PostMapping(value = "/authcert/pageQuery")
    public Object doIndex(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
                          @RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize){

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pageSize", pageSize);
            Page page = userFeign.getMemberByAuth(paramMap);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("/manager/task")
    public String show(Integer memberid,Map<String, Object> map){
        UserEntity member = userFeign.getMemberById(memberid);

        List<Map<String,Object>> list = userFeign.queryCertByMemberid(memberid);

        map.put("member", member);
        map.put("certimgs", list);
        return "manager/show";
    }

    @ResponseBody
    @RequestMapping("/manager/showRefuse")
    public Object showRefuse(UserEntity member){
        AjaxResult result = new AjaxResult();

        try {

            member.setAuthstatus("0");
            TicketEntity ticket = new TicketEntity();
            ticket.setMemberid(member.getId());

            // 更新账户类型为审核成功
            userFeign.updateAuthstatus(member);

            // 更新流程步骤为审核完成
            userFeign.updateStatus(ticket);
            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }
    @ResponseBody
    @RequestMapping("/manager/showPass")
    public Object showPass(UserEntity member){
        AjaxResult result = new AjaxResult();

        try {

            member.setAuthstatus("2");
            TicketEntity ticket = new TicketEntity();
            ticket.setMemberid(member.getId());

            // 更新账户类型为审核成功
            userFeign.updateAuthstatus(member);

            // 更新流程步骤为审核完成
            userFeign.updateStatus(ticket);
            result.setSuccess(true);
        } catch( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }


    @ResponseBody
    @PostMapping(value = "/manager/queryManagerQuestionPage")
    public Object queryMemberQuestionPage(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
                                          @RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize){

        AjaxResult result = new AjaxResult();


        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pageSize", pageSize);

            Page page = userFeign.queryManagerQuestionList(paramMap);

            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value = "/manager/logout")
    public Object managerLogout(HttpSession session){

        session.removeAttribute(TimeConstants.LOGIN_MANAGER);

        return "redirect:/index.html";
    }





}
