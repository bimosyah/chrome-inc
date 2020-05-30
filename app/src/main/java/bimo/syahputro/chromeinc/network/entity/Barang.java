package bimo.syahputro.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class Barang {

    @SerializedName("id_barang")
    private String idBarang;
    @SerializedName("nama_barang")
    private String namaBarang;
    @SerializedName("total_harga")
    private String totalHarga;

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

}
