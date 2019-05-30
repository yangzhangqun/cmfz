package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ExcelIgnore
    private String id;
    @Excel(name = "手机号")
    private String phone_num;
    @Excel(name = "密码")
    private String password;
    @ExcelIgnore
    private String salt;
    @Excel(name = "头像", type = 2 ,width = 40 , height = 20)
    private String head_pic;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "法号")
    private String dharma;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "标记")
    private String sign;
    @Excel(name = "状态")
    private String status;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间",format = "yyyy-MM-dd")
    private Date create_date;
}
