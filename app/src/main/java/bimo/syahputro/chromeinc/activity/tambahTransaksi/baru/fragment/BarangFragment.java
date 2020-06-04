package bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.ApiClient;
import bimo.syahputro.chromeinc.network.ApiService;
import bimo.syahputro.chromeinc.network.entity.Barang;
import bimo.syahputro.chromeinc.network.response.BarangResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangFragment extends Fragment {
    Spinner spinnerBarang;
    EditText etBarangQty;
    Button btnAddBarang;
    LinearLayout container_barang;
    ApiService apiService;
    View view;

    ArrayList<String> array_id_barang = new ArrayList<>();
    ArrayList<String> array_nama_barang = new ArrayList<>();
    ArrayList<String> array_harga_barang = new ArrayList<>();

    ArrayList<String> namaBarangList = new ArrayList<>();
    ArrayList<String> idbarangList = new ArrayList<>();
    ArrayList<String> jumlahBarangList = new ArrayList<>();
    ArrayList<String> hargaBarangList = new ArrayList<>();

    OnDataPassBarang passBarang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_barang, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        loadSpinnerBarang();
        btnAddBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) Objects.requireNonNull(getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View addView = Objects.requireNonNull(layoutInflater).inflate(R.layout.item_input_barang, null);
                TextView tvBarang = addView.findViewById(R.id.tv_barang);
                TextView tvBarangQty = addView.findViewById(R.id.tv_barang_qty);
                Button btnRemoveBarang = addView.findViewById(R.id.btn_remove_barang);
                btnRemoveBarang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((LinearLayout) addView.getParent()).removeView(addView);
                    }
                });

                if (!etBarangQty.getText().toString().equals("")) {

                    String barang = spinnerBarang.getSelectedItem().toString();
                    String jumlah = etBarangQty.getText().toString();
                    String id_barang = array_id_barang.get(spinnerBarang.getSelectedItemPosition());
                    String harga_barang = array_harga_barang.get(spinnerBarang.getSelectedItemPosition());

                    namaBarangList.add(barang);
                    idbarangList.add(id_barang);
                    jumlahBarangList.add(jumlah);
                    hargaBarangList.add(harga_barang);

                    tvBarang.setText(barang);
                    tvBarangQty.setText(jumlah);
                    container_barang.addView(addView);

                }

            }
        });
    }

    private void loadSpinnerBarang() {
        apiService.daftarBarang().enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            List<Barang> data_barang = response.body().getBarangHarga();
                            for (Barang barang : data_barang) {
                                array_id_barang.add(barang.getIdBarang());
                                array_nama_barang.add(barang.getNamaBarang());
                                array_harga_barang.add(barang.getTotalHarga());
                            }
                            setSpinnerBarang();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Snackbar.make(spinnerBarang, t.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void setSpinnerBarang() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, array_nama_barang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerBarang.setAdapter(adapter);
    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiService.class);
        spinnerBarang = view.findViewById(R.id.spinner_barang);
        etBarangQty = view.findViewById(R.id.et_qty);
        btnAddBarang = view.findViewById(R.id.btn_tambah_barang);
        container_barang = view.findViewById(R.id.linearlayout_barang_container);
    }

    public void passData() {

        passBarang.passDataBarang(namaBarangList, idbarangList, jumlahBarangList, hargaBarangList);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        passBarang = (OnDataPassBarang) context;
    }

    public interface OnDataPassBarang {
        void passDataBarang(ArrayList<String> barangList, ArrayList<String> idbarangList,
                            ArrayList<String> jumlahBarangList, ArrayList<String> hargaBarangList);
    }

}
