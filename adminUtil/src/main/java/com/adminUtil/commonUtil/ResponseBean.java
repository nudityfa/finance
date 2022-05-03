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

    public ResponseBean() {
        code = Code.success.getCode();
    }

    public ResponseBean(Code code, String msg, Object data) {
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
    }

    public static ResponseBean code(Code code){
        ResponseBean bean = new ResponseBean();
        if (code != null){
            bean.setCode(code.getCode());
        }
        return bean;
    }

    public static ResponseBean data(Object data){
        ResponseBean bean = new ResponseBean();
        if (data != null){
            bean.setData(data);
        }
        return bean;
    }

    public static ResponseBean dataWithCode(Code code,Object data){
        if (code == null){
            return data(data);
        }
        if (data == null){
            return code(code);
        }
        ResponseBean bean = new ResponseBean();
        bean.setCode(code.getCode());
        bean.setData(data);
        return bean;
    }

    public static ResponseBean dataWithMsg(String msg,Object data){
        if (StrUtil.isNotBlank(msg)){
            return data(data);
        }
        if (data == null){
            return msg(msg);
        }
        ResponseBean bean = new ResponseBean();
        bean.setMsg(msg);
        bean.setData(data);
        return bean;
    }

    public static ResponseBean msg(String msg){
        ResponseBean bean = new ResponseBean();
        if (StrUtil.isNotBlank(msg)){
            bean.setMsg(msg);
        }
        return bean;
    }

    public static ResponseBean msgWithCode(Code code,String msg){
        if (StrUtil.isBlank(msg)){
            return code(code);
        }
        if (code == null){
            return msg(msg);
        }
        ResponseBean bean = new ResponseBean();
        bean.setCode(code.getCode());
        bean.setMsg(msg);
        return bean;
    }


    public static ResponseBean all(Code code,String msg,Object data){
        if (code == null){
            return dataWithMsg(msg,data);
        }
        if (StrUtil.isBlank(msg)){
            return dataWithCode(code,data);
        }
        if (data == null){
            return msgWithCode(code,msg);
        }
        return new ResponseBean(code,msg,data);
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
