package com.czb.test.handler.checkhandler;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/26
 * @time: 10:05
 */

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品对象
 */
@Data
@Builder
public class ProductVO {
    /**
     * 商品SKU，唯一
     */
    private Long skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品图片路径
     */
    private String Path;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
}
