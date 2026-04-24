package com.nhieuhanh.__demo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Đối tượng Sinh viên chứa các thông tin cơ bản")
public class Student {

    @Schema(description = "Mã số sinh viên", example = "24520336", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "Họ và tên đầy đủ của sinh viên", example = "Huỳnh Đức Dũng")
    private String hoTen;

    public Student() {}

    public Student(String id, String hoTen) {
        this.id = id;
        this.hoTen = hoTen;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
}