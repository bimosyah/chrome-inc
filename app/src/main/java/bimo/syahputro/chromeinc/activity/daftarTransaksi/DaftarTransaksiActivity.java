package bimo.syahputro.chromeinc.activity.daftarTransaksi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.tambahTransaksi.lama.DaftarCustomerActivity;
import bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.entity.DaftarTransaksi;
import bimo.syahputro.chromeinc.network.response.TransaksiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class DaftarTransaksiActivity extends AppCompatActivity {
    FloatingActionButton fab_customer_lama, fab_customer_baru;
    List<DaftarTransaksi> barangList = new ArrayList<>();
    DaftarTransaksiAdapter transaksiAdapter;
    RecyclerView rvBarangMasuk;
    ProgressBar progressBar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Transaksi");
        }

        init();
        loadBarang();

        fab_customer_baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarTransaksiActivity.this, TransaksiCustomerBaruActivity.class));
            }
        });

        fab_customer_lama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarTransaksiActivity.this, DaftarCustomerActivity.class));
            }
        });
    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        rvBarangMasuk = findViewById(R.id.rv_barang_masuk);
        apiService = ApiClient.getClient().create(ApiService.class);
        fab_customer_lama = findViewById(R.id.fab_user_lama);
        fab_customer_baru = findViewById(R.id.fab_user_baru);
    }

    private void loadBarang() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.daftarTransaksi().enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, final Response<TransaksiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    barangList = response.body().getDaftarTransaksi();
                                    setupRecyclerView();
                                }
                            }, 3000);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, final Throwable t) {
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
        if (transaksiAdapter == null) {
            transaksiAdapter = new DaftarTransaksiAdapter(DaftarTransaksiActivity.this, barangList);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
            rvBarangMasuk.addItemDecoration(itemDecor);
            rvBarangMasuk.setLayoutManager(new LinearLayoutManager(this));
            rvBarangMasuk.setAdapter(transaksiAdapter);
        } else {
            transaksiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }else if (item.getItemId() == R.id.menu_refresh){
            transaksiAdapter = null;
            loadBarang();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                transaksiAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

}