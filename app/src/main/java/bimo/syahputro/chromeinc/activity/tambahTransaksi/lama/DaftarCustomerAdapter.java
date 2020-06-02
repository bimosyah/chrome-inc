package bimo.syahputro.chromeinc.activity.tambahTransaksi.lama;

import android.content.Context;
import android.content.Intent;
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

import bimo.syahputro.chromeinc.R;
import bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity;
import bimo.syahputro.chromeinc.network.entity.Customer;

import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.ALAMAT_CUSTOMER;
import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.ID_CUSTOMER;
import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.NAMA_CUSTOMER;
import static bimo.syahputro.chromeinc.activity.tambahTransaksi.baru.TransaksiCustomerBaruActivity.NOTELP_CUSTOMER;

class DaftarCustomerAdapter extends RecyclerView.Adapter<DaftarCustomerAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Customer> customerList;
    List<Customer> customerListFull;
    private Filter daftarCustomerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Customer> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(customerListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Customer customer : customerListFull) {
                    if (customer.getNamaCustomer().toLowerCase().contains(filterPattern)) {
                        filteredList.add(customer);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            customerList.clear();
            customerList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

    };

    public DaftarCustomerAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
        this.customerListFull = new ArrayList<>(customerList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daftar_customer, parent, false);
        return new DaftarCustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvNamaCustomer.setText(customerList.get(position).getNamaCustomer());

        final String id_customer = customerList.get(position).getIdCustomer();
        final String nama_customer = customerList.get(position).getNamaCustomer();
        final String alamat_customer = customerList.get(position).getAlamat();
        final String notelp_customer = customerList.get(position).getNoTelp();

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransaksiCustomerBaruActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(ID_CUSTOMER, id_customer);
                bundle.putString(NAMA_CUSTOMER, nama_customer);
                bundle.putString(ALAMAT_CUSTOMER, alamat_customer);
                bundle.putString(NOTELP_CUSTOMER, notelp_customer);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    @Override
    public Filter getFilter() {
        return daftarCustomerFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaCustomer;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.constrain_layout);
            tvNamaCustomer = itemView.findViewById(R.id.tv_nama_customer);
        }

    }
}
