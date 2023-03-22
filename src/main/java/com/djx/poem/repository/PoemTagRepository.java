package com.djx.poem.repository;

import com.djx.poem.model.entity.PoemTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author hhyy
 * @date 2023-03-22 14:07:17
 **/
public interface PoemTagRepository extends JpaRepository<PoemTagEntity, Integer> {
}