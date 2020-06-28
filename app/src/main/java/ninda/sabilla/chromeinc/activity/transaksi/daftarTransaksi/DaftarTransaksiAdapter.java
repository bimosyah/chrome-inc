package ninda.sabilla.chromeinc.activity.transaksi.daftarTransaksi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ninda.sabilla.chromeinc.R;
import ninda.sabilla.chromeinc.activity.transaksi.detailTransaksi.DetailTransaksiActivity;
import ninda.sabilla.chromeinc.network.entity.DaftarTransaksi;

import static ninda.sabilla.chromeinc.activity.transaksi.detailTransaksi.DetailTransaksiActivity.ID_STATUS;
import static ninda.sabilla.chromeinc.activity.transaksi.detailTransaksi.DetailTransaksiActivity.ID_TRANSAKSI;

class DaftarTransaksiAdapter extends RecyclerView.Adapter<DaftarTransaksiAdapter.ViewHolder> implements Filterable {
    Context context;
    List<DaftarTransaksi> daftarTransaksiList;
    List<DaftarTransaksi> daftarTransaksiListFull;

    private Filter daftarTransaksiFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<DaftarTransaksi> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(daftarTransaksiListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (DaftarTransaksi transaksi : daftarTransaksiListFull) {
                    if (transaksi.getNamaCustomer().toLowerCase().contains(filterPattern)) {
                        filteredList.add(transaksi);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            daftarTransaksiList.clear();
            daftarTransaksiList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public DaftarTransaksiAdapter(Context context, List<DaftarTransaksi> daftarTransaksiList) {
        this.context = context;
        this.daftarTransaksiList = daftarTransaksiList;
        this.daftarTransaksiListFull = new ArrayList<>(daftarTransaksiList);
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
        String id_status = "0";
        holder.tvNomer.setText(nomer + ".");
        holder.tvNamaCustomer.setText(daftarTransaksiList.get(position).getNamaCustomer());
        holder.tvStatus.setText(daftarTransaksiList.get(position).getStatus());
//        holder.tvDetail.setText("Detail");

        context.getResources().getString(R.string.status_menunggu);
        String color;

        if (daftarTransaksiList.get(position).getStatus().equals("Menunggu")) {
            color = context.getResources().getString(R.string.status_menunggu);
            id_status = "0";
        } else if (daftarTransaksiList.get(position).getStatus().equals("Selesai")) {
            color = context.getResources().getString(R.string.status_selesai);
            id_status = "2";
        } else {
            color = context.getResources().getString(R.string.status_dikerjakan);
            id_status = "1";
        }

        holder.tvStatus.setBackgroundColor(Color.parseColor(color));

        final String final_id_status = id_status;

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTransaksiActivity.class);
//                intent.putExtra(ID_TRANSAKSI, daftarBarangList.get(position).getIdTransaksi());
//                intent.putExtra(ID_STATUS, final_id_status);

                Bundle bundle = new Bundle();
                bundle.putString(ID_TRANSAKSI, daftarTransaksiList.get(position).getIdTransaksi());
                bundle.putString(ID_STATUS, final_id_status);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarTransaksiList.size();
    }

    @Override
    public Filter getFilter() {
        return daftarTransaksiFilter;
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
