package com.springboot.controller.member;

import com.springboot.constants.TimeConstants;
import com.springboot.entity.AjaxResult;
import com.springboot.entity.AnswerEntity;
import com.springboot.entity.Page;
import com.springboot.entity.UserEntity;
import com.springboot.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AnswerController {


    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/answer/index/{id}")
    public String index(@PathVariable("id") String id, HttpSession session){
        String tempId = id.substring(id.lastIndexOf("=") + 1);
        Integer search = Integer.parseInt(tempId);
        session.setAttribute("tempId", search);
        return "answer";
    }


    @ResponseBody
    @PostMapping("/answer/doindex")
    public Object doIndex(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
                          @RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize,
                          @RequestParam(value="qid", required=false) String qid){

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pageSize", pageSize);
            paramMap.put("qid", qid);
            Page page = userFeign.queryAnswerPage(paramMap);

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
    @PostMapping("/answer/insertAnswer")
    public Object doIndex(AnswerEntity answerEntity, HttpSession session){

        AjaxResult result = new AjaxResult();
        UserEntity member = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);
        try {
            answerEntity.setMid(member.getId());
            userFeign.insertAnswer(answerEntity);
            userFeign.updateAccountById(answerEntity.getQid());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("插入失败");
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @PostMapping("/answer/queryMemberAnswerPage")
    public Object doIndex(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
                          @RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize,
                          HttpSession session){

        AjaxResult result = new AjaxResult();
        UserEntity member = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pageSize", pageSize);
            paramMap.put("mid", member.getId());
            Page page = userFeign.queryMemberAnswerList(paramMap);

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
    @PostMapping(value = "/answer/dodeleteAnswer")
    public Object doIndex(@RequestParam(value="id") Integer id){

        AjaxResult result = new AjaxResult();

        try {

            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("id", id);
            int count = userFeign.deleteAnswerByPrimaryKey(paramMap);
            result.setSuccess(count==1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
        }
        return result;
    }





}
