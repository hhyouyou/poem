package com.djx.poem.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;


/** 
 * @author hhyy
 * @date 2023-03-21 23:11:04
**/
@Data
@Entity
@Table(name = "poem_info")
public class PoemInfoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
	private Integer id;

	/** 
	 * 标题
	**/
    @Column(name = "title", columnDefinition = "varchar")
	private String title;

	/** 
	 * 作者
	**/
    @Column(name = "author", columnDefinition = "varchar")
	private String author;

	/** 
	 * 朝代
	**/
    @Column(name = "dynasty", columnDefinition = "varchar")
	private String dynasty;

	/** 
	 * 诗词内容
	**/
    @Column(name = "content", columnDefinition = "text")
	private String content;

	/** 
	 * 翻译
	**/
    @Column(name = "translate", columnDefinition = "text")
	private String translate;

	/** 
	 * 注释
	**/
    @Column(name = "annotation", columnDefinition = "text")
	private String annotation;

	/** 
	 * 赏析
	**/
    @Column(name = "appreciate", columnDefinition = "text")
	private String appreciate;

	/** 
	 * 参考
	**/
    @Column(name = "reference", columnDefinition = "text")
	private String reference;

	/** 
	 * 类型：诗/词/曲
	**/
    @Column(name = "type", columnDefinition = "varchar")
	private String type;

	/** 
	 * 状态：0-默认;1-校对
	**/
    @Column(name = "status", columnDefinition = "tinyint")
	private Integer status;

	/** 
	 * 创建时间
	**/
    @Column(name = "created_at", columnDefinition = "timestamp")
	private LocalDateTime createdAt;

	/** 
	 * 最后更新时间
	**/
    @Column(name = "last_updated_at", columnDefinition = "timestamp")
	private LocalDateTime lastUpdatedAt;

	/** 
	 * 创建人
	**/
    @Column(name = "created_by", columnDefinition = "int")
	private Integer createdBy;

	/** 
	 * 最后更新人
	**/
    @Column(name = "last_updated_by", columnDefinition = "int")
	private Integer lastUpdatedBy;

}
