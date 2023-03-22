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
    @Column(name = "desc", columnDefinition = "varchar")
	private String desc;

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
