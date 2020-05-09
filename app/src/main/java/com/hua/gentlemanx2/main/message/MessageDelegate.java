package com.hua.gentlemanx2.main.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessageDelegate extends BottomItemDelegate {

    @BindView(R.id.conversation_layout)
    ConversationLayout conversationLayout;
    Unbinder unbinder;

    @Override
    public Object setLayout() {
        return R.layout.delegate_message;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {
        conversationLayout.initDefault();
        TitleBarLayout titleBar = conversationLayout.getTitleBar();
        LinearLayout rightGroup = titleBar.getRightGroup();
        //设置标题
        //初始化消息会话面板
        conversationLayout.initDefault();
        titleBar.getMiddleTitle().setText("消息");

        rightGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        //test
//        rightGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getProxyActivity().getSupportDelegate().start(new TestDelegate());
//            }
//        });
    }

    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(getProxyActivity(), view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.menu_conversation, popupMenu.getMenu());
        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.startconversation:
                        //TODO 发起会话

                        final ConversationListLayout conversationList = conversationLayout.getConversationList();
                        conversationList.setItemTopTextSize(16); // 设置adapter item中top文字大小
                        conversationList.setItemBottomTextSize(12);// 设置adapter item中bottom文字大小
                        conversationList.setItemDateTextSize(10);// 设置adapter item中timeline文字大小
                        conversationList.setItemAvatarRadius(5);// 设置adapter item头像圆角大小
                        conversationList.disableItemUnreadDot(false);// 设置adapter item是否不显示未读红点，默认显示

                        break;
                    case R.id.creategroup:
                        //TODO 创建讨论住
                        break;
                    case R.id.createchat:
                        //TODO 创建群聊
                        break;
                    case R.id.createchatroom:
                        //TODO 创建聊天室
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
