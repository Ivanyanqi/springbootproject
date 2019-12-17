package cn.ivan.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class BussinessException extends RuntimeException {

    public BussinessException(String message){
        super(message);
    }
}
