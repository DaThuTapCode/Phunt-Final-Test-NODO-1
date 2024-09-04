package com.trongphu.finalintern1.util.exception;

/**
 * Created by Trong Phu on 02/09/2024 17:30
 *
 * @author Trong Phu
 */
public class DuplicateEntityRecordException extends RuntimeException{
    private String entityName;
    public DuplicateEntityRecordException(String message, String entityName){
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName(){
        return this.entityName;
    }
}
