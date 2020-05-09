package com.hua.gentlemanx2.launcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hua.gentlemanx2.app.AccountManager;

public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {
        final String status = JSON.parseObject(response).getString("status");
        final String msg = JSON.parseObject(response).getString("msg");
        //已经注册并登录成功了
        if (status.equals("0")) {
            AccountManager.setSignState(true);
            signListener.onSignInSuccess();
        } else {
            signListener.onSignInFailure(msg);
        }
    }


    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        //已经注册并登录成功了
        final String status = JSON.parseObject(response).getString("status");
        final String msg = JSON.parseObject(response).getString("msg");
        if (status.equals("0")) {
            AccountManager.setSignState(true);
            signListener.onSignUpSuccess();
        } else {
            signListener.onSignUpFailure(msg);
        }
    }
}
