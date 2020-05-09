package com.hua.gentlemanx2.main.user;

import android.content.SharedPreferences;
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
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;

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
    AppCompatTextView mUserSignatrue;
    @BindView(R.id.rv_user_mycollection)
    RecyclerView mRecyclerView;

    Unbinder unbinder;
    @BindView(R.id.llc_my_collections)
    LinearLayoutCompat llcMyCollections;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user;
    }

    public static GxDelegate delegate;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        SharedPreferences user = getSupportDelegate().getActivity().getSharedPreferences("user", 0);
        String name = user.getString("name", "");
        atvUserLoginPrompt.setText(name);
        atvUserLoginPrompt.setTextSize(20);
        delegate = getParentDelegate();
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
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick({R.id.atv_user_login_prompt, R.id.atv_user_login_signatrue,
            R.id.rv_user_mycollection,
            R.id.llc_my_collections})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.atv_user_login_prompt:
                break;
            case R.id.atv_user_login_signatrue:
                delegate.getSupportDelegate().start(new SignatureDelegate());
                break;
            case R.id.llc_my_collections:
                delegate.getSupportDelegate().start(new MyCollectionDelegate());
                break;
            case R.id.rv_user_mycollection:
                break;
            default:
                break;

        }
    }

}
