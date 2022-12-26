package com.czb.test.controller;

import com.czb.test.bean.ApplicationContextUtil;
import com.czb.test.entity.RdmpLaunchPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/yu")
public class BusinessController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoSerimpl userInfoSerimpl;

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        userInfoSerimpl.saveUserInfo();
        return "test";
    }

    //测试1
    @Autowired
    @Qualifier("myplan1")
    RdmpLaunchPlan rdmpLaunchPlan;

    @ResponseBody
    @RequestMapping("/generics")
    public String generics(){
        rdmpLaunchPlan.getPlanName();
        Object o = ApplicationContextUtil.getBean(RdmpLaunchPlan.class);

        List<RdmpLaunchPlan> list = new ArrayList<>();
        RdmpLaunchPlan first = new RdmpLaunchPlan();
        first.setId(Long.valueOf(1));
        RdmpLaunchPlan second = new RdmpLaunchPlan();
        second.setId(Long.valueOf(2));
        list.add(first);
        list.add(second);
        List<Long> ids = userInfoSerimpl.fetchId(list,RdmpLaunchPlan.class);
        return "generics";
    }
}

