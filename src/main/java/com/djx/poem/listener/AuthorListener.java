package com.djx.poem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.djx.poem.model.entity.PoemAuthorEntity;
import com.djx.poem.model.eo.PoemEO;
import com.djx.poem.repository.PoemAuthorRepository;
import com.djx.poem.util.JsonUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 作者信息导入
 *
 * @author djx
 * @date 2021/2/5 下午4:41
 */
@Slf4j
@NoArgsConstructor
public class AuthorListener extends AnalysisEventListener<PoemEO> {

    private static final int BATCH_COUNT = 5000;


    private final Map<String, PoemAuthorEntity> authorMap = new HashMap<>(BATCH_COUNT);

    private Set<String> authorSet = new HashSet<>();

    private PoemAuthorRepository poemAuthorRepository;

    public AuthorListener(PoemAuthorRepository poemAuthorRepository) {
        this.poemAuthorRepository = poemAuthorRepository;
        init();
    }

    public void init() {
        List<PoemAuthorEntity> all = poemAuthorRepository.findAll();
        authorSet = all.stream().map(PoemAuthorEntity::getName).collect(Collectors.toSet());
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}, context{} ", exception.getMessage(), JsonUtil.toJsonString(context));
        exception.printStackTrace();
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(), excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }

    }

    @Override
    public void invoke(PoemEO poemData, AnalysisContext analysisContext) {

        // TODO excel导入字段为空?

        String author = poemData.getAuthor();
        String dynasty = poemData.getDynasty();

        if (StringUtils.isBlank(author) || StringUtils.isBlank(dynasty)) {
            log.info(" author or dynasty is null data{} ", JsonUtil.toJsonString(poemData));
            return;
        }

        if (authorSet.contains(author)) {
            log.info(" author is exist {}", author);
            return;
        }

        PoemAuthorEntity authorEntity = new PoemAuthorEntity();
        authorEntity.setName(StringUtils.trim(author));
        authorEntity.setAuthorDesc("");
        authorEntity.setDynasty(StringUtils.trim(dynasty));
        authorEntity.setCreatedAt(LocalDateTime.now());
        authorEntity.setLastUpdatedAt(LocalDateTime.now());
        authorEntity.setCreatedBy(0);
        authorEntity.setLastUpdatedBy(0);


        authorMap.put(author, authorEntity);
        authorSet.add(author);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (authorMap.size() >= BATCH_COUNT) {
            log.info("clear list {}", authorMap.size());
            saveAll();
            // 存储完成清理 list
            authorMap.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveAll();
        log.info("所有数据解析完成！");
    }


    public void saveAll() {
        poemAuthorRepository.saveAll(authorMap.values());
    }


}
