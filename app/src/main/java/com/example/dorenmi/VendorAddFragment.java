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


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorAddFragment extends Fragment {

    EditText title, price, desc;
    Button add,delete;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    Bundle args;
    String id, email, renter;
    LoginSession loginSession;

    public VendorAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor_add, container, false);
        getActivity().setTitle("Tambah Barang");
        title = (EditText) view.findViewById(R.id.add_vendor_title);
        price = (EditText) view.findViewById(R.id.add_vendor_price);
        desc = (EditText) view.findViewById(R.id.add_vendor_desc);
        add = (Button) view.findViewById(R.id.add);
        db = new DatabaseHelper(getActivity());
        loginSession = new LoginSession(getContext());

        //limit = loginSession.getLimit();
        email = loginSession.getEmail();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String titletext, desctext, pricetext;
                titletext = title.getText().toString();
                desctext = desc.getText().toString();
                pricetext = price.getText().toString();
                String query_trans = "INSERT INTO item values (null, '"+titletext+"', '"+desctext+"', '"+pricetext+"', '"+email+"')";
                sqLiteDatabase = db.getReadableDatabase();
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


