package com.kvs.memcachedb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coords {
    private double lat;
    private double lng;
}
