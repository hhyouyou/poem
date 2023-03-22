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
@Table(name = "poem_tag")
public class PoemTagEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
	private Integer id;

	/** 
	 * ʫ�ʱ�ǩ
	**/
    @Column(name = "tag", columnDefinition = "varchar")
	private String tag;

	/** 
	 * ��ǩ����
	**/
    @Column(name = "tag_desc", columnDefinition = "varchar")
	private String tagDesc;

	/** 
	 * ״̬��0-Ĭ��
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
