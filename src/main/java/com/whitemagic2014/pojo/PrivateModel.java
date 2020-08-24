package com.whitemagic2014.pojo;

import com.whitemagic2014.dic.ReturnCode;

import java.util.List;
import java.util.Map;

/**
 * @Description: 返回封装
 * @author: magic chen
 * @date: 2020/8/22 13:54
 **/
public class PrivateModel<T> {

    private String returnCode;
    private String returnMessage;
    private Map<String, List<Long>> ats;
    private T returnObject;

    public PrivateModel() {
        this(ReturnCode.SUCCESS, "", null, null);
    }

    public PrivateModel(String returnCode, String returnMessage) {
        this(returnCode, returnMessage, null, null);
    }

    public PrivateModel(String returnCode, String returnMessage, T returnObject) {
        this(returnCode, returnMessage, null, returnObject);
    }

    public PrivateModel(String returnCode, String returnMessage, Map<String, List<Long>>ats, T returnObject) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.returnObject = returnObject;
        this.ats = ats;
    }


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }


    public void setAts(Map<String, List<Long>> ats) {
        this.ats = ats;
    }

    public Map<String, List<Long>> getAts() {
        return ats;
    }

    public <S> PrivateModel<T> wrapper(PrivateModel<S> model) {
        this.returnCode = model.getReturnCode();
        this.returnMessage = model.getReturnMessage();
        return this;
    }

    /**
     * @Name: isSuccess
     * @Description: 判断当前对象是否为success状态
     * @Param:
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/8/22 13:57
     **/
    public boolean isSuccess() {
        if (ReturnCode.SUCCESS.equals(this.getReturnCode())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PrivateModel{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", ats=" + ats +
                ", returnObject=" + returnObject +
                '}';
    }
}
