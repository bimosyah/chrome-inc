package bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import bimo.syahputro.chromeinc.R;

public class CustomerFragment extends Fragment {
    OnDataPassCustomer dataParser;
    EditText etNamaCustomer, etAlamat, etNoTelp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNamaCustomer = view.findViewById(R.id.et_nama_customer);
        etAlamat = view.findViewById(R.id.et_alamat_customer);
        etNoTelp = view.findViewById(R.id.et_notelp_customer);
    }

    public void passData(){
        dataParser.passDataCustomer(etNamaCustomer.getText().toString(),etAlamat.getText().toString(), etNoTelp.getText().toString());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataParser = (OnDataPassCustomer) context;

    }

    public interface OnDataPassCustomer {
        void passDataCustomer(String nama, String alamat, String notelp);
    }
}


