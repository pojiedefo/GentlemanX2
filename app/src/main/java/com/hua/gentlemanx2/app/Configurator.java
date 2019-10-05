package com.hua.gentlemanx2.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class Configurator {

    private static final HashMap<Object,Object> GX_CONFIGS = new HashMap<>();

    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();


    private Configurator(){
        GX_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getGxConfigs(){
        return GX_CONFIGS;
    }

    //静态内部类的单例模式
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        initIcons();
        GX_CONFIGS.put(ConfigKeys.CONFIG_READY,true);//配置完成
    }

    private void initIcons() {
        if (ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            final int size = ICONS.size();
            for (int i = 1; i < size; i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withApiHost(String host){
        GX_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    //检查是否配置完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) GX_CONFIGS.get(ConfigKeys.CONFIG_READY);
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
