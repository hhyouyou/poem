package com.djx.poem.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author hhyy
 * @date 2023-03-22 14:07:17
 **/
@Data
@Entity
@Table(name = "poem_tag_relation")
public class PoemTagRelationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private Integer id;

    /**
     * ʫ��id
     **/
    @Column(name = "poem_id", columnDefinition = "int")
    private Integer poemId;

    /**
     * ��ǩid
     **/
    @Column(name = "tag_id", columnDefinition = "int")
    private Integer tagId;

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
