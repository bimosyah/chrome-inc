package bimo.syahputro.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import bimo.syahputro.chromeinc.network.entity.DetailTransaksi;
import bimo.syahputro.chromeinc.network.entity.DetailTransaksiCustomer;

public class TransaksiDetailResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("total_harga")
    private Integer totalHarga;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("detail_customer")
    private List<DetailTransaksiCustomer> detailTransaksiCustomer = null;
    @SerializedName("detail_barang")
    private List<DetailTransaksi> detailTransaksi = null;

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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public List<DetailTransaksiCustomer> getDetailTransaksiCustomer() {
        return detailTransaksiCustomer;
    }

    public void setDetailTransaksiCustomer(List<DetailTransaksiCustomer> detailTransaksiCustomer) {
        this.detailTransaksiCustomer = detailTransaksiCustomer;
    }

    public List<DetailTransaksi> getDetailTransaksi() {
        return detailTransaksi;
    }

    public void setDetailTransaksi(List<DetailTransaksi> detailTransaksi) {
        this.detailTransaksi = detailTransaksi;
    }

}
