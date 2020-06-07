package bimo.syahputro.chromeinc.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.dashboard.DashboardActivity;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.response.LoginResponse;
import bimo.syahputro.chromeinc.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvTitle, tvSubTitle;
    Animation animTitle;
    EditText etUsername, etPassword;
    ConstraintLayout constrainLayout;
    ProgressBar progressBar;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        anim();

        if (Preference.getLoggedInStatus(this)){
            if (getIntent().getBooleanExtra("EXIT", false)) {
                finish();
            }else {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            }
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.equals("") && password.equals("")) {
                    Snackbar.make(constrainLayout, R.string.login_kosong, Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBar.setVisibility(View.VISIBLE);
                    login(username, password);
                }
            }
        });
    }

    private void anim() {
        tvTitle.startAnimation(animTitle);
        tvSubTitle.startAnimation(animTitle);
    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);
        constrainLayout = findViewById(R.id.constrain_layout);
        apiService = ApiClient.getClient().create(ApiService.class);
        animTitle = AnimationUtils.loadAnimation(this, R.anim.login_title);
        btnLogin = findViewById(R.id.btn_login);
        tvTitle = findViewById(R.id.tv_title);
        tvSubTitle = findViewById(R.id.tv_subtitle);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
    }

    private void login(final String username, String password) {
        apiService.login(username, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    progressBar.setVisibility(View.GONE);
                                    Preference.setRegisteredUser(getApplicationContext(), "nin", response.body().getPegawai().getIdPegawai(),username);
                                    Preference.setUsername(getApplicationContext(), response.body().getPegawai().getNamaPegawai());
                                    Log.d("nama_user2", Preference.getUsername(getBaseContext()));
                                    Log.d("nama_user3", response.body().getPegawai().getNamaPegawai());
                                    Preference.setLoggedInStatus(getApplicationContext(), true);
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                }
                            }, 3000);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    progressBar.setVisibility(View.GONE);
                                    Snackbar.make(constrainLayout, R.string.login_gagal, Snackbar.LENGTH_SHORT)
                                            .show();
                                }
                            }, 3000);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, final Throwable t) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(constrainLayout, t.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }, 3000);
            }
        });
    }
}
