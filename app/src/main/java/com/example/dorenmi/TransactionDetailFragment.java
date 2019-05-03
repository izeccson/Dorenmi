package com.example.dorenmi;


import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionDetailFragment extends Fragment {

    TextView title, price, desc, status;
    Button rent;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    Bundle args;
    String id, email, renter, stat,role;
    LoginSession loginSession;

    public TransactionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        db = new DatabaseHelper(getActivity());
        status = (TextView) view.findViewById(R.id.trans_status);
        title = (TextView) view.findViewById(R.id.trans_title);
        price = (TextView) view.findViewById(R.id.trans_price);
        desc = (TextView) view.findViewById(R.id.trans_desc);
        rent = (Button) view.findViewById(R.id.conf_rent);

        loginSession = new LoginSession(getContext());

        //limit = loginSession.getLimit();
        email = loginSession.getEmail();
        role = loginSession.getRole();
        args = getArguments();
        if (args != null){
            id = getArguments().getString("id_trans");
        }
        String query_item = "SELECT * FROM trans WHERE id_trans='"+id+"'";
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query_item,null);
        if (cursor.moveToNext()){

            String emailtext=cursor.getString(cursor.getColumnIndex("email"));
            title.setText(cursor.getString(cursor.getColumnIndex("title")));
            desc.setText(cursor.getString(cursor.getColumnIndex("description")));
            stat = cursor.getString(cursor.getColumnIndex("status"));
            status.setText("Di "+stat);
            price.setText("Rp. "+cursor.getInt(cursor.getColumnIndex("price")));
            Log.d("TEST", stat);
        }
        //renter= cursor.getString(cursor.getColumnIndex("email"));

        cursor.close();
        if (stat.equals("sewa") || role.equals("user")){
            rent.setVisibility(View.GONE);
        }

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statustext ="Sewa";
                String query_trans = "UPDATE trans SET status='"+statustext+"' WHERE id_trans='"+id+"'";
                sqLiteDatabase = db.getReadableDatabase();
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
