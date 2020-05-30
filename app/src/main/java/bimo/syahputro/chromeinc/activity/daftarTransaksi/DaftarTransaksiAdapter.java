package bimo.syahputro.chromeinc.activity.barangMasuk;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.entity.DaftarBarang;

class BarangMasukAdapter extends RecyclerView.Adapter<BarangMasukAdapter.ViewHolder> {
    Context context;
    List<DaftarBarang> daftarBarangList;

    public BarangMasukAdapter(Context context, List<DaftarBarang> daftarBarangList) {
        this.context = context;
        this.daftarBarangList = daftarBarangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_barang_masuk, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int nomer = position + 1;
        holder.tvNomer.setText(nomer + ".");
        holder.tvNamaCustomer.setText(daftarBarangList.get(position).getNamaCustomer());
        holder.tvStatus.setText(daftarBarangList.get(position).getStatus());
//        holder.tvDetail.setText("Detail");

        context.getResources().getString(R.string.status_menunggu);
        String color;

        if (daftarBarangList.get(position).getStatus().equals("Menunggu"))
            color = context.getResources().getString(R.string.status_menunggu);
        else if (daftarBarangList.get(position).getStatus().equals("Selesai"))
            color = context.getResources().getString(R.string.status_menunggu);
        else
            color = context.getResources().getString(R.string.status_dikerjakan);

        holder.tvStatus.setBackgroundColor(Color.parseColor(color));

    }

    @Override
    public int getItemCount() {
        return daftarBarangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomer;
        TextView tvNamaCustomer;
        TextView tvStatus;
        TextView tvDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomer = itemView.findViewById(R.id.tv_nomer);
            tvNamaCustomer = itemView.findViewById(R.id.tv_nama_customer);
            tvStatus = itemView.findViewById(R.id.tv_status);
//            tvDetail = itemView.findViewById(R.id.tv_action);

        }
    }
}
