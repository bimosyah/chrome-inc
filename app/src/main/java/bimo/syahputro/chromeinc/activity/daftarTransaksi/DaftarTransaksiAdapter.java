package bimo.syahputro.chromeinc.activity.daftarTransaksi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.detailTransaksi.DetailTransaksiActivity;
import bimo.syahputro.chromeinc.network.entity.DaftarBarang;

import static bimo.syahputro.chromeinc.activity.detailTransaksi.DetailTransaksiActivity.ID_TRANSAKSI;

class DaftarTransaksiAdapter extends RecyclerView.Adapter<DaftarTransaksiAdapter.ViewHolder> {
    Context context;
    List<DaftarBarang> daftarBarangList;

    public DaftarTransaksiAdapter(Context context, List<DaftarBarang> daftarBarangList) {
        this.context = context;
        this.daftarBarangList = daftarBarangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daftar_transaksi, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTransaksiActivity.class);
                intent.putExtra(ID_TRANSAKSI, daftarBarangList.get(position).getIdTransaksi());
                context.startActivity(intent);
            }
        });

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
        ConstraintLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.constrain_layout);
            tvNomer = itemView.findViewById(R.id.tv_nomer);
            tvNamaCustomer = itemView.findViewById(R.id.tv_nama_customer);
            tvStatus = itemView.findViewById(R.id.tv_status);
//            tvDetail = itemView.findViewById(R.id.tv_action);

        }
    }
}
