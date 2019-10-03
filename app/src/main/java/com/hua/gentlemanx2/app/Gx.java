package com.hua.gentlemanx2.app;

import android.content.Context;

import java.util.HashMap;

public final class Gx {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getGxConfigs();
    }


}
