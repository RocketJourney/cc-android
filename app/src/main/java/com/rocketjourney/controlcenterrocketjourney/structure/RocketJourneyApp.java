package com.rocketjourney.controlcenterrocketjourney.structure;

import android.app.Application;
import android.content.Context;

import io.branch.referral.Branch;

public class RocketJourneyApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // Initialize the Branch object
        Branch.getAutoInstance(this);
    }
}
