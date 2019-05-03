package com.example.dorenmi;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static java.lang.Boolean.FALSE;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    private Context context;
    private List<TransactionListFragment> TransactionListFragments;
    TransactionListFragment data;
    //int position;
    boolean expanded = false;

    public TransactionAdapter(Context context, List<TransactionListFragment> TransactionListFragments) {
        this.context = context;
        this.TransactionListFragments = TransactionListFragments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transaction_list,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        data = TransactionListFragments.get(position);

        holder.bind(data);
        holder.itemView.setFocusableInTouchMode(FALSE);
        holder.itemView.setFocusable(FALSE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current state of the item
                expanded = data.isExpanded();

                // Change the state
                data.setExpanded(!expanded);

                TransactionDetailFragment transactionDetailFragment;
                transactionDetailFragment = new TransactionDetailFragment();
                Bundle args = new Bundle();
                args.putString("id_trans", data.getId());
                transactionDetailFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flmain, transactionDetailFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

                // Notify the adapter that item has changed
                notifyItemChanged(position);

            }
        });

        holder.attendance.setText(data.getName());
        holder.date.setText("Di "+data.getStatus());
        holder.day.setText("Rp. "+data.getPrice());

    }

    @Override
    public int getItemCount() {
        return TransactionListFragments == null ? 0 : TransactionListFragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView attendance, date, day, note, time;
        View subitem;

        public ViewHolder(View itemView) {
            super(itemView);
            attendance = (TextView) itemView.findViewById(R.id.attendance_transaction);
            date = (TextView) itemView.findViewById(R.id.date_transaction);
            day = (TextView) itemView.findViewById(R.id.day_transaction);
            note = (TextView) itemView.findViewById(R.id.note_transaction);
            time = (TextView) itemView.findViewById(R.id.time_transaction);
            subitem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(TransactionListFragment data) {


            boolean expanded = data.isExpanded();

            //subitem.setVisibility(expanded ? View.VISIBLE : View.GONE);


        }
    }
}
