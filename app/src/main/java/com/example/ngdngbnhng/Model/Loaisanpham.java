package com.example.ngdngbnhng.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Loaisanpham implements Serializable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("tenloaisanpham")
@Expose
private String tenloaisanpham;
@SerializedName("hinhanhsanpham")
@Expose
private String hinhanhsanpham;

    public Loaisanpham(String id, String tenloaisanpham, String hinhanhsanpham) {
        this.id = id;
        this.tenloaisanpham = tenloaisanpham;
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTenloaisanpham() {
return tenloaisanpham;
}

public void setTenloaisanpham(String tenloaisanpham) {
this.tenloaisanpham = tenloaisanpham;
}

public String getHinhanhsanpham() {
return hinhanhsanpham;
}

public void setHinhanhsanpham(String hinhanhsanpham) {
this.hinhanhsanpham = hinhanhsanpham;
}

}