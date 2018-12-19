package com.rocketjourney.controlcenterrocketjourney.structure;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.branch.referral.Branch;
import io.fabric.sdk.android.Fabric;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class RJControlCenter extends Application {

    public static final String ROCKETJOURNEYCC_REALM = "rocketjourneycc.realm";
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();

        Realm.init(this);
        initRealmConfig();

        // Initialize the Branch object
        Branch.enableLogging();
        Branch.getAutoInstance(this);
    }

    private void initRealmConfig() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(ROCKETJOURNEYCC_REALM)
                .schemaVersion(1)
                .migration(getRealmMigration())
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private RealmMigration getRealmMigration() {
        RealmMigration realmMigration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            }
        };

        return realmMigration;
    }
}
