package com.avg.naked_touch.Popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avg.naked_touch.R;

import FrameWork.cGameManager;
import FrameWork.cMediaManager;
import FrameWork.cSoundManager;
import GameSystem.MEDIA_LIST;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameView.cPlay;

/**
 * Created by AVG on 2016-04-29.
 */
public class cGameClearPopup extends Activity
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

        setContentView(R.layout.activity_game_clear_popup);

        getWindow().getAttributes().width   = (int)(cGameManager.getInstance().getDisplayMetrics().widthPixels * 1);
        getWindow().getAttributes().height  = (int)(cGameManager.getInstance().getDisplayMetrics().heightPixels * 0.6);

        act                 = this;
        act.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton         exitBut = (ImageButton)findViewById(R.id.imageButton_exit);
        exitBut.setOnClickListener(imgButClickListener);

        TextView            passion = (TextView)findViewById(R.id.textView_get_passion);
        TextView            time = (TextView)findViewById(R.id.textView_clear_time);
        passion.setText("X "+getIntent().getStringExtra("CLEAR_PASSION"));
        time.setText(getIntent().getStringExtra("CLEAR_TIME"));

        ((TextView)findViewById(R.id.textView2)).setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
        passion.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
        time.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));

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

    ImageButton.OnClickListener imgButClickListener = new ImageButton.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.imageButton_exit:
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
