package com.trongphu.finalintern1.util.file;

import com.trongphu.finalintern1.util.exception.uploadsfile.FileSizeExceededException;
import com.trongphu.finalintern1.util.exception.uploadsfile.InvalidFileTypeException;
import org.apache.tika.Tika;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Created by Trong Phu on 30/08/2024 08:48
 *
 * @author Trong Phu
 */
public class FileUploadUtil {

    private static final Tika tika = new Tika();

    public static final String IMAGE = "image/";

    public static boolean isImageFile(MultipartFile file, String conditionType) {
        try {
            String mimeType = tika.detect(file.getInputStream());
            return mimeType.startsWith(conditionType);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kiểm tra dữ liệu file đầu vào
     * Phương thức này thực hiện các kiểm tra sau
     * Kiểm tra kích thươc file: Ném ngoại lệ {@link FileSizeExceededException}
     * Kiểm tra định dạng file: Ném ngoại lệ {@link InvalidFileTypeException}
     *
     * @param file          File được upload, đối tượng {@link MultipartFile}.
     * @param conditionSize Kích thước tối đa cho phép của file, tính bằng MB.
     * @param conditionType Loại MIME type mà file phải tuân theo, ví dụ: "image/png".
     * @return {@code true} nếu file hợp lệ, ngược lại {@code false}.
     * @throws FileSizeExceededException nếu kích thước file vượt quá giới hạn cho phép.
     * @throws InvalidFileTypeException  nếu kiểu file không khớp với kiểu mong muốn.
     */
    public static boolean checkFileUpload(MultipartFile file, int conditionSize, String conditionType) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        if (file.getSize() > conditionSize * 1024L * 1024L) {
            throw new FileSizeExceededException("error.file_size_exceeded", conditionSize);
        }
        if (!isImageFile(file, conditionType)) {
            throw new InvalidFileTypeException("error.file_invalid_file_type", conditionType);
        }
        return true;
    }

    /**
     * Lưu file vào thư mục uploads
     */
    public static String uploadFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String newFileName = uuid + "_" + StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path destination = Paths.get(uploadDir.toString(), newFileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        }
        return null;
    }

    /**
     * Xóa file theo tên
     */
    public static boolean deleteFileByName(String fileName) {
        Path uploadDir = Paths.get("uploads");
        Path destination = Paths.get(uploadDir.toString(), fileName);
        File file = new File(String.valueOf(destination));
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


}
