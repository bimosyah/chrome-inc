package bimo.syahputro.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import bimo.syahputro.chromeinc.network.entity.DetailBarang;

public class DetailBarangResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("total_harga")
    private Integer totalHarga;
    @SerializedName("detail_barang")
    private List<DetailBarang> detailBarang = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Integer totalHarga) {
        this.totalHarga = totalHarga;
    }

    public List<DetailBarang> getDetailBarang() {
        return detailBarang;
    }

    public void setDetailBarang(List<DetailBarang> detailBarang) {
        this.detailBarang = detailBarang;
    }

}
