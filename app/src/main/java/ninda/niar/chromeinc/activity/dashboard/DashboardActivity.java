package ninda.niar.chromeinc.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import ninda.niar.chromeinc.R;
import ninda.niar.chromeinc.activity.transaksi.daftarTransaksi.DaftarTransaksiActivity;
import ninda.niar.chromeinc.activity.login.LoginActivity;
import ninda.niar.chromeinc.activity.request.daftarReqBarang.DaftarReqBarangActivity;
import ninda.niar.chromeinc.utils.Preference;

public class DashboardActivity extends AppCompatActivity {
    TextView tvUsername, tvRole;
    ImageView ivPerson;
    Animation dashboard_head;
    GridLayout glMenu;
    CardView cardBarang, cardRequest, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
        anim();

        tvUsername.setText(Preference.getUsername(getBaseContext()));

        cardBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, DaftarTransaksiActivity.class));
            }
        });

        cardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, DaftarReqBarangActivity.class));
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preference.clearLoggedInUser(getApplicationContext());
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void anim() {
        tvUsername.startAnimation(dashboard_head);
        ivPerson.startAnimation(dashboard_head);
        tvRole.startAnimation(dashboard_head);
        glMenu.startAnimation(dashboard_head);
    }

    private void init() {
        cardBarang = findViewById(R.id.card_barang);
        cardRequest = findViewById(R.id.card_request);
        cardLogout = findViewById(R.id.card_logout);
        dashboard_head = AnimationUtils.loadAnimation(this, R.anim.dashboard_head);
        glMenu = findViewById(R.id.gl_menu);
        tvRole = findViewById(R.id.tv_role);
        ivPerson = findViewById(R.id.iv_person);
        tvUsername = findViewById(R.id.tv_username);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
