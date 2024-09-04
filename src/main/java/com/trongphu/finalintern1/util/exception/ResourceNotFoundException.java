package com.trongphu.finalintern1.util.exception;

/**
 * Created by Trong Phu on 29/08/2024 23:40
 * RuntimeException thông báo lỗi về các tài nguyên không được tìm thấy
 * @author Trong Phu
 */
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceType;

    public ResourceNotFoundException(String message, String resourceType){
        super(message);
        this.resourceType = resourceType;
    }

    public String getResourceType(){
        return this.resourceType;
    }
}
