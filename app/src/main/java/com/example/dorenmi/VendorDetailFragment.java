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
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorDetailFragment extends Fragment {


    EditText title, price, desc;
    Button update,delete;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    Bundle args;
    String id, email, renter;
    LoginSession loginSession;

    public VendorDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor_detail, container, false);
        db = new DatabaseHelper(getActivity());
        title = (EditText) view.findViewById(R.id.vendor_title);
        price = (EditText) view.findViewById(R.id.vendor_price);
        desc = (EditText) view.findViewById(R.id.vendor_desc);
        update = (Button) view.findViewById(R.id.update);
        delete = (Button) view.findViewById(R.id.delete);

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
            price.setText(""+cursor.getInt(cursor.getColumnIndex("price")));
        }
        renter= cursor.getString(cursor.getColumnIndex("email"));
        cursor.close();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String titletext, desctext, pricetext;
                titletext = title.getText().toString();
                desctext = desc.getText().toString();
                pricetext = price.getText().toString();
                String query_trans = "UPDATE item SET title='"+titletext+"', description='"+desctext+"', price='"+pricetext+"' WHERE id_item='"+id+"'";
                sqLiteDatabase.execSQL(query_trans);
                VendorFragment vendorFragment = new VendorFragment();
                vendorFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                ft.replace(R.id.flmain, vendorFragment);
                ft.commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String query_trans = "DELETE FROM item WHERE id_item='"+id+"'";
                sqLiteDatabase.execSQL(query_trans);
                VendorFragment vendorFragment = new VendorFragment();
                vendorFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                ft.replace(R.id.flmain, vendorFragment);
                ft.commit();
            }
        });

        return view;
    }

}
