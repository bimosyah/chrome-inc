package ninda.niar.chromeinc.activity.transaksi.detailTransaksi;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ninda.niar.chromeinc.R;
import ninda.niar.chromeinc.network.ApiClient;
import ninda.niar.chromeinc.network.ApiService;
import ninda.niar.chromeinc.network.entity.DetailTransaksi;
import ninda.niar.chromeinc.network.entity.DetailTransaksiCustomer;
import ninda.niar.chromeinc.network.response.TransaksiDetailResponse;
import ninda.niar.chromeinc.network.response.TransaksiUpdateStatusResponse;
import ninda.niar.chromeinc.utils.TransaksiDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class DetailTransaksiActivity extends AppCompatActivity implements TransaksiDialog.TransaksiDialogListener {
    public static String ID_TRANSAKSI = "ID_TRANSAKSI";
    public static String ID_STATUS = "ID_STATUS";

    ConstraintLayout constraintLayout;
    TextView tvNamaCustomer, tvAlamatCustomer, tvNomerCustomer, tvTotalTransaksi, tvEstimasi;
    List<DetailTransaksiCustomer> detailTransaksiCustomerList = new ArrayList<>();
    List<DetailTransaksi> detailTransaksiList = new ArrayList<>();
    DetailTransaksiAdapter adapter;
    RecyclerView rvDetailTransaksi;
    ImageView ivGambarBarang;
    Button btnTransaksiSelesai;
    Spinner spinnerStatus;
    String id_status_transaksi = null;
    String namaCustomer = "";
    int jumlah_bayar = 0;
    private ApiService apiService;
    String id_transaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Transaksi");
        }
        init();

        Bundle bundle = getIntent().getExtras();
        id_transaksi = bundle.getString(ID_TRANSAKSI);
        id_status_transaksi = bundle.getString(ID_STATUS);

        if (id_status_transaksi.equals("2")){
            btnTransaksiSelesai.setVisibility(View.GONE);
        }

        detailTransaksi(id_transaksi);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.parseInt(id_status_transaksi) != i)
                    updateStatus(id_transaksi, String.valueOf(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnTransaksiSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    private void openDialog() {
        TransaksiDialog dialog = new TransaksiDialog();
        dialog.show(getSupportFragmentManager(), "Bayar");
    }

    private void detailTransaksi(String id_transaksi) {
        constraintLayout.setVisibility(View.GONE);
        apiService.detailBarang(id_transaksi).enqueue(new Callback<TransaksiDetailResponse>() {
            @Override
            public void onResponse(Call<TransaksiDetailResponse> call, Response<TransaksiDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            detailTransaksiCustomerList = response.body().getDetailTransaksiCustomer();
                            detailTransaksiList = response.body().getDetailTransaksi();
                            for (DetailTransaksiCustomer customer : detailTransaksiCustomerList) {
                                namaCustomer = customer.getNamaCustomer();
                                tvNamaCustomer.setText(customer.getNamaCustomer());
                                tvAlamatCustomer.setText(customer.getAlamat());
                                tvNomerCustomer.setText(customer.getNoTelp());
                            }
                            setupRecyclerView();
                            Glide.with(getApplicationContext())
                                    .load(response.body().getGambar())
                                    .into(ivGambarBarang);
                            tvTotalTransaksi.setText("Rp. " + response.body().getTotalHarga().toString());
                            tvEstimasi.setText(response.body().getEstimasi() + " hari");
                            spinnerStatus.setSelection(Integer.parseInt(id_status_transaksi));
                            constraintLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiDetailResponse> call, Throwable t) {
                Snackbar.make(constraintLayout, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void updateStatus(String id_transaksi, String id_status) {
        apiService.updateStatus(id_transaksi, id_status).enqueue(new Callback<TransaksiUpdateStatusResponse>() {
            @Override
            public void onResponse(Call<TransaksiUpdateStatusResponse> call, Response<TransaksiUpdateStatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Snackbar.make(constraintLayout, "Update status berhasil", Snackbar.LENGTH_SHORT)
                                    .show();
                        } else {
                            Snackbar.make(constraintLayout, "Update status gagal", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiUpdateStatusResponse> call, Throwable t) {
                Snackbar.make(constraintLayout, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void init() {
        spinnerStatus = findViewById(R.id.spinner_status);
        tvEstimasi = findViewById(R.id.tv_estimasi);
        rvDetailTransaksi = findViewById(R.id.rv_detail_transaksi);
        apiService = ApiClient.getClient().create(ApiService.class);
        constraintLayout = findViewById(R.id.constrain_layout);
        tvNamaCustomer = findViewById(R.id.tv_nama_customer);
        tvAlamatCustomer = findViewById(R.id.tv_alamat_customer);
        tvNomerCustomer = findViewById(R.id.tv_nomer_customer);
        ivGambarBarang = findViewById(R.id.iv_gambar_barang);
        tvTotalTransaksi = findViewById(R.id.tv_total_transaksi);
        btnTransaksiSelesai = findViewById(R.id.btn_transaksi_selesai);
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new DetailTransaksiAdapter(DetailTransaksiActivity.this, detailTransaksiList);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
            rvDetailTransaksi.addItemDecoration(itemDecor);
            rvDetailTransaksi.setLayoutManager(new LinearLayoutManager(this));
            rvDetailTransaksi.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
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

    private void nota(String id_transaksi, String bayar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawText("Chrome.Inc", 20, 30, paint);
        canvas.drawText("Karanglo Indah Blok U-1 Kota Malang", 20, 50, paint);
        canvas.drawText("Telp. 081252411618", 20, 70, paint);

        canvas.drawText("Id Transaksi", 20, 105, paint);
        canvas.drawText("Tanggal", 20, 125, paint);
        canvas.drawText("Nama Customer", 20, 145, paint);
        canvas.drawText(": " + id_transaksi, 150, 105, paint);
        canvas.drawText(": " + formatter.format(date), 150, 125, paint);
        canvas.drawText(": " + namaCustomer, 150, 145, paint);

        canvas.drawText("--------------------------------------------------------------------------------------------------", 20, 180, paint);
        canvas.drawText("Nama Barang", 20, 205, paint);
        canvas.drawText("Qty", 150, 205, paint);
        canvas.drawText("Harga", 200, 205, paint);
        canvas.drawText("Total", 270, 205, paint);
        canvas.drawText("--------------------------------------------------------------------------------------------------", 20, 230, paint);

        int posisi_y = 250;
        int harga_total = 0;

        for (int i = 0; i < detailTransaksiList.size(); i++) {
            int total = Integer.parseInt(detailTransaksiList.get(i).getJumlahBarang()) * Integer.parseInt(detailTransaksiList.get(i).getHargaSatuan());
            canvas.drawText(detailTransaksiList.get(i).getNamaBarang(), 20, posisi_y, paint);
            canvas.drawText(detailTransaksiList.get(i).getJumlahBarang(), 150, posisi_y, paint);
            canvas.drawText(detailTransaksiList.get(i).getHargaSatuan(), 200, posisi_y, paint);
            canvas.drawText(String.valueOf(total), 270, posisi_y, paint);
            posisi_y += 20;
            harga_total += total;
        }

        int kembalian = Integer.parseInt(bayar) - harga_total;


        canvas.drawText("--------------------------------------------------------------------------------------------------", 20, posisi_y, paint);
        canvas.drawText("Total", 20, posisi_y + 20, paint);
        canvas.drawText(String.valueOf(harga_total), 270, posisi_y + 20, paint);
        canvas.drawText("Tunai", 20, posisi_y + 40, paint);
        canvas.drawText(bayar, 270, posisi_y + 40, paint);
        canvas.drawText("Kembali", 20, posisi_y + 60, paint);
        canvas.drawText(String.valueOf(kembalian), 270, posisi_y + 60, paint);

        canvas.drawText("Terimakasih Atas Kepercayaan Anda", 20, posisi_y + 90, paint);
        canvas.drawText("Periksa Barang Sebelum Kembali", 20, posisi_y + 110, paint);
        canvas.drawText("Komplain Tidak Diterima Ketika Sudah Meninggalkan Pabrik", 20, posisi_y + 130, paint);

        document.finishPage(page);

        String filename = "selesai " + id_transaksi;
        File file = new File(Environment.getExternalStorageDirectory(), "/" + filename + ".pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            Log.d("Nota", "nota: Berhasil");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Nota", "nota: gagal ");
        }

        document.close();
    }

    @Override
    public void applyTexts(String jumlah_bayar) {
        nota(id_transaksi,jumlah_bayar);
        updateStatus(id_transaksi, String.valueOf(2));
    }
}