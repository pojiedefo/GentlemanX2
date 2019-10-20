package com.hua.gentlemanx2.ui.banner;

import android.media.Image;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

public class HolderCreator  implements CBViewHolderCreator<ImageHolder> {
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
