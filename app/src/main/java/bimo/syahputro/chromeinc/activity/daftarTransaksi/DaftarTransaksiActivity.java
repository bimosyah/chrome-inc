package bimo.syahputro.chromeinc.activity.daftarTransaksi;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.entity.DaftarBarang;
import bimo.syahputro.chromeinc.network.response.BarangMasukResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class DaftarTransaksiActivity extends AppCompatActivity {
    FloatingActionButton fab;
    List<DaftarBarang> barangList = new ArrayList<>();
    DaftarTransaksiAdapter barangAdapter;
    RecyclerView rvBarangMasuk;
    ProgressBar progressBar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Barang");
        }

        init();
        loadBarang();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        rvBarangMasuk = findViewById(R.id.rv_barang_masuk);
        apiService = ApiClient.getClient().create(ApiService.class);
        fab = findViewById(R.id.fab);
    }

    private void loadBarang() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.daftarBarangMasuk().enqueue(new Callback<BarangMasukResponse>() {
            @Override
            public void onResponse(Call<BarangMasukResponse> call, final Response<BarangMasukResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    barangList = response.body().getDaftarBarang();
                                    setupRecyclerView();
                                }
                            }, 3000);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BarangMasukResponse> call, final Throwable t) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(rvBarangMasuk, t.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }, 3000);
            }
        });
    }

    private void setupRecyclerView() {
        if (barangAdapter == null) {
            barangAdapter = new DaftarTransaksiAdapter(DaftarTransaksiActivity.this, barangList);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
            rvBarangMasuk.addItemDecoration(itemDecor);
            rvBarangMasuk.setLayoutManager(new LinearLayoutManager(this));
            rvBarangMasuk.setAdapter(barangAdapter);
        } else {
            barangAdapter.notifyDataSetChanged();
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