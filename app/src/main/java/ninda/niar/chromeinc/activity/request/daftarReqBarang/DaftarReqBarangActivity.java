package ninda.niar.chromeinc.activity.request.daftarReqBarang;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ninda.niar.chromeinc.R;
import ninda.niar.chromeinc.activity.request.reqBarang.ReqBarangActivity;
import ninda.niar.chromeinc.network.ApiClient;
import ninda.niar.chromeinc.network.ApiService;
import ninda.niar.chromeinc.network.entity.Inventory;
import ninda.niar.chromeinc.network.response.InventoryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class DaftarReqBarangActivity extends AppCompatActivity {
    FloatingActionButton fabReqBarang;
    ApiService apiService;
    ProgressBar progressBar;
    DaftarReqBarangAdapter adapter;
    List<Inventory> inventoryList = new ArrayList<>();
    RecyclerView rvInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_req_barang);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Barang Baku");
        }

        init();
        loadInventory();
        fabReqBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarReqBarangActivity.this, ReqBarangActivity.class));
            }
        });

    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        apiService = ApiClient.getClient().create(ApiService.class);
        fabReqBarang = findViewById(R.id.fab_req_barang);
        rvInventory = findViewById(R.id.rv_inventory);
    }

    private void loadInventory() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.daftarInventory().enqueue(new Callback<InventoryResponse>() {
            @Override
            public void onResponse(Call<InventoryResponse> call, Response<InventoryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            inventoryList = response.body().getInventory();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    setupRecyclerView();
                                }
                            }, 3000);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<InventoryResponse> call, Throwable t) {

            }
        });
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new DaftarReqBarangAdapter(DaftarReqBarangActivity.this, inventoryList);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
            rvInventory.addItemDecoration(itemDecor);
            rvInventory.setLayoutManager(new LinearLayoutManager(this));
            rvInventory.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_refresh) {
            adapter = null;
            loadInventory();
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
                adapter.getFilter().filter(s);
                return false;
            }

        });
        return true;
    }
}