package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guru {
    private String id;
    private String dharma;
    private String head_pic;
    private String status;
    private Date create_date;
}
