package com.ahandy.receiver;

import com.ahandy.service.SimpleDownloadService;
import com.ahandy.util.PackageUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class SimpleUpdateReceiver extends BroadcastReceiver {

    private static final String LOGTAG = "SimpleUpdateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String dir = intent.getStringExtra(SimpleDownloadService.EXTRA_FILE_DIRECTORY);
        String filename = intent.getStringExtra(SimpleDownloadService.EXTRA_FILE_NAME);
        if (filename.contains(".apk")) {
            Uri uri = Uri.fromFile(new File(dir, filename));
            PackageUtils.installApk(context, uri);
        } else {
            Log.e(LOGTAG, "It is not an apk file.");
        }
    }
}
