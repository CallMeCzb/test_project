package com.czb.test.controller;

import com.czb.test.entity.RdmpLaunchPlan;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author:czb
 * @date: 2023/5/7
 * @time: 12:06
 */
@Controller
@RequestMapping("/rdmp/test")
public class RedisController {
    @Autowired
    private RedissonClient redissonClient;

    @PostMapping("/testMethod")
    @ResponseBody
    public RdmpLaunchPlan testMethod(@RequestBody RdmpLaunchPlan rdmpLaunchPlan) {
        RLock rLock = redissonClient.getLock("lockName");
        try {
            boolean isLocked = rLock.tryLock(10, TimeUnit.MILLISECONDS);
            if (isLocked) {
                System.out.println("============1============");
            }
            rLock.unlock();
        } catch (Exception e) {
            rLock.unlock();
        }
        System.out.println("============2============");
        return null;
    }
}
