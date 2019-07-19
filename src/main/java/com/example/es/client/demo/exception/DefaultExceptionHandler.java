package com.example.es.client.demo.exception;

import com.example.es.client.demo.result.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * DefaultExceptionHandler:自定义异常处理器
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/18
 */
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {
    /**
     * 异常统一自定义处理
     * @param e 传入自定义的异常
     * @return  返回异常作为响应结果给客户端
     */
    @ExceptionHandler({MyException.class})
    @ResponseBody
    public ResultData MyException(MyException e) {
        ResultData ResultData=new ResultData<>();
        ResultData.setCode(500);
        ResultData.setMsg(e.getMessage());
        ResultData.setData(null);
        return ResultData;
    }

    /**
     * 异常统一处理(最后的异常处理)
     * @param e 传入处理的异常
     * @return  返回异常作为响应结果给客户端
     */
//    @ExceptionHandler({Exception.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResultData allException(Exception e) {
//        ResultData ResultData=new ResultData<>();
//        ResultData.setCode(500);
//        ResultData.setMsg("系统异常(控制层最后的防线)");
//        ResultData.setData(null);
//        return ResultData;
//    }

}
