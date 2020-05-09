package com.hua.gentlemanx2.main.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignatureDelegate extends GxDelegate {

    Unbinder unbinder;
    @BindView(R.id.common_title)
    FrameLayout commonTitle;
    @BindView(R.id.acet_edit)
    EditText editText;
    @BindView(R.id.acb_signature)
    AppCompatButton acbSignature;

    @Override
    public Object setLayout() {
        return R.layout.delegate_signatrue;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        delegate = getParentDelegate();
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private GxDelegate delegate;

    @OnClick({R.id.common_title, R.id.acb_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title:
                getSupportDelegate().start(new IntroduceBottomDelegate());
                break;
            case R.id.acb_signature:
                final String signature = editText.getText().toString();
                SharedPreferences.Editor localEditor = getProxyActivity().getSharedPreferences("user", 0).edit();
                localEditor.putString("signature", signature);
                getSupportDelegate().start(new IntroduceBottomDelegate());
                break;
        }
    }
}
