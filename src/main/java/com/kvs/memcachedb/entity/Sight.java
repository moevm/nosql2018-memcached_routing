package com.kvs.memcachedb.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Sight implements Serializable {
    private String objectName;
    private String ensembleName;
    private String date;
    private String authors;
    private String address;
    private String district;
    private String base;
    private String description;
}
