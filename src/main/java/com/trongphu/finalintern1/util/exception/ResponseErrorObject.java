package com.trongphu.finalintern1.util.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Trong Phu on 29/08/2024 22:43
 * Đối tượng trả ra cấu trúc lỗi chi tiết
 * @author Trong Phu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorObject {

    private String path;

    private LocalDateTime timestamp;

    private String message;

    private int status;

    private String error;

}
