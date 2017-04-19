package com.alvin.financial.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/4/17.
 */
@Entity
@Data
public class Stock {
    @GeneratedValue
    @Id
    private long id;
}
