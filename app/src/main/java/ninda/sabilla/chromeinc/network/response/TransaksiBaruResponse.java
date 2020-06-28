package ninda.sabilla.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

public class TransaksiBaruResponse {

    @SerializedName("id_transaksi")
    private int id_transaksi;
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

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

}
