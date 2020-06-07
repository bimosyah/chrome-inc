package bimo.syahputro.chromeinc.activity.reqBarang;

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

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.daftarReqBarang.DaftarReqBarangActivity;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.response.InventoryBaruResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqBarangActivity extends AppCompatActivity {

    Button btnReqBarang;
    EditText etNamaBarang, etJumlah, etSatuan, etHargaBeli, etKeterangan;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_barang);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Request Barang");
        }

        init();

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
    }

    private void reqBarang() {
        apiService.inventoryBaru(etNamaBarang.getText().toString(), etJumlah.getText().toString(), etSatuan.getText().toString(),
                etHargaBeli.getText().toString(), etKeterangan.getText().toString()).enqueue(new Callback<InventoryBaruResponse>() {
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

    private void init() {
        apiService = ApiClient.getClient().create(ApiService.class);
        btnReqBarang = findViewById(R.id.btn_req_barang);
        etNamaBarang = findViewById(R.id.et_nama_barang);
        etJumlah = findViewById(R.id.et_jumlah_barang);
        etSatuan = findViewById(R.id.et_satuan_barang);
        etHargaBeli = findViewById(R.id.et_hargabeli_barang);
        etKeterangan = findViewById(R.id.et_keterangan_barang);
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