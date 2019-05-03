package com.example.dorenmi;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    String extranip;
    JSONArray response;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    List<ItemListFragment> ItemListsFragment;
    ItemListFragment itemList;
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

    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        getActivity().setTitle("Barang");
        db = new DatabaseHelper(getActivity());
        shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        args = getArguments();
        if (args != null){
            extranip = getArguments().getString("email");
        }
        //mProgressView = view.findViewById(R.id.history_progress);

        ItemListsFragment = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.item);
        recyclerView.setHasFixedSize(true);
        adapter = new ItemAdapter(getActivity(), ItemListsFragment);
        load_data_transanction();
        layoutManager = new LinearLayoutManager(getActivity());
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    private void load_data_transanction() {
        String query="SELECT * FROM item";
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        int total = cursor.getCount();
        while (cursor.moveToNext()) {
                itemList = new ItemListFragment();
                itemList.setId(cursor.getString(cursor.getColumnIndex("id_item")));
                itemList.setName(cursor.getString(cursor.getColumnIndex("title")));
                itemList.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                ItemListsFragment.add(itemList);
                //Toast.makeText(getContext(), jsonObject.getString("error_msg"), Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);

    }

}
