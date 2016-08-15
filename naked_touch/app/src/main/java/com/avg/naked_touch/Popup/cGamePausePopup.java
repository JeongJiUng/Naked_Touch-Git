package com.avg.naked_touch.Popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.avg.naked_touch.R;

import FrameWork.cGameManager;
import FrameWork.cMediaManager;
import FrameWork.cSoundManager;
import GameSystem.MEDIA_LIST;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameSystem.cStageManager;
import GameView.cPlay;

/**
 * Created by AVG on 2016-04-07.
 */
public class cGamePausePopup extends Activity
{
    private Activity        act;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.7f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_game_pause_popup);

        getWindow().getAttributes().width   = (int)(cGameManager.getInstance().getDisplayMetrics().widthPixels * 0.4);
        getWindow().getAttributes().height  = (int)(cGameManager.getInstance().getDisplayMetrics().heightPixels * 0.4);

        act                 = this;

        ImageButton         resumeBut = (ImageButton)findViewById(R.id.imageButton_game_resume);
        ImageButton         restartBut = (ImageButton)findViewById(R.id.imageButton_game_restart);
        ImageButton         exitBut = (ImageButton)findViewById(R.id.imageButton_game_eixt);
        resumeBut.setOnClickListener(imgButClickListener);
        restartBut.setOnClickListener(imgButClickListener);
        exitBut.setOnClickListener(imgButClickListener);

        cGameManager.getInstance().pause(true);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return true;
    }

    ImageButton.OnClickListener imgButClickListener = new ImageButton.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.imageButton_game_resume:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    cGameManager.getInstance().pause(false);
                    act.finish();
                    break;

                case R.id.imageButton_game_restart:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    cGameManager.getInstance().pause(false);
                    act.finish();

                    cStageManager.getInstance().start();
                    break;

                case R.id.imageButton_game_eixt:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    cGameManager.getInstance().pause(false);
                    //TODO:: 게임 플레이에 사용된 동적 메모리 해제 및 cMain, cPlay 에 필요한 동적 메모리 할당.
                    act.finish();
                    cLoader.getInstance().loadMainSet();
                    cGameManager.getInstance().getGameView().changeGameState(new cPlay());
                    cMediaManager.getInstance().play(MEDIA_LIST.MAIN.getMedia());
                    break;
            }
        }
    };
}
