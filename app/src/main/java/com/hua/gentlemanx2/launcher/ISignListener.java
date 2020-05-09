package com.hua.gentlemanx2.launcher;

public interface ISignListener {

    void onSignInSuccess();

    void onSignUpSuccess();

    void onSignUpFailure(String msg);

    void onSignInFailure(String msg);
}