package com.example.dorenmi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static java.lang.Boolean.TRUE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginVendorActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    EditText mEmailView ,mPasswordView;
    TextView loginuser, register;
    View mProgressView;
    View mLoginFormView;

    DatabaseHelper db;
    long limittime;
    int limithour, limitminute, limitsecond;
    String nip, password, error_login, niptext, emailtext, nametext, agencytext, phonetext, roletext, jobtext, limittext, batastext;
    AlertDialog.Builder builder;
    Bundle bundle;
    AlertDialog alert;
    boolean login,loginbool;

    LoginSession loginSession;
    Context mContext;
    SimpleDateFormat sdftime;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vendor);
        loginSession = new LoginSession(getApplicationContext());
        db = new DatabaseHelper(this);
        mEmailView = (EditText) findViewById(R.id.email_login_vendor);
        builder = new AlertDialog.Builder(this);
        if (loginSession.getLogin_status()){
            startActivity(new Intent(LoginVendorActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        mPasswordView = (EditText) findViewById(R.id.password_login_vendor);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.submit_login_vendor);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginuser = (TextView) findViewById(R.id.login);
        loginuser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movetovendor = new Intent(LoginVendorActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(movetovendor);
                finish();
            }
        });

        register = (TextView) findViewById(R.id.register_vendor);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movetoregister = new Intent(LoginVendorActivity.this, RegisterVendorActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(movetoregister);
                finish();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        nip = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (password.isEmpty()) {
            mPasswordView.setError("Please fill out this field");
            focusView = mPasswordView;
            cancel = true;
        }
        else if (password.length()<6) {
            mPasswordView.setError("Password is too short");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid nip address.
        if (nip.isEmpty()) {
            mEmailView.setError("Please fill out this field");
            focusView = mEmailView;
            cancel = true;
        } else if (!nip.contains("@")) {
            mEmailView.setError("wrong email format");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            doLogin();
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents login/registration task used to authenticate
     * the user.
     */
    private void doLogin() {
        Boolean result = db.checkAccount(nip, password);
        if (result == TRUE){
            String query="SELECT * FROM account WHERE email='"+nip+"'";
            sqLiteDatabase = db.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if (cursor.moveToNext()){

                String emailtext=cursor.getString(cursor.getColumnIndex("email"));
                Intent intent = new Intent(LoginVendorActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                bundle = new Bundle();
                bundle.putBoolean("login", loginbool);
                bundle.putString("email", emailtext);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            cursor.close();
        } else {
            Toast.makeText(LoginVendorActivity.this, "Email or Password invalid", Toast.LENGTH_SHORT);
        }
    }
}

