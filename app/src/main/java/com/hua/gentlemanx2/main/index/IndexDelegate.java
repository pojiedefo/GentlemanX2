package com.hua.gentlemanx2.main.index;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.Constants;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.hua.gentlemanx2.ui.decoration.BaseDecoration;
import com.hua.gentlemanx2.ui.refresh.RefreshHandler;
import com.joanzapata.iconify.widget.IconTextView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class IndexDelegate extends BottomItemDelegate {
    private static final int REQUEST_CODE = 100;
    @BindView(R.id.rv_index)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_index)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.icon_index_scan)
    IconTextView mIconScan;
    @BindView(R.id.et_search_view)
    AppCompatEditText mSearchView;
    @BindView(R.id.icon_btn_search)
    IconTextView mIconMessage;
    @BindView(R.id.tb_index)
    Toolbar mToolbar;
    Unbinder unbinder;

    private RefreshHandler mRefreshHandler;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private GxDelegate delegate;

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
        delegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(delegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage(Constants.URL + "goods/show");
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public static String input;

    @OnClick({R.id.icon_index_scan, R.id.icon_btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_index_scan:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.CAMERA)) {
                        Toast.makeText(getContext(),"请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                    }
                    // 申请权限
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA}, 1000);
                    return;
                }
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.icon_btn_search:
                input = mSearchView.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getContext(), "请输入要搜索的关键词", Toast.LENGTH_SHORT).show();
                } else {
                    checkStatus(input);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void checkStatus(final String input) {
        RestClient.builder()
                .url(Constants.URL + "goods/find")
                .params("input", input)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        String status = JSON.parseObject(response).getString("status");
                        if (status.equals("0")) {
                            final Bundle args = new Bundle();
                            args.putString("input", input);
                            delegate.setArguments(args);
                            delegate.getSupportDelegate().start(new SearchGoodsDelegate());
                        } else {
                            Toast.makeText(getContext(), "没有此产品", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build()
                .post();
    }

}
