package com.hua.gentlemanx2.main.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactDelegate extends BottomItemDelegate {

    @BindView(R.id.contact_layout)
    ContactLayout contactLayout;
    Unbinder unbinder;

    @Override
    public Object setLayout() {
        return R.layout.delegate_compass;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        contactLayout.initDefault();
        TitleBarLayout titleBar = contactLayout.getTitleBar();
        TextView middleTitle = titleBar.getMiddleTitle();
        LinearLayout rightGroup = titleBar.getRightGroup();
        rightGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        middleTitle.setText("美人们");
    }

    private void showPopupMenu(final View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(getProxyActivity(), view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.menu_contact, popupMenu.getMenu());
        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                switch (view.getId()) {
                    case R.id.addfriends:
                        //TODO 添加好友的逻辑
                        Log.e("大哥哥","????");
                        break;
                    case R.id.addchat:
                        //TODO 添加群聊的逻辑
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });
    }

    @Override
    public void post(Runnable runnable) {

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
}
