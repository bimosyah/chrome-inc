package bimo.syahputro.chromeinc.network;


import bimo.syahputro.chromeinc.network.response.BarangResponse;
import bimo.syahputro.chromeinc.network.response.LoginResponse;
import bimo.syahputro.chromeinc.network.response.TransaksiBaruResponse;
import bimo.syahputro.chromeinc.network.response.TransaksiDetailResponse;
import bimo.syahputro.chromeinc.network.response.TransaksiResponse;
import bimo.syahputro.chromeinc.network.response.TransaksiUpdateStatusResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("login/dologin")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("Transaksi/getDaftarBarangMasuk")
    Call<TransaksiResponse> daftarBarangMasuk();

    @GET("barang/getBarangHarga")
    Call<BarangResponse> daftarBarang();

    @FormUrlEncoded
    @POST("transaksi/insertTransaksi")
    Call<TransaksiBaruResponse> newTransaksi(
            @Field("nama_customer") String nama_customer,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat,
            @Field("id_pegawai") String id_pegawai,
            @Field("id_status") String id_status,
            @Field("gambar") String gambar,
            @Field("id_barang") String[] id_barang,
            @Field("jumlah_barang") String[] jumlah_barang,
            @Field("estimasi") String estimasi

    );

    @GET("transaksi/getTransaksiById/{id_transaksi}")
    Call<TransaksiDetailResponse> detailBarang(
            @Path("id_transaksi") String id_transaksi
    );

    @FormUrlEncoded
    @POST("transaksi/updateStatusTransaksi")
    Call<TransaksiUpdateStatusResponse> updateStatus(
            @Field("id_transaksi") String id_transaksi,
            @Field("id_status") String id_status
    );
}
