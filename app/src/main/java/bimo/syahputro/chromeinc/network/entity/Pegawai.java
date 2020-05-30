package bimo.syahputro.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class Pegawai {
    @SerializedName("nama_pegawai")
    private String namaPegawai;

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }
}
