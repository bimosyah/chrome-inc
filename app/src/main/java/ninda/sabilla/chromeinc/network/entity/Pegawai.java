package ninda.sabilla.chromeinc.network.entity;

import com.google.gson.annotations.SerializedName;

public class Pegawai {
    @SerializedName("nama_pegawai")
    private String namaPegawai;

    @SerializedName("id_pegawai")
    private String idPegawai;

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }
}
