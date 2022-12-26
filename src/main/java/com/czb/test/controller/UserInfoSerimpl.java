package com.czb.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service("userInfoService")
public class UserInfoSerimpl {
    private Logger logger = LoggerFactory.getLogger(UserInfoSerimpl.class);

    public void saveUserInfo() {
        test1();
    }


    private void test1(){
        throw new RuntimeException();

    }

    public <T,R> List<R> fetchId(List<T> itemList,Class clazz) {
        List<R> idList = new ArrayList<R>();
        if (itemList.isEmpty()) {
            return idList;
        }
        try {
            Method m = clazz.getDeclaredMethod("getId");
            for (T item : itemList) {
                R id = (R)m.invoke(item);
                idList.add(id);
            }
            return idList;
        } catch (Exception e) {
            e.printStackTrace();
            return idList;
        }
    }
}

