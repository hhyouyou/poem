package com.djx.poem.repository;

import com.djx.poem.model.entity.PoemInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author hhyy
 * @date 2023-03-22 14:07:17
 **/
public interface PoemInfoRepository extends JpaRepository<PoemInfoEntity, Integer> {


    List<PoemInfoEntity> findAllByAuthorAndTitle(String author, String title);


    List<PoemInfoEntity> findAllByIdBetween(Integer start, Integer end);
}