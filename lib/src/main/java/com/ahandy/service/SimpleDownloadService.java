package com.ahandy.service;

import com.ahandy.R;
import com.ahandy.util.IdUtils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for downloading a file.
 */
public class SimpleDownloadService extends IntentService {

    private static final String LOGTAG = "SimpleDownloadService";

    private static final int MAX_PROGRESS = 100;
    private static final int BUFFER_SIZE = 1024;

    private static final String ACTION_DOWNLOAD_SILENCE
            = "com.ahandy.action.download.silence";
    private static final String ACTION_DOWNLOAD_NOTIFICATION
            = "com.ahandy.action.download.notification";

    private static final String EXTRA_URL = "com.ahandy.extra.url";
    private static final String EXTRA_DESTINATION = "com.ahandy.extra.destination";
    private static final String EXTRA_TITLE = "com.ahandy.extra.title";
    private static final String EXTRA_CONTENT = "com.ahandy.extra.content";
    private static final String EXTRA_ICON = "com.ahandy.extra.icon";
    private static final String EXTRA_ACTION_COMPLETE = "com.ahandy.extra.action.complete";

    /**
     * For getting download directory in broadcast intent.
     */
    public static final String EXTRA_FILE_DIRECTORY = "com.ahandy.extra.file.directory";
    /**
     * For getting download file name in broadcast intent.
     */
    public static final String EXTRA_FILE_NAME = "com.ahandy.extra.file.name";

    /**
     * Starts this service to download a file silently with the given parameters. If the service is
     * already performing a task this action will be queued.
     *
     * @param context     The context uses for starting service.
     * @param url         The download file's link address.
     * @param destination The directory where saves the file.
     * @see IntentService
     */
    public static void startDownloadSilence(Context context, String url, String destination) {
        Intent intent = new Intent(context, SimpleDownloadService.class);
        intent.setAction(ACTION_DOWNLOAD_SILENCE);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_DESTINATION, destination);
        context.startService(intent);
    }

    /**
     * Starts this service to download a file silently with the given parameters. If the service is
     * already performing a task this action will be queued.
     *
     * @param context        The context uses for starting service.
     * @param url            The download file's link address.
     * @param destination    The directory where saves the file.
     * @param actionComplete The action uses for sending broadcast when download completed.
     * @see IntentService
     */
    public static void startDownloadSilence(Context context, String url, String destination,
            String actionComplete) {
        Intent intent = new Intent(context, SimpleDownloadService.class);
        intent.setAction(ACTION_DOWNLOAD_SILENCE);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_DESTINATION, destination);
        intent.putExtra(EXTRA_ACTION_COMPLETE, actionComplete);
        context.startService(intent);
    }

    /**
     * Starts this service to download a file and show a notification with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @param context        The context uses for starting service.
     * @param url            The download file's link address.
     * @param destination    The directory where saves the file.
     * @param title          The notification's title.
     * @param content        The notification's content text.
     * @param iconResId      The notification's icon.
     * @param actionComplete The action uses for sending broadcast when download completed.
     * @see IntentService
     */
    public static void startDownloadNotification(Context context, String url, String destination,
            String title, String content, int iconResId, String actionComplete) {
        Intent intent = new Intent(context, SimpleDownloadService.class);
        intent.setAction(ACTION_DOWNLOAD_NOTIFICATION);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_DESTINATION, destination);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_ICON, iconResId);
        intent.putExtra(EXTRA_ACTION_COMPLETE, actionComplete);
        context.startService(intent);
    }

    public SimpleDownloadService() {
        super("SimpleDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD_SILENCE.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                final String destination = intent.getStringExtra(EXTRA_DESTINATION);
                final String actionComplete = intent.getStringExtra(EXTRA_ACTION_COMPLETE);
                downloadSilence(url, destination, actionComplete);
            } else if (ACTION_DOWNLOAD_NOTIFICATION.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                final String destination = intent.getStringExtra(EXTRA_DESTINATION);
                final String title = intent.getStringExtra(EXTRA_TITLE);
                final String content = intent.getStringExtra(EXTRA_CONTENT);
                final int icon = intent.getIntExtra(EXTRA_ICON, 0);
                final String actionComplete = intent.getStringExtra(EXTRA_ACTION_COMPLETE);
                downloadNotification(url, destination, title, content, icon, actionComplete);
            }
        }
    }

    private void downloadSilence(String url, String destination, String actionComplete) {
        HttpURLConnection urlConnection = null;
        try {
            URL downloadUrl = new URL(url);
            urlConnection = (HttpURLConnection) downloadUrl.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                String filename = getFileNameFromUrl(url);
                File file = new File(destination, filename);

                String contentType = urlConnection.getContentType();
                int contentLength = urlConnection.getContentLength();
                logDownloadInfo(url, destination, filename, contentType, contentLength);

                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bufferLength;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();

                // When download completed
                if (!TextUtils.isEmpty(actionComplete)) {
                    Intent broadcastIntent = new Intent(actionComplete);
                    broadcastIntent.putExtra(EXTRA_FILE_DIRECTORY, destination);
                    broadcastIntent.putExtra(EXTRA_FILE_NAME, filename);
                    sendBroadcast(broadcastIntent);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void downloadNotification(String url, String destination, String title, String content,
            int iconResId, String actionComplete) {
        HttpURLConnection urlConnection = null;
        try {
            int notifyId = IdUtils.getUniqueId("SimpleDownloadNotification");
            NotificationManager notifyManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(this)
                    .setOngoing(true)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(iconResId);
            downloadNotify(notifyManager, builder, notifyId, 0);

            URL downloadUrl = new URL(url);
            urlConnection = (HttpURLConnection) downloadUrl.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                String filename = getFileNameFromUrl(url);
                File file = new File(destination, filename);

                String contentType = urlConnection.getContentType();
                int contentLength = urlConnection.getContentLength();
                logDownloadInfo(url, destination, filename, contentType, contentLength);

                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();

                long bufferTotal = 0;
                int bufferLength;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    bufferTotal += bufferLength;
                    int progress =
                            (int) (bufferTotal / urlConnection.getContentLength() * MAX_PROGRESS);
                    downloadNotify(notifyManager, builder, notifyId, progress);
                    Log.i(LOGTAG, "Download Progress: " + progress);
                }
                fileOutput.close();

                // When download completed
                Intent broadcastIntent = new Intent(actionComplete);
                broadcastIntent.putExtra(EXTRA_FILE_DIRECTORY, destination);
                broadcastIntent.putExtra(EXTRA_FILE_NAME, filename);

                PendingIntent completeIntent = PendingIntent
                        .getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);

                builder.setOngoing(false)
                        .setAutoCancel(true)
                        .setContentIntent(completeIntent)
                        .setContentText(getString(R.string.notification_text_download_complete));
                // Remove progress bar
                downloadNotify(notifyManager, builder, notifyId, 0, 0);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void downloadNotify(NotificationManager notifyManager, Notification.Builder builder,
            int notifyId, int progress) {
        downloadNotify(notifyManager, builder, notifyId, MAX_PROGRESS, progress);
    }

    @SuppressWarnings("deprecation")
    private void downloadNotify(NotificationManager notifyManager, Notification.Builder builder,
            int notifyId, int maxProgress, int progress) {
        builder.setProgress(maxProgress, progress, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notifyManager.notify(notifyId, builder.build());
        } else {
            notifyManager.notify(notifyId, builder.getNotification());
        }
    }

    private String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private void logDownloadInfo(String url, String destination, String filename,
            String contentType, int contentLength) {
        String newLine = "\n";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Download URL: ").append(url).append(newLine)
                .append("Download Directory: ").append(destination).append(newLine)
                .append("File Name: ").append(filename).append(newLine)
                .append("Content Type: ").append(contentType).append(newLine)
                .append("Content Length: ").append(contentLength);
        Log.i(LOGTAG, stringBuilder.toString());
    }
}
