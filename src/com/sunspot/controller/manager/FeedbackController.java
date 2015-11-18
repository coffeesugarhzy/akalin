package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.Feedback;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/feedback")
public class FeedbackController
{

    @Resource
    MyService service;

    @RequestMapping("listFeedback")
    public void listFeedback()
    {
    }

    @ResponseBody
    @RequestMapping("queryFeedback")
    public DataGridModel<Feedback> queryFeedback(DataGridModel<Feedback> list,
            String keyword)
    {
        List<Feedback> feedbackList = service.queryFeedback(list, keyword);
        list.setContent(feedbackList);
        list.setRecords(feedbackList.size());
        return list;
    }
}
