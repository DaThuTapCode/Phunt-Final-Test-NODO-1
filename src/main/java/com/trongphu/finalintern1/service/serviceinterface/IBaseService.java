package com.trongphu.finalintern1.service.serviceinterface;

import com.trongphu.finalintern1.util.PaginationObject;
import org.springframework.data.domain.Page;


/**
 * Created by Trong Phu on 31/08/2024 14:03
 * {@link IBaseService} là Generic Interface để quản lý chung các thao các CRUD cơ bản của một Entity
 * @param <E> Entity
 * @param <ID> Kiểu dữ liệu ID của Entity
 * @param <RQ> Đối tượng requestDTO
 * @param <RS> Đối tượng responseDTO
 * @author Trong Phu
 */
public interface IBaseService<E, ID, RQ, RS> {
    /**
     * Lấy phân trang các Entity
     *
     * @param paginationObject là đối tượng hỗ trợ phân trang
     */
    Page<RS> getPage(PaginationObject paginationObject) throws ClassNotFoundException;

    /**
     * Lấy Entity theo id
     *
     * @param id khóa chính của Entity
     */
    RS getById(ID id);

    /**
     * Xóa mềm Entity
     * @param id     khóa chính Entity
     * @param status trạng thái Entity
     */
    void softDelete(ID id, int status);

    /**
     * Thêm 1 Entity mới
     *
     * @param requestDTO đối tượng DTO hứng dữ liệu từ request
     */
    RS createNew(RQ requestDTO);

    /**
     * Update Entity theo id
     *
     * @param id         khóa chính Entity
     * @param requestDTO đối tượng DTO hứng dữ liệu từ request
     */
    RS update(ID id, RQ requestDTO);
}
