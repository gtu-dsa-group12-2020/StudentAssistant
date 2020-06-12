package com.group12.studentassistant.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.group12.studentassistant.R;
import com.group12.studentassistant.view.fragment.HomeFragment;
import com.group12.studentassistant.view.fragment.SignInFragment;

import static com.group12.studentassistant.MyApplication.mAuth;
import static com.group12.studentassistant.MyApplication.mAuthCurrentUser;
import static com.group12.studentassistant.MyApplication.mAuthStateListener;
import static com.group12.studentassistant.MyApplication.mContext;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private static FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mFragmentManager = getSupportFragmentManager();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mAuthCurrentUser = firebaseAuth.getCurrentUser();
                if (mAuthCurrentUser != null) {
                    Log.d(TAG, mAuthCurrentUser.getUid() + " - " + mAuthCurrentUser.getDisplayName());
                    replaceFragment(HomeFragment.newInstance());
                } else {
                    replaceFragment(SignInFragment.newInstance());
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem signOutMenu = menu.findItem(R.id.action_sign_out);
        signOutMenu.setVisible(mAuthCurrentUser != null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            mAuth.signOut();
            invalidateOptionsMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
    }
}