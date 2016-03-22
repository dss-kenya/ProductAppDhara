package com.agro.star.dhara.productapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CustomExceptionHandler implements UncaughtExceptionHandler {
    public static String sendErrorLogsTo = "sdhara2@hotmail.com";
    Activity activity;
    private UncaughtExceptionHandler defaultUEH;

    public CustomExceptionHandler(Activity activity) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.activity = activity;
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public void uncaughtException(Thread t, Throwable e) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        String filename = "error" + System.nanoTime() + ".stacktrace";

        Log.e("Hi", "url != null");
        sendToServer(stacktrace, filename);

        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString() + "\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }
        report += "-------------------------------\n\n";

        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }
        report += "-------------------------------\n\n";

        defaultUEH.uncaughtException(t, e);
    }

    private void sendToServer(String stacktrace, String filename) {
        AsyncTaskClass async = new AsyncTaskClass(stacktrace, filename,
                getAppLable(activity));
        async.execute("");
    }

    public String getAppLable(Context pContext) {
        PackageManager lPackageManager = pContext.getPackageManager();
        ApplicationInfo lApplicationInfo = null;
        try {
            lApplicationInfo = lPackageManager.getApplicationInfo(
                    pContext.getApplicationInfo().packageName, 0);
        } catch (final NameNotFoundException e) {
        }
        return (String) (lApplicationInfo != null ? lPackageManager
                .getApplicationLabel(lApplicationInfo) : "Unknown");
    }

    public class AsyncTaskClass extends AsyncTask<String, String, Void> {
        final String filename;
        InputStream is = null;
        String stacktrace;
        String applicationName;

        AsyncTaskClass(final String stacktrace, final String filename,
                       String applicationName) {
            this.applicationName = applicationName;
            this.stacktrace = stacktrace;
            this.filename = filename;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String strUrl = "http://suo-yang.com/books/sendErrorLog/sendErrorLogs.php?";
                URL url = new URL(strUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(20000);
                connection.setConnectTimeout(20000);
                connection.setRequestMethod("POST");

                HashMap<String, String> map = new HashMap<>();
                map.put("data", stacktrace);
                map.put("to", sendErrorLogsTo);
                map.put("subject", applicationName);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(getQuery(map));
                writer.flush();
                writer.close();
                outputStream.close();

                InputStream is = connection.getInputStream();
                Log.e("Stream Data", getStringFromInputStream(is));
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        private String getQuery(HashMap<String,String> map) throws UnsupportedEncodingException{
            boolean first = true;
            StringBuilder stringBuilder = new StringBuilder();
            for(Map.Entry<String,String> entry : map.entrySet()) {
                if(first){
                    first= false;
                }else {
                    stringBuilder.append("&");
                }
                stringBuilder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                stringBuilder.append("=");
            }
            return stringBuilder.toString();
        }
    }

}