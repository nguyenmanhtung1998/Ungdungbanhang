package com.example.ngdngbnhng.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sanpham implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tensanpham")
    @Expose
    private String tensanpham;
    @SerializedName("giasanpham")
    @Expose
    private int giasanpham;
    @SerializedName("hinhsanpham")
    @Expose
    private String hinhsanpham;
    @SerializedName("motasanpham")
    @Expose
    private String motasanpham;

    public Sanpham(String id, String tensanpham, int giasanpham, String hinhsanpham, String motasanpham, String idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.hinhsanpham = hinhsanpham;
        this.motasanpham = motasanpham;
        this.idsanpham = idsanpham;
    }

    @SerializedName("idsanpham")
    @Expose

    private String idsanpham;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhsanpham() {
        return hinhsanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        this.hinhsanpham = hinhsanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

}