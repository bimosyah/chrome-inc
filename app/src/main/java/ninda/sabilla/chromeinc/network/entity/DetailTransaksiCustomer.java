package ninda.sabilla.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class DetailTransaksiCustomer {
    @SerializedName("id_transaksi")
    private String idTransaksi;
    @SerializedName("nama_customer")
    private String namaCustomer;
    @SerializedName("no_telp")
    private String noTelp;
    @SerializedName("alamat")
    private String alamat;

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

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}
