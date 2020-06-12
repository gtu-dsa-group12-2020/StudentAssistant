package com.group12.studentassistant.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    /**
     * Live Data Instance
     */
    private MutableLiveData<String> mFirstName = new MutableLiveData<>();
    private MutableLiveData<String> mLastName = new MutableLiveData<>();
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();

    public void setFirstName(String password) {
        mFirstName.setValue(password);
    }

    public LiveData<String> getFirstName() {
        return mFirstName;
    }

    public void setLastName(String password) {
        mLastName.setValue(password);
    }

    public LiveData<String> getLastName() {
        return mLastName;
    }

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
