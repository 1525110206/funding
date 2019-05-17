package com.springboot.controller.member;


import com.springboot.constants.TimeConstants;
import com.springboot.entity.AjaxResult;
import com.springboot.entity.Page;
import com.springboot.entity.QuestionEntity;
import com.springboot.entity.UserEntity;
import com.springboot.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/question")
public class QuestionController {


    @Autowired
    private UserFeign userFeign;

    @ResponseBody
    @PostMapping("/doIndex")
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
            Page page = userFeign.queryQuestionList(paramMap);

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
    @PostMapping("/insertQuestion")
    public Object doIndex(QuestionEntity questionEntity, HttpSession session){

        AjaxResult result = new AjaxResult();

        try {
            UserEntity loginMember = (UserEntity) session.getAttribute(TimeConstants.LOGIN_MEMBER);
            questionEntity.setMid(loginMember.getId());

            userFeign.insertQuestion(questionEntity);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加问题失败");
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @PostMapping(value = "/dodeleteQuestion")
    public Object doIndex(@RequestParam(value="id") Integer id){

        AjaxResult result = new AjaxResult();

        try {

            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("id", id);
            int count = userFeign.deleteQuestionByPrimaryKey(paramMap);
            result.setSuccess(count==1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
        }
        return result;
    }








}
