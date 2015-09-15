package com.fiftyfive.cargo;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.Container;

import java.util.Map;

/**
 * Created by louis on 15/09/15.
 */

public class UserGoogleIdMacroHandler implements Container.FunctionCallMacroCallback {


    @Override
    public Object getValue(String name, Map<String, Object> parameters) {

        if ("userGoogleId".equals(name)) {
            Log.i(Constants.TAG, "Start to retrieve the client_id");

            Context c = ContainerHolderSingleton.getContext();

            if(c == null){
                Log.e(Constants.TAG, "You must provide the applicationContext to ContainerHolderSingleton");
                return null;
            }

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(c);

            // On Android we cannot retrieve a tracker based on his id
            // so we create a new tracker
            // note : all trackers share the same client_id on android
            Tracker tracker = analytics.newTracker("UA-1111-1");

            // the following line blocks the thread

            // HERE IS THE PROBLEM
            // THIS LINE BLOCKS THE THREAD
            String client_id = "not_set";

            try {
                client_id = tracker.get("&cid");
            }catch(Exception e){
                Log.e(Constants.TAG,"Error is fired "+ e.getMessage());
            }

            // APP IS FROZEN


            Log.i(Constants.TAG,"This line will never be logged");

            return client_id;
        } else {
            throw new IllegalArgumentException("Custom macro name: " + name
                    + " is not supported.");
        }
    }
}
