package ninda.niar.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ninda.niar.chromeinc.network.entity.Barang;

public class BarangResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("barang_harga")
    private List<Barang> barangHarga = null;

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

    public List<Barang> getBarangHarga() {
        return barangHarga;
    }

    public void setBarangHarga(List<Barang> barangHarga) {
        this.barangHarga = barangHarga;
    }

}
