package ninda.sabilla.chromeinc.activity.request.daftarReqBarang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import ninda.sabilla.chromeinc.activity.request.reqBarang.ReqBarangActivity;
import ninda.sabilla.chromeinc.network.entity.Inventory;

import static ninda.sabilla.chromeinc.activity.request.reqBarang.ReqBarangActivity.PASS_DATA;

class DaftarReqBarangAdapter extends RecyclerView.Adapter<DaftarReqBarangAdapter.ViewHolder> implements Filterable {
    List<Inventory> inventoryList;
    List<Inventory> inventoryListFull;
    Context context;
    private Filter inventoryListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Inventory> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(inventoryListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Inventory inventory : inventoryListFull) {
                    if (inventory.getNamaInv().toLowerCase().contains(filterPattern)) {
                        filteredList.add(inventory);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            inventoryList.clear();
            inventoryList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public DaftarReqBarangAdapter(Context context, List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
        this.inventoryListFull = new ArrayList<>(inventoryList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daftar_inventory, parent, false);
        return new DaftarReqBarangAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvNamaInventory.setText(inventoryList.get(position).getNamaInv());
        String status = inventoryList.get(position).getStatus().equals("1") ? "Selesai" : "Menunggu";
        holder.tvStatus.setText(status);

        String color = inventoryList.get(position).getStatus().equals("1") ? context.getResources().getString(R.string.status_selesai) : context.getResources().getString(R.string.status_menunggu);
        holder.tvStatus.setBackgroundColor(Color.parseColor(color));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReqBarangActivity.class);
                intent.putExtra(PASS_DATA, inventoryList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    @Override
    public Filter getFilter() {
        return inventoryListFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaInventory, tvStatus;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.constrain_layout);
            tvNamaInventory = itemView.findViewById(R.id.tv_nama_barang);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
