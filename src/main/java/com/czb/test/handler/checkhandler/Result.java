package com.czb.test.handler.checkhandler;


import java.util.HashMap;
import org.springframework.http.HttpStatus;

public class Result extends HashMap<String, Object> {
    public static final String CODE_KEY = "code";
    public static final String DATA_KEY = "data";
    public static final String MSG_KEY = "msg";
    public static final String RESULT_KEY = "result";

    public Result() {
    }

    public static Result build() {
        return new Result();
    }

    public Result code(HttpStatus code) {
        return this.add("result", code == HttpStatus.OK).add("code", code.value());
    }

    public Result msg(String msg) {
        return this.add("msg", msg);
    }

    public Result data(Object data) {
        return this.add("data", data);
    }

    public Result success() {
        return this.code(HttpStatus.OK);
    }

    public Result success(Object data) {
        return this.success().data(data);
    }

    public Result success(String msg, Object data) {
        return this.success().msg(msg).data(data);
    }

    public Result error() {
        return this.code(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Result error(String msg) {
        return this.error().msg(msg);
    }

    public Result add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public boolean isSuccess() {
        return (Boolean)this.get("result");
    }

    public String getMsg() {
        return String.valueOf(this.get("msg"));
    }

    public Object getData() {
        return this.get("data");
    }
}
