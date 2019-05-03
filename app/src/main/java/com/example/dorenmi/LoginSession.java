package com.example.dorenmi;

import android.content.Context;
import android.content.SharedPreferences;

import static java.lang.Boolean.FALSE;

public class LoginSession {

    String pref_name = "LoginSession";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String nip_session="nip",name_session="name",agency_session="agency",phone_session="phone",email_session="email",
            login_status_session="login_status", role_session="role", job_session="job", limit_session="limit";

    public LoginSession(Context context) {
        sharedPreferences = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getNip() {
        return sharedPreferences.getString(nip_session,"");
    }

    public void setNip(String key, String nip_session) {
        editor.putString(key,nip_session);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(name_session,"");
    }

    public void setName(String key,String name_session) {
        editor.putString(key,name_session);
        editor.commit();
    }

    public String getAgency() {
        return sharedPreferences.getString(agency_session,"");
    }

    public void setAgency(String key,String agency_session) {
        editor.putString(key,agency_session);
        editor.commit();
    }

    public String getJob() {
        return sharedPreferences.getString(job_session,"");
    }

    public void setJob(String key,String job_session) {
        editor.putString(key,job_session);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(email_session,"");
    }

    public void setEmail(String key,String email_session) {
        editor.putString(key,email_session);
        editor.commit();
    }

    public String getRole() {
        return sharedPreferences.getString(role_session,"");
    }

    public void setRole(String key,String role_session) {
        editor.putString(key,role_session);
        editor.commit();
    }

    public String getPhone() {
        return sharedPreferences.getString(phone_session,"");
    }

    public void setPhone(String key,String phone_session) {
        editor.putString(key,phone_session);
        editor.commit();
    }

    public long getLimit() {
        return sharedPreferences.getLong(limit_session,0);
    }

    public void setLimit(String key,long limit_session) {
        editor.putLong(key,limit_session);
        editor.commit();
    }

    public Boolean getLogin_status() {
        return sharedPreferences.getBoolean(login_status_session,FALSE);
    }

    public void setLogin_status(String key,Boolean login_status_session) {
        editor.putBoolean(key,login_status_session);
        editor.commit();
    }

}
