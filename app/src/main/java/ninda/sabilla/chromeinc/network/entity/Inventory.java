package ninda.sabilla.chromeinc.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Inventory implements Parcelable {
    @SerializedName("id_inventory")
    private String idInventory;
    @SerializedName("no_inv")
    private String noInv;
    @SerializedName("nama_tukang")
    private String namaTukang;
    @SerializedName("tanggal")
    private String tanggal;
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
    @SerializedName("status")
    private String status;

    protected Inventory(Parcel in) {
        idInventory = in.readString();
        noInv = in.readString();
        namaTukang = in.readString();
        tanggal = in.readString();
        namaInv = in.readString();
        jumlah = in.readString();
        satuan = in.readString();
        hargaBeli = in.readString();
        keterangan = in.readString();
        status = in.readString();
    }

    public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel in) {
            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };

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

    public String getNamaTukang() {
        return namaTukang;
    }

    public void setNamaTukang(String namaTukang) {
        this.namaTukang = namaTukang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idInventory);
        parcel.writeString(noInv);
        parcel.writeString(namaTukang);
        parcel.writeString(tanggal);
        parcel.writeString(namaInv);
        parcel.writeString(jumlah);
        parcel.writeString(satuan);
        parcel.writeString(hargaBeli);
        parcel.writeString(keterangan);
        parcel.writeString(status);
    }
}
