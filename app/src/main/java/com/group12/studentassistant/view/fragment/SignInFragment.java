package com.group12.studentassistant.view.fragment;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.group12.studentassistant.MyApplication;
import com.group12.studentassistant.R;
import com.group12.studentassistant.viewModel.SignInViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.group12.studentassistant.MyApplication.mAuth;
import static com.group12.studentassistant.view.activity.MainActivity.replaceFragment;

public class SignInFragment extends Fragment {

    private SignInViewModel mViewModel;
    private Context mContext;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_in_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        mContext = getContext();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatAutoCompleteTextView usernameEditText = view.findViewById(R.id.email);
        EditText passwordEditText = view.findViewById(R.id.password);
        Button signInButton = view.findViewById(R.id.sign_in_button);
        Button signUpButton = view.findViewById(R.id.sign_up_button);

        // Add Text Watcher on name input text
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewModel.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewModel.setPassword(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mViewModel.getEmail().getValue();
                final String password = mViewModel.getPassword().getValue();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmailAndPassword:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a placeName to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmailAndPassword", task.getException());
                            MyApplication.showText(mContext.getResources().getString(R.string.sign_in_failed));
                        } else {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            mViewModel.setEmail("");
                                            mViewModel.setPassword("");

                                            Activity activity = getActivity();
                                            if (activity != null) activity.invalidateOptionsMenu();
                                        }
                                    }, 500);
                        }
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(SignUpFragment.newInstance());
            }
        });
    }

}
