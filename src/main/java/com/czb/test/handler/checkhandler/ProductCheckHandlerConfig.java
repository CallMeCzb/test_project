package com.czb.test.handler.checkhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/26
 * @time: 10:06
 */
@AllArgsConstructor
@Data
public class ProductCheckHandlerConfig {
    /**
     * 处理器Bean名称
     */
    private String handler;
    /**
     * 下一个处理器
     */
    private ProductCheckHandlerConfig next;
    /**
     * 是否降级
     */
    private Boolean down = Boolean.FALSE;
}
