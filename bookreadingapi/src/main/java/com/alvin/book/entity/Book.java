package com.alvin.book.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/4/18.
 */
@Entity
@Table(name = "book", schema = "book")
@Data
public  class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
