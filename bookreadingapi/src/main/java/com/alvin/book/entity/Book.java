package com.alvin.book.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/18.
 */
@Entity
@Table(name = "book")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "数据库自动生成的ID")
    private Long id;
    @ApiModelProperty(notes = "读者姓名")
    private String reader;
    @ApiModelProperty(notes = "ISBN号码")
    private String isbn;
    @ApiModelProperty(notes = "图书标题")
    private String title;
    @ApiModelProperty(notes = "作者")
    private String author;
    @ApiModelProperty(notes = "描述")
    private String description;

    // Hibernate JAP 字段自动添加下划线问题
    // http://blog.csdn.net/xiaozaq/article/details/70157119
    // org.joda.time.DateTime, org.joda.time.LocalDateTime, java.util.Date, java.lang.Long, long
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;
}
