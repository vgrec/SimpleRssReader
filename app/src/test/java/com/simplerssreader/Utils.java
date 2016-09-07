package com.simplerssreader;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class Utils {
    public static void setupRxJava() {
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

    }

    public static void resetRxJava() {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }
}
