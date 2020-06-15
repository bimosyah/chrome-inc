package bimo.syahputro.chromeinc.activity.transaksi.tambahTransaksi.baru.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bimo.syahputro.chromeinc.R;

class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {
    ArrayList<String> barangList;
    ArrayList<String> jumlahBarangList;
    ArrayList<String> hargaBarangList;
    Context context;

    public CheckoutAdapter(ArrayList<String> barangList, ArrayList<String> jumlahBarangList, ArrayList<String> hargaBarangList, Context context) {
        this.barangList = barangList;
        this.jumlahBarangList = jumlahBarangList;
        this.hargaBarangList = hargaBarangList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_transaksi, parent, false);
        return new CheckoutAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int total_harga = Integer.parseInt(jumlahBarangList.get(position)) * Integer.parseInt(hargaBarangList.get(position));
        holder.tvNamaBarang.setText(barangList.get(position));
        holder.tvJumlahBarang.setText("Qty " + jumlahBarangList.get(position));
        holder.tvTotalHarga.setText("Rp. "+ total_harga);
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBarang, tvJumlahBarang, tvTotalHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
            tvJumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang);
            tvTotalHarga = itemView.findViewById(R.id.tv_total_harga);
        }
    }
}
