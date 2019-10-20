package com.hua.gentlemanx2.delegate;

public abstract class GxDelegate extends PermissionCheckerDelegate{
    @SuppressWarnings("unchecked")
    public <T extends GxDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
