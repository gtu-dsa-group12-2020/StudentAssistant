package com.group12.studentassistant.view.fragment;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.group12.studentassistant.model.User;
import com.group12.studentassistant.viewModel.SignUpViewModel;

import static com.group12.studentassistant.MyApplication.mAuth;
import static com.group12.studentassistant.MyApplication.mUserReference;
import static com.group12.studentassistant.view.activity.MainActivity.replaceFragment;

public class SignUpFragment extends Fragment {

    private SignUpViewModel mViewModel;
    private Context mContext;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        mContext = getContext();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText firstNameEditText = view.findViewById(R.id.first_name);
        EditText lastNameEditText = view.findViewById(R.id.last_name);
        AppCompatAutoCompleteTextView emailEditText = view.findViewById(R.id.email);
        EditText passwordEditText = view.findViewById(R.id.password);
        Button signUpButton = view.findViewById(R.id.sign_up_button);

        // Add Text Watcher on name input text
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewModel.setFirstName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewModel.setLastName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        emailEditText.addTextChangedListener(new TextWatcher() {
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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = mViewModel.getFirstName().getValue();
                final String lastName = mViewModel.getLastName().getValue();
                final String email = mViewModel.getEmail().getValue();
                final String password = mViewModel.getPassword().getValue();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            MyApplication.showText(mContext.getResources().getString(R.string.sign_up_failed));
                        } else {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            final String uid = task.getResult().getUser().getUid();

                                            User user = new User();
                                            user.setFirstName(firstName);
                                            user.setLastName(lastName);
                                            user.setEmail(email);

                                            mUserReference.child(uid).setValue(user);

                                            MyApplication.showText(mContext.getResources().getString(R.string.sign_up_successful));

                                            mViewModel.setFirstName("");
                                            mViewModel.setLastName("");
                                            mViewModel.setEmail("");
                                            mViewModel.setPassword("");

                                            replaceFragment(SignInFragment.newInstance());
                                        }
                                    }, 500);
                        }
                    }
                });
            }
        });
    }
}
