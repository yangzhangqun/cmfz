package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Counter {
    private String id;
    private String title;
    private Integer count;
    private Date last_time;
    private String user_id;
    private String task_id;

}
