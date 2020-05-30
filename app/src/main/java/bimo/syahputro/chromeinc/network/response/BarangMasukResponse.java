package bimo.syahputro.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import bimo.syahputro.chromeinc.network.entity.DaftarBarang;

public class BarangMasukResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("daftar_barang")
    private List<DaftarBarang> daftarBarang = null;

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

    public List<DaftarBarang> getDaftarBarang() {
        return daftarBarang;
    }

    public void setDaftarBarang(List<DaftarBarang> daftarBarang) {
        this.daftarBarang = daftarBarang;
    }
}
