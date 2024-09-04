package com.trongphu.finalintern1.util.exception.uploadsfile;

/**
 * Created by Trong Phu on 30/08/2024 09:02
 * RuntimeException thông báo lỗi về size file vượt quá điều kiện yêu cầu
 * @author Trong Phu
 */
public class FileSizeExceededException extends RuntimeException{


    private int conditionSize;

    public FileSizeExceededException(String message, int conditionSize){
        super(message);
        this.conditionSize = conditionSize;
    }

    public int getConditionSize(){
        return this.conditionSize;
    }
}
