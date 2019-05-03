package com.example.dorenmi;


import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {
    TextView title, price, desc;
    Button rent;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    Bundle args;
    String id, email, renter;
    LoginSession loginSession;
    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        db = new DatabaseHelper(getActivity());
        title = (TextView) view.findViewById(R.id.trans_title);
        price = (TextView) view.findViewById(R.id.trans_price);
        desc = (TextView) view.findViewById(R.id.trans_desc);
        rent = (Button) view.findViewById(R.id.rent);

        loginSession = new LoginSession(getContext());

        //limit = loginSession.getLimit();
        email = loginSession.getEmail();
        args = getArguments();
        if (args != null){
            id = getArguments().getString("id_item");
        }
        String query_item = "SELECT * FROM item WHERE id_item='"+id+"'";
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query_item,null);
        if (cursor.moveToNext()){

            String emailtext=cursor.getString(cursor.getColumnIndex("email"));
            title.setText(cursor.getString(cursor.getColumnIndex("title")));
            desc.setText(cursor.getString(cursor.getColumnIndex("description")));
            price.setText("Rp. "+cursor.getInt(cursor.getColumnIndex("price")));
        }
        renter= cursor.getString(cursor.getColumnIndex("email"));
        cursor.close();
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "proses";
                String query_trans = "INSERT INTO trans values  (null, '"+status+"', '"+email+"', '"+renter+"', '"+id+"')";
                sqLiteDatabase.execSQL(query_trans);
                TransactionFragment transactionFragment = new TransactionFragment();
                transactionFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                ft.replace(R.id.flmain, transactionFragment);
                ft.commit();
            }
        });
        return view;
    }

}
