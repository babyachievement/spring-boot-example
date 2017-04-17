package com.alvin.financial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/4/17.
 */
@Entity
public class Stock {
    @GeneratedValue
    @Id
    private long id;
}
