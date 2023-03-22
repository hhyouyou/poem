package com.djx.poem.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;


/** 
 * @author hhyy
 * @date 2023-03-22 14:07:17
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
	 * ����
	**/
    @Column(name = "title", columnDefinition = "varchar")
	private String title;

	/** 
	 * ����
	**/
    @Column(name = "author", columnDefinition = "varchar")
	private String author;

	/** 
	 * ����
	**/
    @Column(name = "dynasty", columnDefinition = "varchar")
	private String dynasty;

	/** 
	 * ʫ������
	**/
    @Column(name = "content", columnDefinition = "text")
	private String content;

	/** 
	 * ����
	**/
    @Column(name = "translate", columnDefinition = "text")
	private String translate;

	/** 
	 * ע��
	**/
    @Column(name = "annotation", columnDefinition = "text")
	private String annotation;

	/** 
	 * ����
	**/
    @Column(name = "appreciate", columnDefinition = "text")
	private String appreciate;

	/** 
	 * �ο�
	**/
    @Column(name = "reference", columnDefinition = "text")
	private String reference;

	/** 
	 * ���ͣ�ʫ/��/��
	**/
    @Column(name = "type", columnDefinition = "varchar")
	private String type;

	/** 
	 * ״̬��0-Ĭ��;1-У��
	**/
    @Column(name = "status", columnDefinition = "tinyint")
	private Integer status;

	/** 
	 * ����ʱ��
	**/
    @Column(name = "created_at", columnDefinition = "timestamp")
	private LocalDateTime createdAt;

	/** 
	 * ������ʱ��
	**/
    @Column(name = "last_updated_at", columnDefinition = "timestamp")
	private LocalDateTime lastUpdatedAt;

	/** 
	 * ������
	**/
    @Column(name = "created_by", columnDefinition = "int")
	private Integer createdBy;

	/** 
	 * ��������
	**/
    @Column(name = "last_updated_by", columnDefinition = "int")
	private Integer lastUpdatedBy;

}
