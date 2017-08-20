package org.viktorot.izicheck;

import android.app.Application;
import android.content.Context;

import io.reactivex.annotations.NonNull;

public class IziApplication extends Application {

    private Storage storage;

    public static IziApplication get(@NonNull Context context) {
        return (IziApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        storage = new Storage(getApplicationContext());
    }

    public Storage getStorage() {
        return this.storage;
    }

}
