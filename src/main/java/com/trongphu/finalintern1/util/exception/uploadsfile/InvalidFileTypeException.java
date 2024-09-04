package com.trongphu.finalintern1.util.exception.uploadsfile;

/**
 * Created by Trong Phu on 30/08/2024 09:12
 * RuntimeException thông báo lỗi về
 * @author Trong Phu
 */
public class InvalidFileTypeException extends RuntimeException{

    private final String conditionFileType;

    public InvalidFileTypeException(String message, String conditionFileType){
        super(message);
        this.conditionFileType = conditionFileType;
    }

    public String getConditionFileType(){
        return this.conditionFileType;
    }
}
