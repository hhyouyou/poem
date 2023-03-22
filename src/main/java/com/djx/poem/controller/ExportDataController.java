package com.djx.poem.controller;

import com.alibaba.excel.EasyExcel;
import com.djx.poem.listener.AuthorListener;
import com.djx.poem.listener.PoemListener;
import com.djx.poem.listener.TagListener;
import com.djx.poem.model.entity.PoemInfoEntity;
import com.djx.poem.model.entity.PoemTagEntity;
import com.djx.poem.model.entity.PoemTagRelationEntity;
import com.djx.poem.model.eo.PoemEO;
import com.djx.poem.repository.PoemAuthorRepository;
import com.djx.poem.repository.PoemInfoRepository;
import com.djx.poem.repository.PoemTagRelationRepository;
import com.djx.poem.repository.PoemTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongjingxi
 * @date 2023/3/22
 */
@Slf4j
@RestController
@RequestMapping("/import")
public class ExportDataController {


    private final PoemAuthorRepository poemAuthorRepository;

    private final PoemTagRepository poemTagRepository;

    private final PoemInfoRepository poemInfoRepository;

    private final PoemTagRelationRepository poemTagRelationRepository;

    @Autowired
    public ExportDataController(PoemAuthorRepository poemAuthorRepository, PoemTagRepository poemTagRepository, PoemInfoRepository poemInfoRepository, PoemTagRelationRepository poemTagRelationRepository) {
        this.poemAuthorRepository = poemAuthorRepository;
        this.poemTagRepository = poemTagRepository;
        this.poemInfoRepository = poemInfoRepository;
        this.poemTagRelationRepository = poemTagRelationRepository;
    }


    @PostMapping("/author")
    public String uploadFile1(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        if (uploadFile.isEmpty()) {
            return "empty";
        }
        String fileName = uploadFile.getOriginalFilename();

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(uploadFile.getInputStream(), PoemEO.class, new AuthorListener(poemAuthorRepository))
                .sheet()
                .doRead();


        return fileName;
    }

    @PostMapping("/tag")
    public String uploadFile2(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        if (uploadFile.isEmpty()) {
            return "empty";
        }
        String fileName = uploadFile.getOriginalFilename();

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(uploadFile.getInputStream(), PoemEO.class, new TagListener(poemTagRepository))
                .sheet()
                .doRead();


        return fileName;
    }

    @PostMapping("/poem")
    public String uploadFile3(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        if (uploadFile.isEmpty()) {
            return "empty";
        }
        String fileName = uploadFile.getOriginalFilename();

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(uploadFile.getInputStream(), PoemEO.class, new PoemListener(poemInfoRepository))
                .sheet()
                .doRead();


        return fileName;
    }


    @PostMapping("/relation")
    public void createRelation() {

        Map<String, Integer> tagMap = poemTagRepository.findAll().stream()
                .collect(Collectors.toMap(PoemTagEntity::getTag, PoemTagEntity::getId));

        List<PoemInfoEntity> poemInfoEntities;
        for (int i = 0; i <= 350000; i = i + 1000) {
            poemInfoEntities = poemInfoRepository.findAllByIdBetween(i, i + 999);


            List<PoemTagRelationEntity> relationEntities = poemInfoEntities.stream().filter(e -> StringUtils.isNotBlank(e.getType())).flatMap(e -> {
                String types = e.getType();
                String[] s = StringUtils.trim(types).split(" ");

                return Arrays.stream(s).distinct().map(StringUtils::trim)
                        .map(tag -> {

                            PoemTagRelationEntity relationEntity = new PoemTagRelationEntity();
                            relationEntity.setPoemId(e.getId());
                            relationEntity.setTagId(tagMap.get(tag));
                            relationEntity.setCreatedAt(LocalDateTime.now());
                            relationEntity.setLastUpdatedAt(LocalDateTime.now());
                            relationEntity.setCreatedBy(0);
                            relationEntity.setLastUpdatedBy(0);
                            return relationEntity;
                        });

            }).collect(Collectors.toList());

            poemTagRelationRepository.saveAll(relationEntities);
        }

    }


}
