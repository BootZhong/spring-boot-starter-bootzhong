package top.bootzhong.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.bootzhong.common.exception.ServiceException;
import top.bootzhong.common.model.entity.CommonResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局拦截器
 * @author bootzhong
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null && body.getClass().equals(CommonResponse.class)){
            return body;
        }
        return CommonResponse.success(body);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object exceptionHandle(Exception e) {
        //todo
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log.error("server error" + e.getMessage(), e);
        return CommonResponse.serverException("\r\n" + sw + "\r\n");
    }

    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object serviceExceptionHandle(Exception e) {
        return CommonResponse.serviceException(e.getMessage());
    }
}
