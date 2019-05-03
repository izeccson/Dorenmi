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

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ViewHolder> {

    private Context context;
    private List<VendorListFragment> VendorListFragments;
    VendorListFragment data;
    boolean expanded = false;

    public VendorAdapter(Context context, List<VendorListFragment> vendorListFragments) {
        this.context = context;
        VendorListFragments = vendorListFragments;
    }

    @NonNull
    @Override
    public VendorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_vendor_list,parent,false);
        return new VendorAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull VendorAdapter.ViewHolder holder, final int position) {
        data = VendorListFragments.get(position);

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
                VendorDetailFragment vendorDetailFragment;
                vendorDetailFragment = new VendorDetailFragment();
                Bundle args = new Bundle();
                args.putString("id_item", data.getId());
                vendorDetailFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flmain, vendorDetailFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

                // Notify the adapter that item has changed
                notifyItemChanged(position);

            }
        });

        holder.attendance.setText(data.getTitle());
        holder.date.setText("Rp. "+data.getPrice());

    }

    @Override
    public int getItemCount() {
        return VendorListFragments == null ? 0 : VendorListFragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView attendance, date, day, note, time;
        View subitem;

        public ViewHolder(View itemView) {
            super(itemView);
            attendance = (TextView) itemView.findViewById(R.id.attendance_vendor);
            date = (TextView) itemView.findViewById(R.id.date_vendor);
            day = (TextView) itemView.findViewById(R.id.day_vendor);
            note = (TextView) itemView.findViewById(R.id.note_vendor);
            time = (TextView) itemView.findViewById(R.id.time_vendor);
            //subitem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(VendorListFragment data) {


            boolean expanded = data.isExpanded();

        }
    }

}
