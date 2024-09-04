package com.trongphu.finalintern1.util.exception;

/**
 * Created by Trong Phu on 31/08/2024 15:19
 * Exception thông báo lỗi về các đối số truyền vào không hợp lệ
 * @author Trong Phu
 */
public class InvalidArgumentException extends RuntimeException{
    private String argumentValue;
    private String argumentName;

    public InvalidArgumentException(String message){
        super(message);
    }

    public InvalidArgumentException(String message, String argumentValue, String argumentName){
        super(message);
        this.argumentName = argumentName;
        this.argumentValue = argumentValue;
    }

    public String getArgumentValue() {
        return argumentValue;
    }

    public String getArgumentName() {
        return argumentName;
    }

}
