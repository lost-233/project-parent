package com.zhaoming.base.dto;

/**
 * 对象转换器
 * E 实体类
 * D DTO
 * @author hhx
 */
public interface Convert<E, D> {

    /**
     * 实体类转成Dto
     * @param e
     * @return
     */
    D entity2dto(E e);

    /**
     * Dto转实体类
     * @param d
     * @return
     */
    E dto2entity(D d);
}
