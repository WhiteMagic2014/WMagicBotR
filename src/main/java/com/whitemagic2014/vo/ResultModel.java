package com.whitemagic2014.vo;

import com.whitemagic2014.dic.ReturnCode;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Description: 对外接口都由此model进行封装，禁止任何其他封装。
 * @author: magic chen
 * @date: 2020/10/26 14:41
 **/
public class ResultModel extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private static final String CODE = "code";
    /**
     * 描述性内容
     */
    private static final String MESSAGE = "message";
    /**
     * 返回结果集
     */
    private static final String RESULT = "result";
    // ---------------以下只添加状态码--------------------------

    public ResultModel success() {
        this.put(CODE, ReturnCode.SUCCESS);
        return this;
    }

    public ResultModel fail(String code) {
        this.put(CODE, code);
        return this;
    }

    public ResultModel message(String message) {
        this.put(MESSAGE, message);
        return this;
    }

    public ResultModel result(Object object) {
        if (null != object) {
            this.put(RESULT, object);
        } else {
            this.put(RESULT, "");
        }
        return this;
    }

    public ResultModel base(String code, String message) {
        this.put(CODE, code);
        this.put(MESSAGE, message);
        return this;
    }

    public ResultModel base(String code, String message, Object object) {
        this.put(CODE, code);
        this.put(MESSAGE, message);
        this.result(object);
        return this;
    }


    public Object getObject() {
        return this.get(RESULT);
    }

    public boolean isSuccess() {
        return this.get(CODE).equals(ReturnCode.SUCCESS);
    }

    public <T> boolean isSuccess(PrivateModel<T> privateModel) {
        return ReturnCode.SUCCESS.equals(privateModel.getReturnCode());
    }


    public <T> ResultModel wrapper(PrivateModel<T> privateModel) {
        this.put(CODE, privateModel.getReturnCode());
        this.put(MESSAGE, privateModel.getReturnMessage());
        if (null != privateModel.getReturnObject()) {
            this.result(privateModel.getReturnObject());
        }
        return this;
    }
}
