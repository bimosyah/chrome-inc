package bimo.syahputro.chromeinc.activity.tambahTransaksi.baru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import bimo.syahputro.chromeinc.R;

public class TransaksiCustomerBaruActivity extends AppCompatActivity {
    public static String ID_CUSTOMER = "ID_CUSTOMER";
    public static String NAMA_CUSTOMER = "NAMA_CUSTOMER";
    public static String ALAMAT_CUSTOMER = "ALAMAT_CUSTOMER";
    public static String NOTELP_CUSTOMER = "NOTELP_CUSTOMER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Transaksi Baru");
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