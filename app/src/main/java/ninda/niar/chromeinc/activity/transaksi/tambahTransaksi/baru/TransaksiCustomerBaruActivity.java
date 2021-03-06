package ninda.niar.chromeinc.activity.transaksi.tambahTransaksi.baru;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ninda.niar.chromeinc.R;
import ninda.niar.chromeinc.activity.dashboard.DashboardActivity;
import ninda.niar.chromeinc.activity.transaksi.tambahTransaksi.baru.fragment.BarangFragment;
import ninda.niar.chromeinc.activity.transaksi.tambahTransaksi.baru.fragment.CheckoutFragment;
import ninda.niar.chromeinc.activity.transaksi.tambahTransaksi.baru.fragment.CustomerFragment;
import ninda.niar.chromeinc.network.ApiClient;
import ninda.niar.chromeinc.network.ApiService;
import ninda.niar.chromeinc.network.response.TransaksiBaruResponse;
import ninda.niar.chromeinc.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiCustomerBaruActivity extends AppCompatActivity implements CustomerFragment.OnDataPassCustomer,
        BarangFragment.OnDataPassBarang, CheckoutFragment.OnDataPassCheckout {

    public static String ID_CUSTOMER = "ID_CUSTOMER";
    public static String NAMA_CUSTOMER = "NAMA_CUSTOMER";
    public static String ALAMAT_CUSTOMER = "ALAMAT_CUSTOMER";
    public static String NOTELP_CUSTOMER = "NOTELP_CUSTOMER";
    public static String BARANGLIST = "BARANGLIST";
    public static String IDBARANGLIST = "IDBARANGLIST";
    public static String JUMLAHBARANGLIST = "JUMLAHBARANGLIST";
    public static String HARGABARANGLIST = "HARGABARANGLIST";

    Fragment fragmentCustomer, fragmentBarang, fragmentCheckout;
    ImageView ivKiri, ivTengah, ivKanan;
    TextView tvKiri, tvTengah, tvKanan, tvBtnNext;
    int posisi_fragment = 1;
    LinearLayout btnBack, btnNext;
    String id_customer = "";
    String namaCustomer, alamatCustomer, notelpCustomer;
    Bitmap bitmapBarang;
    String imageConvert;
    ArrayList<String> barangList;
    ArrayList<String> idbarangList;
    ArrayList<String> jumlahBarangList;
    ArrayList<String> hargaBarangList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Transaksi Baru");
        }

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        init();
        cekCustomerLama();

        openFragment(fragmentCustomer);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posisi_fragment == 1) {
                    callCustomerData();
                    if (!namaCustomer.equals("") || !alamatCustomer.equals("") || !notelpCustomer.equals("")) {
                        posisi_fragment = 2;

                        openFragment(fragmentBarang);

                        tvKiri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.transaksi_header));
                        tvTengah.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        ivKiri.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness, getApplicationContext().getTheme()));
                    } else {
                        Snackbar.make(btnNext, "Data customer harus diisi", Snackbar.LENGTH_SHORT)
                                .show();
                    }

                } else if (posisi_fragment == 2) {
                    callBarangData();

                    if (barangList.size() != 0) {
                        posisi_fragment = 3;
                        openFragment(fragmentCheckout);
                        passCustomerandBarangDatatoCheckout();

                        tvBtnNext.setText("Buat Transaksi");
                        tvTengah.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.transaksi_header));
                        tvKanan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        ivTengah.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness, getApplicationContext().getTheme()));
                    } else {
                        Snackbar.make(btnNext, "Data barang harus diisi", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                } else if (posisi_fragment == 3) {
                    callCheckoutData();
                    if (bitmapBarang != null) {
                        imageConvert = imageToString();
                        transaksiBaru();
                    } else {
                        Snackbar.make(btnNext, "Gambar barang harus diisi", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_transaksi, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callCustomerData() {
        CustomerFragment fragment = (CustomerFragment) getSupportFragmentManager().findFragmentById(R.id.container_transaksi);
        if (fragment != null) {
            fragment.passData();
        }
    }

    private void callBarangData() {
        BarangFragment fragment = (BarangFragment) getSupportFragmentManager().findFragmentById(R.id.container_transaksi);
        if (fragment != null) {
            fragment.passData();
        }
    }

    private void callCheckoutData() {
        CheckoutFragment fragment = (CheckoutFragment) getSupportFragmentManager().findFragmentById(R.id.container_transaksi);
        if (fragment != null) {
            fragment.passData();
        }
    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiService.class);
        tvBtnNext = findViewById(R.id.tv_btn_next);
        ivKiri = findViewById(R.id.iv_posisi_kiri);
        ivTengah = findViewById(R.id.iv_posisi_tengah);
        tvKanan = findViewById(R.id.tv_posisi_kanan);
        tvKiri = findViewById(R.id.tv_posisi_kiri);
        tvTengah = findViewById(R.id.tv_posisi_tengah);
        ivKanan = findViewById(R.id.iv_posisi_kanan);
        fragmentCustomer = new CustomerFragment();
        fragmentBarang = new BarangFragment();
        fragmentCheckout = new CheckoutFragment();
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
    }

    private void cekCustomerLama() {
        //cek data customer lama exist or not
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_customer = bundle.getString(ID_CUSTOMER);
            String nama_customer = bundle.getString(NAMA_CUSTOMER);
            String alamat_customer = bundle.getString(ALAMAT_CUSTOMER);
            String notelp_customer = bundle.getString(NOTELP_CUSTOMER);

            Bundle bundleCustomer = new Bundle();
            bundleCustomer.putString(NAMA_CUSTOMER, nama_customer);
            bundleCustomer.putString(ALAMAT_CUSTOMER, alamat_customer);
            bundleCustomer.putString(NOTELP_CUSTOMER, notelp_customer);
            fragmentCustomer.setArguments(bundleCustomer);
        }
    }

    private void passCustomerandBarangDatatoCheckout() {
        Bundle bundle = new Bundle();
        bundle.putString(NAMA_CUSTOMER, namaCustomer);
        bundle.putString(ALAMAT_CUSTOMER, alamatCustomer);
        bundle.putString(NOTELP_CUSTOMER, notelpCustomer);
        bundle.putStringArrayList(BARANGLIST, barangList);
        bundle.putStringArrayList(IDBARANGLIST, idbarangList);
        bundle.putStringArrayList(JUMLAHBARANGLIST, jumlahBarangList);
        bundle.putStringArrayList(HARGABARANGLIST, hargaBarangList);
        fragmentCheckout.setArguments(bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void passDataCustomer(String nama, String alamat, String notelp) {
        namaCustomer = nama;
        alamatCustomer = alamat;
        notelpCustomer = notelp;
    }

    @Override
    public void passDataBarang(ArrayList<String> barangList, ArrayList<String> idbarangList, ArrayList<String> jumlahBarangList, ArrayList<String> hargaBarangList) {
        this.barangList = barangList;
        this.idbarangList = idbarangList;
        this.jumlahBarangList = jumlahBarangList;
        this.hargaBarangList = hargaBarangList;
    }

    @Override
    public void passDataCheckout(Bitmap bitmap) {
        this.bitmapBarang = bitmap;
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapBarang.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void transaksiBaru() {
        apiService.transaksiBaru(
                namaCustomer, notelpCustomer,
                alamatCustomer, Preference.getIdUser(getApplicationContext()),
                id_customer, imageConvert,
                idbarangList, jumlahBarangList).enqueue(new Callback<TransaksiBaruResponse>() {
            @Override
            public void onResponse(Call<TransaksiBaruResponse> call, Response<TransaksiBaruResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Snackbar.make(btnNext, "Berhasil", Snackbar.LENGTH_SHORT)
                                    .show();
                            nota(response.body().getId_transaksi());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentToBeNewRoot = new Intent(TransaksiCustomerBaruActivity.this, DashboardActivity.class);
                                    ComponentName cn = intentToBeNewRoot.getComponent();
                                    Intent mainIntent = Intent.makeRestartActivityTask(cn);

                                    startActivity(mainIntent);
                                }
                            }, 1000);
                        } else {
                            Snackbar.make(btnNext, "gagal", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiBaruResponse> call, Throwable t) {
                Snackbar.make(btnNext, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    private void nota(int id_transaksi){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat formatter_selesai = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        Date date_selesai = new Date();
        date_selesai = addDays(date_selesai,4);

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawText("Chrome.Inc",20,30, paint);
        canvas.drawText("Karanglo Indah Blok U-1 Kota Malang",20,50, paint);
        canvas.drawText("Telp. 081252411618",20,70, paint);

        canvas.drawText("Id Transaksi",20,105, paint);
        canvas.drawText("Tanggal",20,125, paint);
        canvas.drawText("Nama Customer",20,145, paint);
        canvas.drawText(": " + id_transaksi,150,105, paint);
        canvas.drawText(": " + formatter.format(date),150,125, paint);
        canvas.drawText(": " + namaCustomer,150,145, paint);

        canvas.drawText("--------------------------------------------------------------------------------------------------",20,180, paint);
        canvas.drawText("Nama Barang",20,205, paint);
        canvas.drawText("Qty",150,205, paint);
        canvas.drawText("Harga",200,205, paint);
        canvas.drawText("Total",270,205, paint);
        canvas.drawText("--------------------------------------------------------------------------------------------------",20,230, paint);

        int posisi_y = 250;
        int harga_total = 0;
        for (int i = 0; i < barangList.size(); i++){
            int total = Integer.parseInt(jumlahBarangList.get(i)) * Integer.parseInt(hargaBarangList.get(i));
            canvas.drawText(barangList.get(i),20,posisi_y, paint);
            canvas.drawText(jumlahBarangList.get(i),150,posisi_y, paint);
            canvas.drawText(hargaBarangList.get(i),200,posisi_y, paint);
            canvas.drawText(String.valueOf(total),270,posisi_y, paint);
            posisi_y += 20;
            harga_total += total;
        }

        canvas.drawText("--------------------------------------------------------------------------------------------------",20,posisi_y, paint);
        canvas.drawText("Total",20,posisi_y + 20, paint);
        canvas.drawText(String.valueOf(harga_total),270,posisi_y + 20, paint);
        canvas.drawText("Estimasi Barang Selesai",20,posisi_y + 40, paint);
        canvas.drawText(formatter_selesai.format(date_selesai),170,posisi_y + 40, paint);


        document.finishPage(page);

        String filename = formatter.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),"/"+filename+".pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            Log.d("Nota", "nota: Berhasil");
        } catch (IOException e){
            e.printStackTrace();
            Log.d("Nota", "nota: gagal ");
        }

        document.close();

    }
}