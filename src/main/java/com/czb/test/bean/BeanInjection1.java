package com.czb.test.bean;

import com.czb.test.entity.RdmpLaunchPlan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/8
 * @time: 21:55
 */
@Configuration
public class BeanInjection1 {

    static {
        System.out.println("2---------");
    }
    {
        System.out.println("3---------------");
    }

    @Bean
    public RdmpLaunchPlan myplan1(){
        System.out.println("myplan=====1");
        RdmpLaunchPlan launchPlan = new RdmpLaunchPlan();
        System.out.println("myplan=====2");
        launchPlan.setPlanName("plan_name1");
        return launchPlan;
    }

}
