package com.hua.gentlemanx2.icon;

import com.joanzapata.iconify.Icon;

public enum IntroduceIcons implements Icon {
    icon_scan('\ue68b'),
    icon_search('\ue60e');

    private char character;

    IntroduceIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
