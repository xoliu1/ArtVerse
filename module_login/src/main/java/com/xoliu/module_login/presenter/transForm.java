package com.xoliu.module_login.presenter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;
import com.xoliu.module_login.model.mPassword;
import com.xoliu.module_login.Iview.mView;

public class transForm {
    public mPassword model;

    mView view;

    public transForm() {
        model = new mPassword();
    }
    public void setView(mView view){
        this.view = view;
    }

    public void SetText(TextInputLayout textInputLayout){
        textInputLayout.setErrorEnabled(true);

        textInputLayout.setCounterEnabled(true);

        textInputLayout.setCounterMaxLength(18);
        textInputLayout.getEditText().setMaxLines(1);
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textInputLayout.getEditText().getText().toString().trim().length()>18){
                    textInputLayout.setError("用户名长度超出限制");
                }else{
                    textInputLayout.setError(null);
                }
            }
        });
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        textInputLayout.getEditText().setFilters(new InputFilter[]{filter});
    }
    public void SetText2(TextInputLayout textInputLayout){
        textInputLayout.setErrorEnabled(true);

        textInputLayout.setCounterEnabled(true);

        textInputLayout.setCounterMaxLength(18);
        textInputLayout.getEditText().setMaxLines(1);
        textInputLayout.setPasswordVisibilityToggleEnabled(true);
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textInputLayout.getEditText().getText().toString().trim().length()>18){
                    textInputLayout.setError("用户名长度超出限制");
                }else{
                    textInputLayout.setError(null);
                }
            }
        });
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        textInputLayout.getEditText().setFilters(new InputFilter[]{filter});
    }

}
