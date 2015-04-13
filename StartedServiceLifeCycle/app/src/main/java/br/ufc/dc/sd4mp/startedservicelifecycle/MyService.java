package br.ufc.dc.sd4mp.startedservicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service implements Runnable {

    private static final String LOG_CATEGORY = "MyServiceLogCat";

    private boolean running = false;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_CATEGORY, getClassName() + "onCreate");
    }

    @Override
    public  void onDestroy() {
        running = false;
        super.onDestroy();
        Log.i(LOG_CATEGORY, getClassName() + "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_CATEGORY, getClassName() + "onStartCommand");
        running = true;
        new Thread(this).start();
        return START_NOT_STICKY;
    }

    private String getClassName() {
        return MyService.class.getName();
    }

    public void run() {
        int i = 0;

        while (running && i<10) {
            String message = "MyService is running!";
            Log.i("(" + i++ + ") MyService", message);

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {

            }
        }

        running = false;
    }
}
