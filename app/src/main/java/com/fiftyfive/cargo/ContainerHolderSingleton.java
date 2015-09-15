package com.fiftyfive.cargo;

import android.content.Context;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Singleton to hold the GTM Container (since it should be only created once
 * per run of the app).
 */
public class ContainerHolderSingleton {
    private static ContainerHolder containerHolder;
    private static Context context;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {
    }

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }

    public static void setContext(Context c ){
        context = c;
    }

    public static Context getContext(){
        return context;
    }
}
