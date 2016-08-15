package com.avg.naked_touch.Popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.avg.naked_touch.R;

import FrameWork.cGameManager;
import FrameWork.cSoundManager;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameSystem.cStageManager;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-03-04.
 */
public class cGameStartPopup extends Activity
{
    final int               NORMAL = 3;
    final int               HARD = 6;
    final int               HELL = 9;

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

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(255)));

        setContentView(R.layout.activity_game_start_popup);

        getWindow().getAttributes().width   = (int)(cGameManager.getInstance().getDisplayMetrics().widthPixels * 0.9);
        getWindow().getAttributes().height  = (int)(cGameManager.getInstance().getDisplayMetrics().heightPixels * 0.4);

        act                 = this;

        ImageButton startBut = (ImageButton)findViewById(R.id.imageButton_game_start);
        startBut.setOnClickListener(butClickListner);
        /**
         * 선택된 캐릭터에 따라 팝업 섬네일 설정.*/
        ImageView           thumbnail = (ImageView)findViewById(R.id.img_thumbnail);
        switch(cUserData.getInstance().getSelectCharNum())
        {
            case 0:
                thumbnail.setImageResource(R.drawable.thumb_nail_1);
                break;

            case 1:
                thumbnail.setImageResource(R.drawable.thumb_nail_2);
                break;

            case 2:
                thumbnail.setImageResource(R.drawable.thumb_nail_3);
                break;
        }

        /**
         * 사용자가 마지막으로 선택한 난이도로 설정.*/
        RadioButton         normal = (RadioButton)findViewById(R.id.radioButton_normal);
        RadioButton         hard = (RadioButton)findViewById(R.id.radioButton_hard);
        RadioButton         hell = (RadioButton)findViewById(R.id.radioButton_hell);
        TextView            ticketCount = (TextView)findViewById(R.id.string_ticket_count);

        normal.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
        hard.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
        hell.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));
        ticketCount.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RIX_STAR_N_ME.TTF"));

        normal.setOnClickListener(clickListner);
        hard.setOnClickListener(clickListner);
        hell.setOnClickListener(clickListner);

        switch (cUserData.getInstance().getLevel())
        {
            case 0:
                ticketCount.setText("X 3");
                normal.setChecked(true);
                cStageManager.getInstance().setDecrement(NORMAL);
                break;

            case 1:
                ticketCount.setText("X 6");
                hard.setChecked(true);
                cStageManager.getInstance().setDecrement(HARD);
                break;

            case 2:
                ticketCount.setText("X 9");
                hell.setChecked(true);
                cStageManager.getInstance().setDecrement(HELL);
                break;
        }
    }

    RadioButton.OnClickListener clickListner = new RadioButton.OnClickListener() {
        public void onClick(View v)
        {
            /**
             * 난이도에 따른 티켓 수량 설정.*/
            TextView    ticketCount = (TextView)findViewById(R.id.string_ticket_count);
            switch (v.getId())
            {
                case R.id.radioButton_normal:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    ticketCount.setText("X 3");
                    cStageManager.getInstance().setDecrement(NORMAL);
                    cUserData.getInstance().setLevel(0);
                    break;

                case R.id.radioButton_hard:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    ticketCount.setText("X 6");
                    cStageManager.getInstance().setDecrement(HARD);
                    cUserData.getInstance().setLevel(1);
                    break;

                case R.id.radioButton_hell:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    ticketCount.setText("X 9");
                    cStageManager.getInstance().setDecrement(HELL);
                    cUserData.getInstance().setLevel(2);
                    break;
            }
        }
    };

    ImageButton.OnClickListener butClickListner = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.imageButton_game_start:
                    cSoundManager.getInstance().play(SOUND_LIST.START_BUTTON_CLICK.getSound(), 0);
                    if (cStageManager.getInstance().start())
                        cLoader.getInstance().recycleMainSet();
                    act.finish();
                    break;
            }
        }
    };
}