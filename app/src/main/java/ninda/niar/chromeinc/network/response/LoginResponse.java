package ninda.niar.chromeinc.network.response;

import com.google.gson.annotations.SerializedName;

import ninda.niar.chromeinc.network.entity.Pegawai;

public class LoginResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("pegawai")
    private Pegawai pegawai;

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

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }

}
