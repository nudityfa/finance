package com.adminUtil.exception;

import com.adminUtil.commonUtil.ResponseBean;
import lombok.Data;

/**
  *自定义异常
  * @author luojinhua
  * @date 19:24 2019/12/16
  * @param
  * @return
  */

@Data
public class MyException extends RuntimeException {

    protected ResponseBean.Code errorCode = ResponseBean.Code.serverError;

    protected String errorMessage;

    public MyException(){
        super();
    }

    public MyException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public MyException(ResponseBean.Code errorCode, String errorMessage){
        super(errorCode.getCode());
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MyException(ResponseBean.Code errorCode, String errorMessage, Throwable throwable){
        super(errorCode.getCode(),throwable);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
