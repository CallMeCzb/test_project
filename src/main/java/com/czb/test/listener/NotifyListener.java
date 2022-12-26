package com.czb.test.listener;

import com.czb.test.listener.event.NotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/11
 * @time: 16:55
 */
@Component
public class NotifyListener implements ApplicationListener<NotifyEvent> {

    @Override
    public void onApplicationEvent(NotifyEvent event) {
        System.out.println("邮件地址：" + event.getEmail());
        System.out.println("邮件内容：" + event.getContent());
    }
}
