package bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import bimo.syahputro.chromeinc.R;

import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.ALAMAT_CUSTOMER;
import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.NAMA_CUSTOMER;
import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.NOTELP_CUSTOMER;

public class CheckoutFragment extends Fragment {
    View view;
    TextView tvNamaCustomer, tvAlamatCustomer, tvNomerCustomer, tvTotalTransaksi;
    String namaCustomer = "", alamatCustomer = "", notelpCustomer = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_checkout, container, false);
        init();
        if (getArguments() != null) {
            namaCustomer = getArguments().getString(NAMA_CUSTOMER);
            alamatCustomer = getArguments().getString(ALAMAT_CUSTOMER);
            notelpCustomer = getArguments().getString(NOTELP_CUSTOMER);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNamaCustomer.setText(namaCustomer);
        tvAlamatCustomer.setText(alamatCustomer);
        tvNomerCustomer.setText(notelpCustomer);
    }

    private void init(){
        tvNamaCustomer = view.findViewById(R.id.tv_nama_customer);
        tvAlamatCustomer = view.findViewById(R.id.tv_alamat_customer);
        tvNomerCustomer = view.findViewById(R.id.tv_nomer_customer);
    }

}
