package com.trongphu.finalintern1.util.exception.uploadsfile;

/**
 * Created by Trong Phu on 31/08/2024 16:40
 * RuntimeException thông báo lỗi trong quá trình uploadFile;
 * @author Trong Phu
 */
public class FileUploadingException extends RuntimeException{
    public FileUploadingException(String message){
        super(message);
    }
}
