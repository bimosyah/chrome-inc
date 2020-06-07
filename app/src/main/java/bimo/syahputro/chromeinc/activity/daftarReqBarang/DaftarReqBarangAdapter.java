package bimo.syahputro.chromeinc.activity.daftarReqBarang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.network.entity.Inventory;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNamaInventory.setText(inventoryList.get(position).getNamaInv());
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
        TextView tvNamaInventory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaInventory = itemView.findViewById(R.id.tv_nama_barang);
        }
    }
}
