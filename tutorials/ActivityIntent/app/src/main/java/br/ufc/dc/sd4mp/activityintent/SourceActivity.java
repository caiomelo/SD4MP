package br.ufc.dc.sd4mp.activityintent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class SourceActivity extends Activity {

    private static final String CATEGORIA = "CicloVida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);
        Log.i(CATEGORIA, getClassName() + ".onCreate()-->Running");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CATEGORIA, getClassName() + ".onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(CATEGORIA, getClassName() + ".onPause()-->Paused");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(CATEGORIA, getClassName() + ".onRestart()-->Running");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CATEGORIA, getClassName() + ".onResume()-->Running");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(CATEGORIA, getClassName() + ".onStart()-->Running");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(CATEGORIA, getClassName() + ".onStop()-->Stopped");
    }

    private String getClassName() {
        return SourceActivity.class.getName();
    }

    public void startActivityExplicita(View view) {
        Intent intent = new Intent(SourceActivity.this, TargetActivity.class);
        startActivity(intent);
    }

    public void startActivityImplicita(View view) {
        Intent intent = new Intent();
        intent.setAction("br.ufc.dc.sd4mp.acdon.ACAO");
        intent.addCategory("br.ufc.dc.sd4mp.category.CATEGORIA");
        startActivity(intent);
    }

}
