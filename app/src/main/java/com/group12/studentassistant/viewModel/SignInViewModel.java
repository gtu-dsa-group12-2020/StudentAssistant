package com.group12.studentassistant.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignInViewModel extends ViewModel {

    /**
     * Live Data Instance
     */
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();

    public void setEmail(String email) {
        mEmail.setValue(email);
    }
    public LiveData<String> getEmail() {
        return mEmail;
    }

    public void setPassword(String password) {
        mPassword.setValue(password);
    }

    public LiveData<String> getPassword() {
        return mPassword;
    }
}
