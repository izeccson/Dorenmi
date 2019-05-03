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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private Context context;
    private List<ItemListFragment> ItemListFragments;
    ItemListFragment data;
    //int position;
    boolean expanded = false;


    public ItemAdapter(Context context, List<ItemListFragment> ItemListFragments) {
        this.context = context;
        this.ItemListFragments = ItemListFragments;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_list,parent,false);
        return new ItemAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, final int position) {
        data = ItemListFragments.get(position);

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
                ItemDetailFragment itemDetailFragment;
                itemDetailFragment = new ItemDetailFragment();
                Bundle args = new Bundle();
                args.putString("id_item", data.getId());
                itemDetailFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flmain, itemDetailFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

                // Notify the adapter that item has changed
                notifyItemChanged(position);

            }
        });

        holder.attendance.setText(data.getName());
        holder.date.setText("Rp. "+data.getPrice());

    }

    @Override
    public int getItemCount() {
        return ItemListFragments == null ? 0 : ItemListFragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView attendance, date, day, note, time;
        View subitem;

        public ViewHolder(View itemView) {
            super(itemView);
            attendance = (TextView) itemView.findViewById(R.id.attendance_item);
            date = (TextView) itemView.findViewById(R.id.date_item);
            day = (TextView) itemView.findViewById(R.id.day_item);
            note = (TextView) itemView.findViewById(R.id.note_item);
            time = (TextView) itemView.findViewById(R.id.time_item);
            //subitem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(ItemListFragment data) {


            boolean expanded = data.isExpanded();

        }
    }
}
