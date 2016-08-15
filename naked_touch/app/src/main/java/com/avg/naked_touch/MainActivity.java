package com.avg.naked_touch;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import FrameWork.cGameManager;
import FrameWork.cGameView;
import FrameWork.cMediaManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /** 기본으로 미디어 볼륨이 조절되도록 설정. */
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        /** 기기의 DPI 를 구하기 위해 Display Metrics Metrics 를 구하는 부분 */
        Resources           resources = this.getResources();
        DisplayMetrics      metrics = resources.getDisplayMetrics();
        cGameManager.getInstance().setDisplayMetrics(metrics);

        /** Screen On/Off 이벤트 */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        cGameManager.getInstance().setActivity(this);
        setContentView(new cGameView(this));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        cMediaManager.getInstance().autoPause();
        cGameManager.getInstance().pause(true);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        cMediaManager.getInstance().autoResume();
        cGameManager.getInstance().pause(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.BT_START:
                Toast.makeText(this, "on click BT_START", Toast.LENGTH_SHORT).show();
                break;

            case R.id.BT_EXIT:
                Toast.makeText(this, "on click BT_EXIT", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        cGameManager.getInstance().getGameView().onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        cGameManager.getInstance().getGameView().onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        cGameManager.getInstance().getGameView().onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}