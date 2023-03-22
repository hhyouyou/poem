package com.djx.poem.model.eo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 接收诗词导入的 excel object
 *
 * @author djx
 * @date 2021/2/7 下午1:35
 */
@Data
public class PoemEO {

    /**
     * 序号，无意义
     */
    @ExcelProperty("id")
    private Integer id;

    /**
     * 诗歌标题
     */
    @ExcelProperty("title")
    private String title;

    /**
     * 作者
     */
    @ExcelProperty("author")
    private String author;

    /**
     * 不清楚什么含义
     * 都为空值
     */
    @ExcelProperty("peruid")
    private Integer peruId;

    /**
     * 朝代
     */
    @ExcelProperty("dynasty")
    private String dynasty;

    /**
     * 诗词正文
     */
    @ExcelProperty("content")
    private String content;

    /**
     * 诗词翻译
     */
    @ExcelProperty("translate")
    private String translate;

    /**
     * 诗词注释
     */
    @ExcelProperty("annotation")
    private String annotation;

    /**
     * 诗词赏析
     */
    @ExcelProperty("appreciate")
    private String appreciate;

    /**
     * 参考
     */
    @ExcelProperty("reference")
    private String reference;

    /**
     * 诗词类型
     * TODO 转list
     */
    @ExcelProperty("poem_type")
    private String poemType;

}
