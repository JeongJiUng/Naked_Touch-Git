package com.avg.naked_touch.Popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * Created by AVG on 2016-04-30.
 */
public class cGameFailPopup extends Activity
{
    private Activity        act;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.7f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_game_fail_popup);

        getWindow().getAttributes().width   = (int)(cGameManager.getInstance().getDisplayMetrics().widthPixels * 1);
        getWindow().getAttributes().height  = (int)(cGameManager.getInstance().getDisplayMetrics().heightPixels * 0.4);

        act                 = this;
        act.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton         restartBut = (ImageButton)findViewById(R.id.imageButton_fail_restart);
        ImageButton         exitBut = (ImageButton)findViewById(R.id.imageButton_fail_exit);
        restartBut.setOnClickListener(butListener);
        exitBut.setOnClickListener(butListener);


        super.onCreate(savedInstanceState);
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

    ImageButton.OnClickListener butListener = new ImageButton.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.imageButton_fail_restart:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    act.finish();

                    cStageManager.getInstance().start();
                    break;

                case R.id.imageButton_fail_exit:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    act.finish();

                    cLoader.getInstance().loadMainSet();
                    cGameManager.getInstance().getGameView().changeGameState(new cPlay());
                    cMediaManager.getInstance().play(MEDIA_LIST.MAIN.getMedia());
                    break;
            }
        }
    };
}
