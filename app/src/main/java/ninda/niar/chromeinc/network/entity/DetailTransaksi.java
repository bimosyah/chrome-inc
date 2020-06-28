package ninda.niar.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class DetailTransaksi {
    @SerializedName("id_detail_transaksi")
    private String idDetailTransaksi;
    @SerializedName("id_transaksi")
    private String idTransaksi;
    @SerializedName("nama_barang")
    private String namaBarang;
    @SerializedName("jumlah_barang")
    private String jumlahBarang;
    @SerializedName("harga_satuan")
    private String hargaSatuan;
    @SerializedName("harga_total")
    private String hargaTotal;
    @SerializedName("estimasi")
    private String estimasi;

    public String getIdDetailTransaksi() {
        return idDetailTransaksi;
    }

    public void setIdDetailTransaksi(String idDetailTransaksi) {
        this.idDetailTransaksi = idDetailTransaksi;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(String hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public String getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(String hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

}
