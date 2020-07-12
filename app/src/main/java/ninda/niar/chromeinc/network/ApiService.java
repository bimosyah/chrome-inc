package ninda.niar.chromeinc.network;


import java.util.ArrayList;

import ninda.niar.chromeinc.network.response.BarangResponse;
import ninda.niar.chromeinc.network.response.CustomerResponse;
import ninda.niar.chromeinc.network.response.InventoryBaruResponse;
import ninda.niar.chromeinc.network.response.InventoryResponse;
import ninda.niar.chromeinc.network.response.LoginResponse;
import ninda.niar.chromeinc.network.response.TransaksiBaruResponse;
import ninda.niar.chromeinc.network.response.TransaksiDetailResponse;
import ninda.niar.chromeinc.network.response.TransaksiResponse;
import ninda.niar.chromeinc.network.response.TransaksiUpdateStatusResponse;
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
    Call<TransaksiResponse> daftarTransaksi();

    @GET("barang/getBarangHarga")
    Call<BarangResponse> daftarBarang();

    @FormUrlEncoded
    @POST("transaksi/insertTransaksi")
    Call<TransaksiBaruResponse> transaksiBaru(
            @Field("nama_customer") String nama_customer,
            @Field("no_telp") String no_telp,
            @Field("alamat") String alamat,
            @Field("id_pegawai") String id_pegawai,
            @Field("id_customer") String id_customer,
            @Field("gambar") String gambar,
            @Field("id_barang[]") ArrayList<String> id_barang,
            @Field("jumlah_barang[]") ArrayList<String> jumlah_barang
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

    @GET("Transaksi/getTransaksiByCustomerName/{nama_customer}")
    Call<TransaksiResponse> searchTransaksi(
            @Path("nama_customer") String nama_customer
    );

    @GET("customer/loadCustomer")
    Call<CustomerResponse> daftarCustomer();

    @FormUrlEncoded
    @POST("inventory/insertInventory")
    Call<InventoryBaruResponse> inventoryBaru(
            @Field("nama_tukang") String nama_tukang,
            @Field("nama_inv") String nama_inv,
            @Field("jumlah") String jumlah,
            @Field("satuan") String satuan,
            @Field("harga_beli") String harga_beli,
            @Field("keterangan") String keterangan
    );

    @GET("inventory/daftartInventory")
    Call<InventoryResponse> daftarInventory();

    @FormUrlEncoded
    @POST("inventory/updateStokInventory")
    Call<InventoryBaruResponse> updateStokBarang(
            @Field("id_inventory") String id_inventory,
            @Field("stok_digunakan") String stok_digunakan
    );

    @GET("inventory/reqStokInventory")
    Call<InventoryBaruResponse> reqStokBarang();

}
