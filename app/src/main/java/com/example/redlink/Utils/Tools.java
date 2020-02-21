package com.example.redlink.Utils;

import android.content.Context;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

public class Tools {

    /**
     * For API version with no TLS (api 19)
     * */
    public static void checkTls(Context ctx){

        try {
            ProviderInstaller.installIfNeeded(ctx);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
