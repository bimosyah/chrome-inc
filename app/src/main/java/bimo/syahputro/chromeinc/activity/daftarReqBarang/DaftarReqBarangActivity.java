package bimo.syahputro.chromeinc.activity.daftarReqBarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.reqBarang.ReqBarangActivity;

public class DaftarReqBarangActivity extends AppCompatActivity {
    FloatingActionButton fabReqBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_req_barang);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Request Barang");
        }
        init();

        fabReqBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarReqBarangActivity.this, ReqBarangActivity.class));
            }
        });

    }

    private void init() {
        fabReqBarang = findViewById(R.id.fab_req_barang);
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