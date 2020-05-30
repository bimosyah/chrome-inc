package bimo.syahputro.chromeinc.activity.detailTransaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import bimo.syahputro.chromeinc.R;

public class DetailTransaksiActivity extends AppCompatActivity {
    public static String ID_TRANSAKSI;
    ConstraintLayout constraintLayout;

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


    }

    private void init(){
        constraintLayout = findViewById(R.id.constrain_layout);
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