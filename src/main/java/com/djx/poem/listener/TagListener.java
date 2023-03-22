package com.djx.poem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.djx.poem.model.entity.PoemTagEntity;
import com.djx.poem.model.eo.PoemEO;
import com.djx.poem.repository.PoemTagRepository;
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
public class TagListener extends AnalysisEventListener<PoemEO> {

    private static final int BATCH_COUNT = 5000;


    private final Map<String, PoemTagEntity> tagMap = new HashMap<>(BATCH_COUNT);

    private Set<String> tagSet = new HashSet<>();

    private PoemTagRepository poemTagRepository;

    public TagListener(PoemTagRepository poemTagRepository) {
        this.poemTagRepository = poemTagRepository;
        init();
    }

    public void init() {
        List<PoemTagEntity> all = poemTagRepository.findAll();
        tagSet = all.stream().map(PoemTagEntity::getTag).collect(Collectors.toSet());
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

        String types = poemData.getPoemType();

        if (StringUtils.isBlank(types)) {
            log.info(" typeis null data{} ", JsonUtil.toJsonString(poemData));
            return;
        }

        String[] s = StringUtils.trim(types).split(" ");

        Arrays.stream(s).distinct().map(StringUtils::trim)
                .filter(str -> !tagSet.contains(str))
                .forEach(tag -> {

                    PoemTagEntity tagEntity = new PoemTagEntity();
                    tagEntity.setTag(tag);
                    tagEntity.setTagDesc("");
                    tagEntity.setStatus(0);
                    tagEntity.setCreatedAt(LocalDateTime.now());
                    tagEntity.setLastUpdatedAt(LocalDateTime.now());
                    tagEntity.setCreatedBy(0);
                    tagEntity.setLastUpdatedBy(0);

                    tagMap.put(tag, tagEntity);
                    tagSet.add(tag);

                });


        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (tagMap.size() >= BATCH_COUNT) {
            log.info("clear list {}", tagMap.size());
            saveAll();
            // 存储完成清理 list
            tagMap.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveAll();
        log.info("所有数据解析完成！");
    }


    public void saveAll() {
        poemTagRepository.saveAll(tagMap.values());
    }


}
