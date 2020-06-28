package ninda.sabilla.chromeinc.activity.transaksi.tambahTransaksi.baru.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ninda.sabilla.chromeinc.R;
import ninda.sabilla.chromeinc.activity.transaksi.tambahTransaksi.baru.TransaksiCustomerBaruActivity;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;

public class CheckoutFragment extends Fragment {
    View view;
    TextView tvNamaCustomer, tvAlamatCustomer, tvNomerCustomer, tvTotalHarga;
    String namaCustomer = "", alamatCustomer = "", notelpCustomer = "";
    ImageView ivBarang;
    RecyclerView rvCheckout;
    ArrayList<String> barangList;
    ArrayList<String> idbarangList;
    ArrayList<String> jumlahBarangList;
    ArrayList<String> hargaBarangList;
    CheckoutAdapter adapter;
    OnDataPassCheckout dataParser;
    Bitmap bitmapBarang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_checkout, container, false);
        init();

        if (getArguments() != null) {
            namaCustomer = getArguments().getString(TransaksiCustomerBaruActivity.NAMA_CUSTOMER);
            alamatCustomer = getArguments().getString(TransaksiCustomerBaruActivity.ALAMAT_CUSTOMER);
            notelpCustomer = getArguments().getString(TransaksiCustomerBaruActivity.NOTELP_CUSTOMER);
            barangList = getArguments().getStringArrayList(TransaksiCustomerBaruActivity.BARANGLIST);
            idbarangList = getArguments().getStringArrayList(TransaksiCustomerBaruActivity.IDBARANGLIST);
            jumlahBarangList = getArguments().getStringArrayList(TransaksiCustomerBaruActivity.JUMLAHBARANGLIST);
            hargaBarangList = getArguments().getStringArrayList(TransaksiCustomerBaruActivity.HARGABARANGLIST);

        }

        Log.d("barang",String.valueOf(barangList.size()));
        Log.d("id barang",String.valueOf(idbarangList.size()));
        Log.d("jumlah barang",String.valueOf(jumlahBarangList.size()));
        Log.d("harga barang",String.valueOf(hargaBarangList.size()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNamaCustomer.setText(namaCustomer);
        tvAlamatCustomer.setText(alamatCustomer);
        tvNomerCustomer.setText(notelpCustomer);
        tvTotalHarga.setText("Rp. " + String.valueOf(hitungHarga()));
        setupRecyclerView();

        ivBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
            }
        });
    }

    private int hitungHarga(){
        int total = 0;
        for (int i = 0; i < barangList.size(); i++){
            int temp_total = Integer.parseInt(hargaBarangList.get(i)) * Integer.parseInt(jumlahBarangList.get(i));
            total += temp_total;
        }
        return total;
    }

    private void init(){
        rvCheckout = view.findViewById(R.id.rv_checkout);
        tvNamaCustomer = view.findViewById(R.id.tv_nama_customer);
        tvAlamatCustomer = view.findViewById(R.id.tv_alamat_customer);
        tvNomerCustomer = view.findViewById(R.id.tv_nomer_customer);
        tvTotalHarga = view.findViewById(R.id.tv_total_harga);
        ivBarang = view.findViewById(R.id.iv_gambar_barang);
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new CheckoutAdapter(barangList, jumlahBarangList, hargaBarangList,getContext());
            DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);
            rvCheckout.addItemDecoration(itemDecor);
            rvCheckout.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCheckout.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
        bitmapBarang = capturedImage;
        ivBarang.setImageBitmap(bitmapBarang);

    }

    public void passData() {
        dataParser.passDataCheckout(bitmapBarang);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataParser = (OnDataPassCheckout) context;
    }

    public interface OnDataPassCheckout {
        void passDataCheckout(Bitmap bitmap);
    }
}
