package com.example.dorenmi;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorFragment extends Fragment {

    String extranip;
    JSONArray response;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    List<VendorListFragment> VendorListsFragment;
    VendorListFragment vendorListFragment;
    TextView historytext;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    Bundle args;
    TextView errorview;
    Button retry;
    RecyclerView.LayoutManager layoutManager;
    View mProgressView;
    int shortAnimTime;
    int num_error=0;

    public VendorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor, container, false);
        getActivity().setTitle("Barang Vendor");
        db = new DatabaseHelper(getActivity());
        shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        args = getArguments();
        if (args != null){
            extranip = getArguments().getString("email");
        }
        //mProgressView = view.findViewById(R.id.history_progress);

        VendorListsFragment = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.vendor);
        recyclerView.setHasFixedSize(true);
        adapter = new VendorAdapter(getActivity(), VendorListsFragment);
        load_data_transanction();
        layoutManager = new LinearLayoutManager(getActivity());
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    private void load_data_transanction() {
        Log.d("TEST", "email :"+extranip);
        String query="SELECT * FROM item,account WHERE item.email=account.email AND item.email='"+extranip+"'";
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        int total = cursor.getCount();
        Log.d("TEST", "total :"+total);
        while (cursor.moveToNext()) {
            vendorListFragment = new VendorListFragment();
            vendorListFragment.setId(cursor.getString(cursor.getColumnIndex("id_item")));
            vendorListFragment.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            vendorListFragment.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            VendorListsFragment.add(vendorListFragment);
            //Toast.makeText(getContext(), jsonObject.getString("error_msg"), Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);

    }
}
