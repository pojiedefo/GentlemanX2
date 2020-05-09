package com.hua.gentlemanx2.app;

import android.app.Application;

import com.hua.gentlemanx2.icon.FontIntroduceMudule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MyApplication extends Application {

    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        Gx.init(this)
                .withApiHost("http://127.0.0.1/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontIntroduceMudule())
                .configure();

        //微信转发
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APPID, true);
        mWxApi.registerApp(Constants.WECHAT_APPID);

        //第三方mob 集成微信转发
        MobSDK.submitPolicyGrantResult(true, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void aVoid) {
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });

        //二维码
        ZXingLibrary.initDisplayOpinion(this);

//        DataBaseManager.getInstance().init(this);
        // 配置 Config，请按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new TIMSdkConfig(Constants.SDKAPPID));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());
        TUIKit.init(this, Constants.SDKAPPID, configs);
    }

}
