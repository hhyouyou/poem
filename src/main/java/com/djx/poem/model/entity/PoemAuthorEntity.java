package com.djx.poem.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author hhyy
 * @date 2023-05-06 18:41:52
 **/
@Data
@Entity
@Table(name = "poem_author")
public class PoemAuthorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private Integer id;

    /**
     * 名称
     **/
    @Column(name = "name", columnDefinition = "varchar")
    private String name;

    /**
     * 作者描述
     **/
    @Column(name = "author_desc", columnDefinition = "varchar")
    private String authorDesc;

    /**
     * 朝代
     **/
    @Column(name = "dynasty", columnDefinition = "varchar")
    private String dynasty;

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
