package bimo.syahputro.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class DaftarTransaksi {
    @SerializedName("id_transaksi")
    private String idTransaksi;
    @SerializedName("nama_customer")
    private String namaCustomer;
    @SerializedName("tanggal_masuk")
    private String tanggalMasuk;
    @SerializedName("status")
    private String status;

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
