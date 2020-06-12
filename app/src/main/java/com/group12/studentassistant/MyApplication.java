package com.group12.studentassistant;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group12.studentassistant.utils.AppContants;

public class MyApplication extends Application {

    private final String TAG = MyApplication.class.getSimpleName();

    /* Data from the authenticated user */
    public static FirebaseAuth mAuth;

    /* Listener for Firebase session changes */
    public static FirebaseAuth.AuthStateListener mAuthStateListener;

    /* Firebase user */
    public static FirebaseUser mAuthCurrentUser;

    /*  Firebase References */
    public static FirebaseDatabase mDatabase;
    public static DatabaseReference mMainReference;
    public static DatabaseReference mUserReference;
    public static DatabaseReference mMessageReference;

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        FirebaseApp.initializeApp(this);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mMainReference = mDatabase.getReference();
        mUserReference = mDatabase.getReference(AppContants.USER_REFERENCE_KEY);
        mMessageReference = mDatabase.getReference(AppContants.MESSAGE_REFERENCE_KEY);
    }

    public static String getUid() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public static void showText(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}