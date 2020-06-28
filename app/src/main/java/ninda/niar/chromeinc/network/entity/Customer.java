package ninda.niar.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("id_customer")
    private String idCustomer;
    @SerializedName("nama_customer")
    private String namaCustomer;
    @SerializedName("no_telp")
    private String noTelp;
    @SerializedName("alamat")
    private String alamat;

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
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
