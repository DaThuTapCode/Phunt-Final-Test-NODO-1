package com.trongphu.finalintern1.util.exception;

import com.trongphu.finalintern1.config.i18nconfig.Translator;
import com.trongphu.finalintern1.util.exception.uploadsfile.FileSizeExceededException;
import com.trongphu.finalintern1.util.exception.uploadsfile.FileUploadingException;
import com.trongphu.finalintern1.util.exception.uploadsfile.InvalidFileTypeException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;

/**
 * Created by Trong Phu on 29/08/2024 20:50
 * Nơi bắt các ngoại lệ toàn cục và trả ra 1 đối tượng {@link ResponseErrorObject} thông báo lỗi chi tiết
 * @author Trong Phu
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Tạo đối tượng lỗi phản hồi với thông tin chi tiết về lỗi.
     *
     * @param message Tin nhắn lỗi được lấy từ các dịch vụ i18n, thường chứa mô tả ngắn gọn về lỗi.
     * @param request Đối tượng {@link WebRequest} dùng để lấy thông tin về yêu cầu HTTP hiện tại,
     *                bao gồm đường dẫn (URI) mà yêu cầu đã được gửi đến.
     *
     * @return {@link ResponseErrorObject} Đối tượng chứa thông tin lỗi, bao gồm mã lỗi, tin nhắn lỗi,
     *         đường dẫn yêu cầu, mã trạng thái HTTP và thời gian xảy ra lỗi.
     */
    public ResponseErrorObject setResponseError(String message, WebRequest request) {
        System.out.println(message); // Ghi tin nhắn lỗi ra console (có thể cần gỡ bỏ trong môi trường sản xuất)
        return ResponseErrorObject.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase()) // Mô tả lỗi HTTP
                .message(message) // Tin nhắn lỗi
                .path(request.getDescription(false).replace("uri=", "")) // Đường dẫn yêu cầu (URI)
                .status(HttpStatus.BAD_REQUEST.value()) // Mã trạng thái HTTP
                .timestamp(LocalDateTime.now()) // Thời gian lỗi xảy ra
                .build();
    }


    /**
     * Bắt exception {@link MethodArgumentTypeMismatchException}  sai kiểu dữ liệu
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseErrorObject> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC MethodArgumentTypeMismatchException]");
        String errorMessage = Translator.toLocale("error.method_argument_type_mismatch",
                new Object[]{ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    /**
     * Bắt exception {@link InvalidArgumentException} đối số truyền vào không hợp lệ
     */
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ResponseErrorObject> handleMethodArgumentTypeMismatch(InvalidArgumentException ex, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC InvalidArgumentException]");
        String errorMessage = Translator.toLocale(ex.getMessage(),
                new Object[]{ex.getArgumentValue(), ex.getArgumentName()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    /**
     * Bắt exception {@link MissingPathVariableException} thiếu path variable
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseErrorObject> handlerMissingPathVariableException(MissingPathVariableException ex, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC MissingPathVariableException]");
        String errorMessage = Translator.toLocale("error.missing_path_variable", new Object[]{ex.getVariableName()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link ResourceNotFoundException} không tìm thấy tài nguyên
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseErrorObject> handlerResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC ResourceNotFoundException]");
        String errorMessage = Translator.toLocale(ex.getMessage(), new Object[]{ex.getResourceType()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }


    /**
     * Set dữ liệu cho đối tượng lỗi trả về cho lỗi validation
     */
    public ResponseErrorObject setResponseErrorForValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String messageError = ex.getMessage();
        int start = messageError.lastIndexOf("[");
        int end = messageError.lastIndexOf("]");
        messageError = messageError.substring(start + 1, end - 1);
        ResponseErrorObject errorObject = ResponseErrorObject.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(messageError)
                .path(request.getDescription(false).replace("uri", ""))
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        System.out.println("[DỊCH MESSAGE: " + messageError + "]");
        return errorObject;
    }

    /**
     * Bắt exception {@link MethodArgumentNotValidException} validation đối tượng
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC MethodArgumentNotValidException]");

        // Lấy lỗi đầu tiên từ danh sách lỗi
        FieldError firstFieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

        if (firstFieldError != null) {
            String fieldErrorName = firstFieldError.getField(); // Tên trường lỗi
            String errorCode = firstFieldError.getCode(); // Loại mã lỗi

            System.out.println("[FIELD ERROR: " + fieldErrorName + "]");
            System.out.println("[ERROR CODE: " + errorCode + "]");

            String errorMessage = "";
            switch (errorCode) {
                case "NotBlank", "Email", "NotNull":
                    errorMessage = Translator.toLocale(errorCode, new Object[]{fieldErrorName});
                    break;
                case "Range", "Length":
                    Object min = firstFieldError.getArguments().length > 2 ? firstFieldError.getArguments()[2] : "";
                    Object max = firstFieldError.getArguments().length > 1 ? firstFieldError.getArguments()[1] : "";
                    errorMessage = Translator.toLocale(errorCode, new Object[]{fieldErrorName, min, max});
                    break;
                default:
                    errorMessage = Translator.toLocale(errorCode, new Object[]{fieldErrorName});
                    break;
            }

            ResponseErrorObject object = ResponseErrorObject.builder()
                    .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(errorMessage)
                    .path(request.getDescription(false).replace("uri=", ""))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.badRequest().body(object);
        }

        // Trường hợp không tìm thấy lỗi nào
        return ResponseEntity.badRequest().build();
    }


    /**
     * Bắt exception {@link FileSizeExceededException} kích cỡ file quá giới hạn
     */
    @ExceptionHandler(FileSizeExceededException.class)
    public ResponseEntity<ResponseErrorObject> handlerFileSizeExceededException(FileSizeExceededException ex, WebRequest request) {
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC FileSizeExceededException]");
        String errorMessage = Translator.toLocale("error.file_size_exceeded", new Object[]{ex.getConditionSize()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        errorObject.setError(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase());
        errorObject.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
        return new ResponseEntity(errorObject, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    /**
     * Bắt exception {@link MaxUploadSizeExceededException} kích cỡ file quá giới hạn
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseErrorObject> handlerFileSizeExceededException(WebRequest request) {
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC MaxUploadSizeExceededException]");
        String errorMessage = Translator.toLocale("error.multipart_max_file_size");
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        errorObject.setError(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase());
        errorObject.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
        return new ResponseEntity<>(errorObject, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    /**
     * Bắt exception {@link InvalidFileTypeException} không đúng định dạng file
     */
    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<ResponseErrorObject> handlerInvalidFileTypeException(InvalidFileTypeException ex, WebRequest request) {
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC InvalidFileTypeException]");
        String errorMessage = Translator.toLocale("error.file_invalid_file_type", new Object[]{ex.getConditionFileType()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        errorObject.setError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        errorObject.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        return new ResponseEntity<>(errorObject, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Bắt exception {@link FileUploadingException} trùng code của các entity
     **/
    @ExceptionHandler({FileUploadingException.class})
    public ResponseEntity<ResponseErrorObject> handlerFileUploadingException(FileUploadingException ex, WebRequest request) {
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC FileUploadingException]");
        String errorMessage = Translator.toLocale(ex.getMessage());
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link DuplicateCodeException} trùng code của các entity
     **/
    @ExceptionHandler(DuplicateCodeException.class)
    public ResponseEntity<ResponseErrorObject> handlerDuplicateCodeException(DuplicateCodeException ex, WebRequest request) {
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC DuplicateCodeException]");
        String errorMessage = Translator.toLocale(ex.getMessage(), new Object[]{ex.getEntity(), ex.getEntityCode()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link DuplicateEntityRecordException}
     * */
    @ExceptionHandler(DuplicateEntityRecordException.class)
    public ResponseEntity<ResponseErrorObject> handlerDuplicateEntityRecordException(DuplicateEntityRecordException ex, WebRequest request) {
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC DuplicateEntityRecordException]");
        String errorMessage = Translator.toLocale(ex.getMessage(), new Object[]{ex.getEntityName()});
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link HttpMessageNotReadableException} lỗi chuyển đổi JSON sang Object
     * */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseErrorObject> handlerHttpMessageNotReadableException(
            WebRequest request
    ){
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC HttpMessageNotReadableException]");
        String errorMessage = Translator.toLocale("error.HttpMessageNotReadableException");
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link PathElementException} lỗi chuyển đổi JSON sang Object
     * */
    @ExceptionHandler(PathElementException.class)
    public ResponseEntity<ResponseErrorObject> handlerPathElementException(
            PathElementException ex,
            WebRequest request
    ){
        System.out.println("-----> [ĐÃ BẮT ĐƯỢC PathElementException]");
        String errorMessage = Translator.toLocale("error.PathElementException");
        ResponseErrorObject errorObject = setResponseError(errorMessage, request);
        return ResponseEntity.badRequest().body(errorObject);
    }
}
