package com.fiftyfive.cargo;

import android.util.Log;

import com.google.android.gms.tagmanager.Container;

import java.util.Map;

/**
 * Created by louis on 15/09/15.
 */
public class DummyFunctionTagHandler implements Container.FunctionCallTagCallback {

    /*
    Just a dummy FunctionCallTagHandler
     */

    @Override
    public void execute(String s, Map<String, Object> stringObjectMap) {
        Log.i(Constants.TAG, "Function " + s + " has been called with " + stringObjectMap.toString());

        //Here we have so code to initialize Mobile App Tracking from Tune
    }

}
