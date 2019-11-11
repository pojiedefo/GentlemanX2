package com.hua.gentlemanx2.main.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserDelegate extends BottomItemDelegate {

    @BindView(R.id.aiv_user_image)
    AppCompatImageView aivUserImage;
    @BindView(R.id.atv_user_login_prompt)
    AppCompatTextView atvUserLoginPrompt;
    @BindView(R.id.atv_user_login_signatrue)
    AppCompatTextView atvUserLoginSignatrue;
    @BindView(R.id.itv_user_login_prompt)
    IconTextView itvUserLoginPrompt;
    @BindView(R.id.rv_user_mycollection)
    RecyclerView rvUserMycollection;
    @BindView(R.id.llc_user_my_friends)
    LinearLayoutCompat llcUserMyFriends;
    @BindView(R.id.llc_user_my_message)
    LinearLayoutCompat llcUserMyMessage;
    @BindView(R.id.llc_user_my_history)
    LinearLayoutCompat llcUserMyHistory;
    @BindView(R.id.llc_user_my_setting)
    LinearLayoutCompat llcUserMySetting;
    Unbinder unbinder;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }

    //进入注册页面
    private void signUp() {
        getProxyActivity().getSupportDelegate().start(new SignUpDelegate());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.aiv_user_image, R.id.atv_user_login_prompt, R.id.atv_user_login_signatrue, R.id.itv_user_login_prompt, R.id.rv_user_mycollection, R.id.llc_user_my_friends, R.id.llc_user_my_message, R.id.llc_user_my_history, R.id.llc_user_my_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aiv_user_image:
                break;
            case R.id.atv_user_login_prompt:
                signUp();
                break;
            case R.id.atv_user_login_signatrue:
                break;
            case R.id.itv_user_login_prompt:
                signUp();
                break;
            case R.id.rv_user_mycollection:
                break;
            case R.id.llc_user_my_friends:
                break;
            case R.id.llc_user_my_message:
                break;
            case R.id.llc_user_my_history:
                break;
            case R.id.llc_user_my_setting:
                break;
        }
    }
}
