package com.adminUtil.commonUtil;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
/**
 * @description
 * @author luojinhua 
 * @date 2021/5/11 18:15
 * @param 
 * @return 
 */
@Data
public class ResponseBean {

    private String code;

    private String msg;

    private Object data;

    private ResponseBean() {

    }

    public static ResponseBean success(){
        ResponseBean bean = new ResponseBean();
        bean.setCode(Code.success.getCode());
        return bean;
    }

    public static ResponseBean success(Object object){
        ResponseBean bean = success();
        bean.setData(object);
        return bean;
    }

    public static ResponseBean successWithMsg(String msg){
        ResponseBean bean = success();
        bean.setMsg(msg);
        return bean;
    }

    public static ResponseBean fail(){
        ResponseBean bean = new ResponseBean();
        bean.setCode(Code.serverError.getCode());
        return bean;
    }

    public static ResponseBean fail(Object object){
        ResponseBean bean = fail();
        bean.setData(object);
        return bean;
    }

    public static ResponseBean failWithMsg(String msg){
        ResponseBean bean = fail();
        bean.setMsg(msg);
        return bean;
    }

    public static ResponseBean fail(Code code,String msg){
        ResponseBean bean = fail();
        bean.setCode(code.getCode());
        bean.setMsg(msg);
        bean.setData("");
        return bean;
    }

    public static ResponseBean custom(String code,String msg,Object object){
        ResponseBean bean = new ResponseBean();
        bean.setCode(code);
        bean.setMsg(msg);
        bean.setData(object);
        return bean;
    }

    /**
     * @description
     * 后期需要自己补充吧
     * @author luojinhua
     * @date 2021/5/11 17:18
     * @param
     * @return
     */
    public enum Code{
        //
        success("200"),
        badRequest("400"),
        unauthorized("401"),
        forbidden("403"),
        notFound("404"),
        notAllowed("405"),
        unAccept("406"),
        authenticationRequired("407"),
        requestTimeOut("408"),
        serverError("500")
        ;
        private final String code;
        Code(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }
}
