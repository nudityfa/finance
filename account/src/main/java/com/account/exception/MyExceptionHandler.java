package com.account.exception;


import com.adminUtil.commonUtil.ResponseBean;
import com.adminUtil.exception.MyException;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @description
 * @author luojinhua 
 * @date 2021/5/11 17:56     
 * @param 
 * @return 
 */
@Log4j2
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    public ResponseBean exceptionHandler(MyException e){
        log.error("发生业务异常！原因是：" + e);
        return ResponseBean.fail(e.getErrorCode(),e.getErrorMessage());
    }


    @ExceptionHandler(value =NullPointerException.class)
    public ResponseBean exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:" + e);
        return ResponseBean.fail(ResponseBean.Code.serverError,e.getMessage());
    }

    @ExceptionHandler(value =NumberFormatException.class)
    public ResponseBean exceptionHandler(NumberFormatException e){
        log.error("类型转换错误！原因是:" + e);
        return ResponseBean.fail(ResponseBean.Code.serverError,e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseBean exceptionHandler(UnauthorizedException e) {
        log.error("无权限异常！原因是:" + e);
        return ResponseBean.fail(ResponseBean.Code.serverError,"无权限访问");
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    public ResponseBean exceptionHandler(UnauthenticatedException e) {
        log.error("登录验证异常！原因是:" + e);
        return ResponseBean.fail(ResponseBean.Code.authenticationRequired,"身份验证失败");
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseBean exceptionHandler(DuplicateKeyException e) {
        log.error("数据重复！原因是:"+e);
        return ResponseBean.fail(ResponseBean.Code.serverError,"数据已存在");
    }

    @ExceptionHandler(value =Exception.class)
    public ResponseBean exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return ResponseBean.fail(ResponseBean.Code.serverError,e.getMessage());
    }



}
