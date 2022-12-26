package com.czb.test.handler.checkhandler;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/26
 * @time: 10:08
 */
public class HandlerClient {

    public static Result executeChain(AbstractCheckHandler handler, ProductVO param) {
        //执行处理器
        Result handlerResult = handler.handle(param);
        if (!handlerResult.isSuccess()) {
            System.out.println("HandlerClient 责任链执行失败返回：" + handlerResult.toString());
            return handlerResult;
        }
        return Result.build().success();
    }
}
