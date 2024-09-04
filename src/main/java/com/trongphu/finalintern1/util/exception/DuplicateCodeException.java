package com.trongphu.finalintern1.util.exception;

/**
 * Created by Trong Phu on 30/08/2024 15:36
 * RuntimeException xử lý lỗi trùng mã của các entity
 * @author Trong Phu
 */
public class DuplicateCodeException extends RuntimeException{

    private final String entity;
    private final String entityCode;

    public DuplicateCodeException(String message, String entity, String entityCode){
        super(message);
        this.entity = entity;
        this.entityCode = entityCode;
    }

    public String getEntity(){
        return this.entity;
    }
    public String getEntityCode(){
        return this.entityCode;
    }
}
