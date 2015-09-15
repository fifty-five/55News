package com.fiftyfive.news;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;


import com.fiftyfive.cargo.ContainerHolderSingleton;
import com.fiftyfive.cargo.DummyFunctionTagHandler;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import com.fiftyfive.cargo.UserGoogleIdMacroHandler;

public class SplashScreenActivity extends Activity {
    //Define your GTM account number
    private static final String CONTAINER_ID = "GTM-5KX4SM";
    @SuppressWarnings("unused")
    private static final String GOOGLE_PROJECT_ID = "219762725716";

    private static Context applicationContext;

    private static final String TAG = "55News";
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
        
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//remove Title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);


		//Launch Google Tag Manager
		final TagManager tagManager = TagManager.getInstance(this);
        //Store app Context in Container Holder Singleton
        ContainerHolderSingleton.setContext(this.getApplicationContext());

		// Modify the log level of the logger to print out not only
		// warning and error messages, but also verbose, debug, info messages.
		tagManager.setVerboseLoggingEnabled(true);


        // GTM standard integration
        PendingResult<ContainerHolder> pending =
                tagManager.loadContainerPreferNonDefault(CONTAINER_ID,
                        R.raw.gtm_default_container);

        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {

                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("fifty-five", "failure loading container");
                    return;
                }

                // Save the containerHolder
                ContainerHolderSingleton.setContainerHolder(containerHolder);

                // Add a listener to register a macroTagHandler
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());

                Intent intent = getIntent();
                if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                    Uri data = intent.getData();
                    String host = data.getHost();
                    System.out.println("host == " + host);
                }


                startMainActivity();
            }
        }, 2, TimeUnit.SECONDS);
	}


	@Override 
	public void onResume() {
		super.onResume();

    }


    @Override
	public void onPause()
	{
		super.onPause();
	}

	private void startMainActivity() {
		Intent intent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
		startActivity(intent);
	}




    private static class ContainerLoadedCallback implements
            ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder,
                                         String containerVersion) {

            Log.i(TAG, "Container is available");
            // Store the container in the containerHolder
            Container container = containerHolder.getContainer();

            // Register Custom Macro and Tag
            Log.i(TAG, "Registering macro userGoogleId and tag Tune_init");
            container.registerFunctionCallMacroCallback("userGoogleId",
                    new UserGoogleIdMacroHandler());
            container.registerFunctionCallTagCallback( "Tune_init", new DummyFunctionTagHandler());

            //Push an event applicationStart
            DataLayer dataLayer = TagManager.getInstance(ContainerHolderSingleton.getContext()).getDataLayer();
            dataLayer.push(DataLayer.mapOf("event", "applicationStart"));

        }
    }


}
