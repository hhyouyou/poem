package com.djx.poem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.djx.poem.model.entity.PoemInfoEntity;
import com.djx.poem.model.eo.PoemEO;
import com.djx.poem.repository.PoemInfoRepository;
import com.djx.poem.util.JsonUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author djx
 * @date 2021/2/5 下午4:41
 */
@Slf4j
@NoArgsConstructor
public class PoemListener extends AnalysisEventListener<PoemEO> {

    private static final int BATCH_COUNT = 1000;

    private final List<PoemInfoEntity> list = new ArrayList<>(BATCH_COUNT);


    private PoemInfoRepository poemInfoRepository;

    public PoemListener(PoemInfoRepository poemInfoRepository) {
        this.poemInfoRepository = poemInfoRepository;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}, context{} ", exception.getMessage(), JsonUtil.toJsonString(context));
        exception.printStackTrace();
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }

    }

    @Override
    public void invoke(PoemEO poemData, AnalysisContext analysisContext) {

        // TODO excel导入字段为空?

        String author = StringUtils.trim(poemData.getAuthor());
        String title = StringUtils.trim(poemData.getTitle());
        String dynasty = StringUtils.trim(poemData.getDynasty());

        if (StringUtils.isBlank(title) || StringUtils.isBlank(author) || StringUtils.isBlank(dynasty) || StringUtils.isBlank(poemData.getContent())) {
            log.warn("title is null , : {}", JsonUtil.toJsonString(poemData));
            return;
        }

        PoemInfoEntity poemEntity = new PoemInfoEntity();
        poemEntity.setTitle(title);
        poemEntity.setAuthor(author);
        poemEntity.setDynasty(dynasty);

        poemEntity.setContent(StringUtils.trimToEmpty(poemData.getContent()));
        poemEntity.setTranslate(StringUtils.trimToEmpty(poemData.getTranslate()));
        poemEntity.setAnnotation(StringUtils.trimToEmpty(poemData.getAnnotation()));
        poemEntity.setAppreciate(StringUtils.trimToEmpty(poemData.getAppreciate()));
        poemEntity.setReference(StringUtils.trimToEmpty(poemData.getReference()));

        poemEntity.setType(StringUtils.trimToEmpty(poemData.getPoemType()));
        poemEntity.setStatus(0);
        poemEntity.setCreatedAt(LocalDateTime.now());
        poemEntity.setLastUpdatedAt(LocalDateTime.now());
        poemEntity.setCreatedBy(0);
        poemEntity.setLastUpdatedBy(0);


        if (checkPoemEntityRepeat(poemEntity)) {
            log.info(" 诗词 重复 data{} ", JsonUtil.toJsonString(poemData));
            return;
        }

        list.add(poemEntity);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            log.info("clear list {}", list.size());
            saveAll();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveAll();
        log.info("所有数据解析完成！");
    }


    public void saveAll() {
        poemInfoRepository.saveAll(list);
    }


    public Boolean checkPoemEntityRepeat(PoemInfoEntity poemData) {

        // 先和缓存的比较
        boolean anyMatchCache = list.stream()
                .anyMatch(e -> e.getTitle().equals(poemData.getTitle())
                        && e.getAuthor().equals(poemData.getAuthor()) && e.getContent().equals(poemData.getContent()));
        if (anyMatchCache) {
            return true;
        }
        // 数据库查重复
        List<PoemInfoEntity> poemEntityList = poemInfoRepository.findAllByAuthorAndTitle(poemData.getAuthor(), poemData.getTitle());

        // 存在重复的, 比较content是否一致
        boolean anyMatch = poemEntityList.stream()
                .anyMatch(entity -> poemData.getContent().trim().equals(entity.getContent().trim()));
        if (CollectionUtils.isNotEmpty(poemEntityList) && !anyMatch) {
            log.warn(" warn:  作者 + 标题重复,内容不重复 ____{}", JsonUtil.toJsonString(poemData));
        }

        return anyMatch;
    }


    private static final Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public static String replaceBlank(String str) {

        String dest = "";

        if (str != null) {


            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;
    }


}
