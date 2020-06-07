package bimo.syahputro.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class Inventory {
    @SerializedName("id_inventory")
    private String idInventory;
    @SerializedName("no_inv")
    private String noInv;
    @SerializedName("nama_inv")
    private String namaInv;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("satuan")
    private String satuan;
    @SerializedName("harga_beli")
    private String hargaBeli;
    @SerializedName("keterangan")
    private String keterangan;

    public String getIdInventory() {
        return idInventory;
    }

    public void setIdInventory(String idInventory) {
        this.idInventory = idInventory;
    }

    public String getNoInv() {
        return noInv;
    }

    public void setNoInv(String noInv) {
        this.noInv = noInv;
    }

    public String getNamaInv() {
        return namaInv;
    }

    public void setNamaInv(String namaInv) {
        this.namaInv = namaInv;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
