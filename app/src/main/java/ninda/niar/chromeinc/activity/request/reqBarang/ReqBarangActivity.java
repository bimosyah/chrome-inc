package ninda.niar.chromeinc.activity.request.reqBarang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import ninda.niar.chromeinc.R;
import ninda.niar.chromeinc.activity.request.daftarReqBarang.DaftarReqBarangActivity;
import ninda.niar.chromeinc.network.ApiClient;
import ninda.niar.chromeinc.network.ApiService;
import ninda.niar.chromeinc.network.entity.Inventory;
import ninda.niar.chromeinc.network.response.InventoryBaruResponse;
import ninda.niar.chromeinc.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqBarangActivity extends AppCompatActivity {
    public static String PASS_DATA = "pass_data";
    Button btnReqBarang,btnStok, btnReqStok;
    EditText etNamaBarang, etJumlah, etSatuan, etHargaBeli, etKeterangan,etNamaTukang, etStok, etStokDipakai;
    ApiService apiService;
    String id_inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_barang);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Request Barang");
        }

        init();

        etNamaTukang.setText(Preference.getUsername(getBaseContext()));

        Inventory inventory = getIntent().getParcelableExtra(PASS_DATA);
        if (inventory != null){
            btnReqBarang.setVisibility(View.GONE);
            etNamaBarang.setText(inventory.getNamaInv());
            etJumlah.setText(inventory.getJumlah());
            etSatuan.setText(inventory.getSatuan());
            etHargaBeli.setText(inventory.getHargaBeli());
            etKeterangan.setText(inventory.getKeterangan());
            etStok.setText(inventory.getStok());
            etNamaTukang.setText(inventory.getNamaTukang());
            id_inventory = inventory.getIdInventory();

            if (inventory.getStok().equals("0")){
                btnStok.setVisibility(View.GONE);
                btnReqStok.setVisibility(View.VISIBLE);
                etStokDipakai.setEnabled(false);
            }
        }

        btnReqBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNamaBarang.getText().toString().equals("") && !etJumlah.getText().toString().equals("")
                        && !etSatuan.getText().toString().equals("") && !etHargaBeli.getText().toString().equals("")
                        && !etKeterangan.getText().toString().equals("")){
                    reqBarang();
                }else {
                    Snackbar.make(btnReqBarang, "Data barang harus diisi", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        btnStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etStok.getText().equals("")){
                    updateStok();
                }
            }
        });

        btnReqStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqStok();
            }
        });
    }

    private void reqBarang() {
        apiService.inventoryBaru(
                Preference.getUsername(getBaseContext()),
                etNamaBarang.getText().toString(),
                etJumlah.getText().toString(),
                etSatuan.getText().toString(),
                etHargaBeli.getText().toString(),
                etKeterangan.getText().toString()
        ).enqueue(new Callback<InventoryBaruResponse>() {
            @Override
            public void onResponse(Call<InventoryBaruResponse> call, Response<InventoryBaruResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Snackbar.make(btnReqBarang, "Berhasil", Snackbar.LENGTH_SHORT)
                                    .show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(ReqBarangActivity.this, DaftarReqBarangActivity.class));
                                    finish();
                                }
                            }, 1000);
                        } else {
                            Snackbar.make(btnReqBarang, "Gagal", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<InventoryBaruResponse> call, Throwable t) {
                Snackbar.make(btnReqBarang, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }
    private void updateStok(){
        apiService.updateStokBarang(id_inventory, etStokDipakai.getText().toString()).enqueue(new Callback<InventoryBaruResponse>() {
            @Override
            public void onResponse(Call<InventoryBaruResponse> call, Response<InventoryBaruResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Snackbar.make(btnStok, "Update Stok Berhasil", Snackbar.LENGTH_SHORT)
                                    .show();
                        } else {
                            Snackbar.make(btnStok, "Update Stok Gagal", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<InventoryBaruResponse> call, Throwable t) {
                Snackbar.make(btnStok, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();

            }
        });
    }
    private void reqStok(){
        apiService.reqStokBarang().enqueue(new Callback<InventoryBaruResponse>() {
            @Override
            public void onResponse(Call<InventoryBaruResponse> call, Response<InventoryBaruResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Snackbar.make(btnReqBarang, "Request Berhasil", Snackbar.LENGTH_SHORT)
                                    .show();
                        } else {
                            Snackbar.make(btnReqBarang, "Request Gagal", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<InventoryBaruResponse> call, Throwable t) {
                Snackbar.make(btnReqBarang, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();

            }
        });
    }
    private void init() {
        apiService = ApiClient.getClient().create(ApiService.class);
        btnReqBarang = findViewById(R.id.btn_req_barang);
        btnStok = findViewById(R.id.btn_stok);
        btnReqStok = findViewById(R.id.btn_req_stok);
        etNamaBarang = findViewById(R.id.et_nama_barang);
        etJumlah = findViewById(R.id.et_jumlah_barang);
        etSatuan = findViewById(R.id.et_satuan_barang);
        etHargaBeli = findViewById(R.id.et_hargabeli_barang);
        etKeterangan = findViewById(R.id.et_keterangan_barang);
        etNamaTukang = findViewById(R.id.et_nama_tukang);
        etStok = findViewById(R.id.et_stok);
        etStokDipakai = findViewById(R.id.et_stok_dipakai);
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