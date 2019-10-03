package com.hua.gentlemanx2.app;

import java.util.HashMap;

public class Configurator {

    private static final HashMap<String,Object> GX_CONFIGS = new HashMap<>();
    private Configurator(){
        GX_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<String,Object> getGxConfigs(){
        return GX_CONFIGS;
    }

    //静态内部类的单例模式
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        GX_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);//配置完成
    }

    public final Configurator withApiHost(String host){
        GX_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    //检查是否配置完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) GX_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("Configuration is not Ready,call configure");
        }
    }

    final <T> T getConfiguration(Object key){
        checkConfiguration();
       final Object value = GX_CONFIGS.get(key);
       if (value == null){
           throw new NullPointerException(key.toString() + " IS NULL");
       }
       return (T) GX_CONFIGS.get(key);
    }
















}
