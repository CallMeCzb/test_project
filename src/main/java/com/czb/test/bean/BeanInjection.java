package com.czb.test.bean;

import com.czb.test.entity.RdmpLaunchPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/8
 * @time: 21:55
 */
@Configuration
public class BeanInjection {

    static {
        System.out.println("2---------");
    }
    {
        System.out.println("3---------------");
    }

    @Bean
    public RdmpLaunchPlan myplan(){
        System.out.println("myplan=====1");
        RdmpLaunchPlan launchPlan = new RdmpLaunchPlan();
        System.out.println("myplan=====2");
        launchPlan.setPlanName("plan_name0");
        return launchPlan;
    }

}
