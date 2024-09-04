package com.trongphu.finalintern1.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Trong Phu on 28/08/2024 22:25
 *
 * @author Trong Phu
 */
public interface BaseMapper <E, D>{
    /**
     * Chuyển đổi Entity sang DTO
     * @param e Entity cần chuyển đổi
     * */
    D toDTO(E e);

    /**
     * Chuyển đổi danh sách Entity sang danh sách DTO
     * @param listE danh sách Entity cần chuyển đổi
     * */
    List<D> listToDTO(List<E> listE);

    /**
     * Chuyển đổi DTO sang Entity
     * @param d DTO cần chuyển đổi
     * */
    E toEntity(D d);

    /**
     * Chuyển đổi danh sách DTO sang danh sách Entity
     * @param d Danh sách DTO cần chuyển đổi
     * */
    List<E> toListEntity(List<D> d);
}
