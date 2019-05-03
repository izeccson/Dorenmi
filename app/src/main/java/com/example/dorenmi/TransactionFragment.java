package com.example.dorenmi;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Network;
import android.os.Bundle;
import android.support.constraint.solver.Cache;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    String extranip, role;
    JSONArray response;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    List<TransactionListFragment> TransactionListsFragment;
    TransactionListFragment transactionListFragment;
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
    LoginSession loginSession;

    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        getActivity().setTitle("Transaksi");
        db = new DatabaseHelper(getActivity());
        shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        loginSession = new LoginSession(getContext());
        role = loginSession.getRole();
        args = getArguments();
        if (args != null){
            extranip = getArguments().getString("email");
        }
        //mProgressView = view.findViewById(R.id.history_progress);

        TransactionListsFragment = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.transaction);
        recyclerView.setHasFixedSize(true);
        adapter = new TransactionAdapter(getActivity(), TransactionListsFragment);
        load_data_transaction();
        layoutManager = new LinearLayoutManager(getActivity());
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    private void load_data_transaction() {
        String query;
        if (role.equals("vendor")){
            query="SELECT * FROM item, trans WHERE item.id_item=trans.id_item AND trans.renter='"+extranip+"'";
        } else {
            query="SELECT * FROM item, trans WHERE item.id_item=trans.id_item AND trans.tenant='"+extranip+"'";
        }
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        int total = cursor.getCount();
        while (cursor.moveToNext()) {
            transactionListFragment = new TransactionListFragment();
            transactionListFragment.setId(cursor.getString(cursor.getColumnIndex("id_trans")));
            transactionListFragment.setName(cursor.getString(cursor.getColumnIndex("title")));
            transactionListFragment.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            transactionListFragment.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            TransactionListsFragment.add(transactionListFragment);
            //Toast.makeText(getContext(), jsonObject.getString("error_msg"), Toast.LENGTH_LONG).show();
        }
        recyclerView.setAdapter(adapter);

    }

}
