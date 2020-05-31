package bimo.syahputro.chromeinc.activity.detailTransaksi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.entity.DetailBarang;
import bimo.syahputro.chromeinc.network.entity.DetailCustomer;
import bimo.syahputro.chromeinc.network.response.TransaksiDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class DetailTransaksiActivity extends AppCompatActivity {
    public static String ID_TRANSAKSI;
    ConstraintLayout constraintLayout;
    TextView tvNamaCustomer, tvAlamatCustomer, tvNomerCustomer, tvTotalTransaksi;
    List<DetailCustomer> detailCustomerList = new ArrayList<>();
    List<DetailBarang> detailBarangList = new ArrayList<>();
    DetailTransaksiAdapter adapter;
    RecyclerView rvDetailTransaksi;
    ImageView ivGambarBarang;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Transaksi");
        }
        init();

        Intent i = getIntent();
        String id_transaksi = i.getStringExtra(ID_TRANSAKSI);

        detailTransaksi(id_transaksi);

    }

    private void detailTransaksi(String id_transaksi) {
        constraintLayout.setVisibility(View.GONE);
        apiService.detailBarang(id_transaksi).enqueue(new Callback<TransaksiDetailResponse>() {
            @Override
            public void onResponse(Call<TransaksiDetailResponse> call, Response<TransaksiDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            detailCustomerList = response.body().getDetailCustomer();
                            detailBarangList = response.body().getDetailBarang();
                            for (DetailCustomer customer : detailCustomerList) {
                                tvNamaCustomer.setText(customer.getNamaCustomer());
                                tvAlamatCustomer.setText(customer.getAlamat());
                                tvNomerCustomer.setText(customer.getNoTelp());
                            }
                            setupRecyclerView();
                            Glide.with(getApplicationContext())
                                    .load(response.body().getGambar())
                                    .into(ivGambarBarang);
                            tvTotalTransaksi.setText("Rp. " +response.body().getTotalHarga().toString());
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

    private void init() {
        rvDetailTransaksi = findViewById(R.id.rv_detail_transaksi);
        apiService = ApiClient.getClient().create(ApiService.class);
        constraintLayout = findViewById(R.id.constrain_layout);
        tvNamaCustomer = findViewById(R.id.tv_nama_customer);
        tvAlamatCustomer = findViewById(R.id.tv_alamat_customer);
        tvNomerCustomer = findViewById(R.id.tv_nomer_customer);
        ivGambarBarang = findViewById(R.id.iv_gambar_barang);
        tvTotalTransaksi = findViewById(R.id.tv_total_transaksi);
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new DetailTransaksiAdapter(DetailTransaksiActivity.this, detailBarangList);
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
}