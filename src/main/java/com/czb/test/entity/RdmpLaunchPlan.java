package com.czb.test.entity;


import lombok.Data;
import sun.rmi.runtime.Log;

import java.io.Serializable;
import java.util.Date;

/**
* 上线计划表 rdmp_launch_plan
*/

@Data
public class RdmpLaunchPlan implements Serializable {
    protected static final long serialVersionUID = 1L;

    static {
        System.out.println("1===========");
    }

    protected Long id;

    private String planName;

    public String softProvider;

    protected String belongSys;

    protected Integer regTest;

    protected Long launchRespId;

    protected String launchRespName;

    protected Long regTesterId;

    protected String regTesterName;

    protected Date expLaunchDate;

    protected Integer launchPlanType;

    protected String currentLink;

    protected Integer status;

    protected String remark;

    protected Long subFile;

    protected Long createUser;

    protected Date createDate;

    protected Long updateUser;

    protected Date updateDate;

    protected String delFlag;

}


