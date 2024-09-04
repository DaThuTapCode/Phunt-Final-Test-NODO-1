package com.trongphu.finalintern1.util;

import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.util.exception.InvalidArgumentException;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trong Phu on 02/09/2024 08:38
 * Đối tượng hỗ trợ các tham số phục vụ phân trang
 *
 * @author Trong Phu
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationObject {
    @NotNull(message = "NotNull")
    private Integer page;

    @NotNull(message = "NotNull")
    private Integer size;
    private String sortBy;
    private String direction;

    private Class<?> entityClass;

    private static final int MAX_SIZE = 100;

    private void validate() {

        if (this.page < 0) {
            throw new InvalidArgumentException("error.InvalidArgumentException", this.page + "", "page");
        }
        if (this.size <= 0) {
            throw new InvalidArgumentException("error.InvalidArgumentException", this.size + "", "size");
        }
        if (this.size > MAX_SIZE) {
            throw new InvalidArgumentException("error.InvalidArgumentException", this.size + "", "size");
        }

        List<String> validFields = getValidFields();

        if (this.sortBy != null && !validFields.contains(this.sortBy)) {
            throw new InvalidArgumentException("error.InvalidArgumentException", this.sortBy, "sortBy");
        }
        if (this.direction == null) {
           this.direction = "ASC";
        }
        if (!this.direction.equalsIgnoreCase("asc") && !this.direction.equalsIgnoreCase("desc")) {
            throw new InvalidArgumentException("error.InvalidArgumentException", this.direction, "direction");
        }
    }

    public Pageable toPageable()  {
        validate();
        Sort.Direction soDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if (this.sortBy == null || this.sortBy.isEmpty()) {
            return PageRequest.of(this.page, this.size);
        }
        return PageRequest.of(this.page, this.size, Sort.by(soDirection, sortBy));
    }


    public List<String> getValidFields(){
        return Arrays.stream(this.entityClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }

}
