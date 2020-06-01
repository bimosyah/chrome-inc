package bimo.syahputro.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import bimo.syahputro.chromeinc.network.entity.DaftarTransaksi;

public class TransaksiResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("daftar_barang")
    private List<DaftarTransaksi> daftarTransaksi = null;

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

    public List<DaftarTransaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }

    public void setDaftarTransaksi(List<DaftarTransaksi> daftarTransaksi) {
        this.daftarTransaksi = daftarTransaksi;
    }
}
