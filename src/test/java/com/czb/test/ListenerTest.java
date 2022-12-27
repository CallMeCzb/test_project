package com.czb.test;

import com.alibaba.fastjson.JSON;
import com.czb.test.controller.UserInfoSerimpl;
import com.czb.test.entity.RdmpLaunchPlan;
import com.czb.test.handler.checkhandler.*;
import com.czb.test.listener.event.NotifyEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/11
 * @time: 16:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Map<String, AbstractCheckHandler> handlerMap;

    @Autowired
    private Map<String, RdmpLaunchPlan> serimplMap;

    @Autowired
    List<RdmpLaunchPlan> rdmpLaunchPlan;

    @Test
    public void testListener() {

        NotifyEvent event = new NotifyEvent("object", "abc@qq.com", "This is the content");

        webApplicationContext.publishEvent(event);
    }

    /**
     * 创建商品
     */
    @Test
    public void createProduct() {
        ProductVO param = ProductVO.builder()
                .skuId(null).skuName("").Path("http://...")
                .price(new BigDecimal(1))
                .stock(1)
                .build();
        //参数校验，使用责任链模式
        Result paramCheckResult = this.paramCheck(param);
        if (!paramCheckResult.isSuccess()) {
            System.out.println("11=======");
        }

        //测试4
        //创建商品
//        return this.saveProduct(param);
    }

    /**
     * 参数校验：责任链模式
     * @param param
     * @return
     */
    private Result paramCheck(ProductVO param) {

        //获取处理器配置：通常配置使用统一配置中心存储，支持动态变更
        ProductCheckHandlerConfig handlerConfig = this.getHandlerConfigFile();

        //获取处理器
        AbstractCheckHandler handler = this.getHandler(handlerConfig);

        //责任链：执行处理器链路
        Result executeChainResult = HandlerClient.executeChain(handler, param);
        if (!executeChainResult.isSuccess()) {
            System.out.println("创建商品 失败...");
            return executeChainResult;
        }

        //处理器链路全部成功
        return Result.build().success();
    }

    /**
     * 获取处理器配置：通常配置使用统一配置中心存储，支持动态变更
     * @return
     */
    private ProductCheckHandlerConfig getHandlerConfigFile() {
        //配置中心存储的配置
        String configJson = "{\"handler\":\"nullValueCheckHandler\",\"down\":true,\"next\":{\"handler\":\"priceCheckHandler\",\"next\":{\"handler\":\"stockCheckHandler\",\"next\":null}}}";
        //转成Config对象
        ProductCheckHandlerConfig handlerConfig = JSON.parseObject(configJson, ProductCheckHandlerConfig.class);
        return handlerConfig;
    }


    /**
     * 获取处理器
     * @param config
     * @return
     */
    private AbstractCheckHandler getHandler (ProductCheckHandlerConfig config) {
        //配置检查：没有配置处理器链路，则不执行校验逻辑
        if (Objects.isNull(config)) {
            return null;
        }
        //配置错误
        String handler = config.getHandler();
        if (StringUtils.isEmpty(handler)) {
            return null;
        }
        //配置了不存在的处理器
        AbstractCheckHandler abstractCheckHandler = handlerMap.get(config.getHandler());
        if (Objects.isNull(abstractCheckHandler)) {
            return null;
        }

        //处理器设置配置Config
        abstractCheckHandler.setConfig(config);

        //递归设置链路处理器
        abstractCheckHandler.setNextHandler(this.getHandler(config.getNext()));

        return abstractCheckHandler;
    }
}
