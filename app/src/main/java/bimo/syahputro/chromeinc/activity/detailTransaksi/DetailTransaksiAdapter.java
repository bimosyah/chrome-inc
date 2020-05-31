package bimo.syahputro.chromeinc.activity.detailTransaksi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.entity.DetailBarang;

public class DetailTransaksiAdapter extends RecyclerView.Adapter<DetailTransaksiAdapter.ViewHolder> {
    Context context;
    List<DetailBarang> detailBarangList;

    public DetailTransaksiAdapter(Context context, List<DetailBarang> detailBarangList) {
        this.context = context;
        this.detailBarangList = detailBarangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_transaksi, parent, false);
        return new DetailTransaksiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNamaBarang.setText(detailBarangList.get(position).getNamaBarang());
        holder.tvJumlahBarang.setText("Qty " + detailBarangList.get(position).getJumlahBarang());
        holder.tvTotalHarga.setText("Rp. "+detailBarangList.get(position).getHargaTotal());
    }

    @Override
    public int getItemCount() {
        return detailBarangList.size();
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
