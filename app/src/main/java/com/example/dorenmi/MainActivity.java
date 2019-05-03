package com.example.dorenmi;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LoginSession loginSession;
    ItemFragment itemFragment;
    VendorFragment vendorFragment;
    View header;
    TextView nametext;
    String email,role;
    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginSession = new LoginSession(getApplicationContext());

        //limit = loginSession.getLimit();
        email = loginSession.getEmail();
        role = loginSession.getRole();
        args = new Bundle();
        args.putString("email", email);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        nametext = (TextView) header.findViewById(R.id.nav_header_text);
        nametext.setText(email);
        if (role.equals("user")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_vendor).setVisible(false);
            nav_Menu.findItem(R.id.nav_vendoradd).setVisible(false);
            itemFragment = new ItemFragment();
            itemFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flmain, itemFragment);
            ft.commit();
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_items).setVisible(false);
            vendorFragment = new VendorFragment();
            vendorFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flmain, vendorFragment);
            ft.commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_items) {
            itemFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
            ft.replace(R.id.flmain, itemFragment);
            ft.commit();
        } else if (id == R.id.nav_transanction) {
            TransactionFragment transactionFragment = new TransactionFragment();
            transactionFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
            ft.replace(R.id.flmain, transactionFragment);
            ft.commit();

        } else if (id == R.id.nav_vendor) {
            vendorFragment = new VendorFragment();
            vendorFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flmain, vendorFragment);
            ft.commit();
        } else if (id == R.id.nav_vendoradd) {
            VendorAddFragment vendorAddFragment;
            vendorAddFragment = new VendorAddFragment();
            vendorAddFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flmain, vendorAddFragment);
            ft.commit();
        } else if (id == R.id.nav_logout) {
            loginSession.setLogin_status(loginSession.login_status_session, false);
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
