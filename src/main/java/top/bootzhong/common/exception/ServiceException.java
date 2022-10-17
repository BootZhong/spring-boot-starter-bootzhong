package top.bootzhong.common.exception;

/**
 * 业务逻辑异常
 * @author bootzhong
 */
public class ServiceException extends RuntimeException{
    public ServiceException(String msg){
        super(msg);
    }
}
