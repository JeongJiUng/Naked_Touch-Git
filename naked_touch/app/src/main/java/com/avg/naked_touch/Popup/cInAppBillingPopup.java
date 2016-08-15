package com.avg.naked_touch.Popup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avg.naked_touch.R;

import FrameWork.cGameManager;
import FrameWork.cInAppBillingHelper;
import FrameWork.cSoundManager;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-05-11.
 */
public class cInAppBillingPopup extends Activity
{
    private int             mItem;

    private Activity        act;
    private cInAppBillingHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.7f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_in_app_billing_popup);

        getWindow().getAttributes().width   = (int)(cGameManager.getInstance().getDisplayMetrics().widthPixels * 1);
        getWindow().getAttributes().height  = (int)(cGameManager.getInstance().getDisplayMetrics().heightPixels * 0.6);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(255)));

        act                 = this;

        // 선택된 아이템의 ID를 Intent로 전달 받음.
        mItem               = Integer.valueOf(getIntent().getStringExtra("SELECT_ITEM"));

        ImageButton         buyCash = (ImageButton)findViewById(R.id.imageButton_item_buy_cash);
        ImageButton         buyBut = (ImageButton)findViewById(R.id.imageButton_item_buy);
        ImageButton         cancelBut = (ImageButton)findViewById(R.id.imageButton_item_cancel);
        buyCash.setOnClickListener(clickListener);
        buyBut.setOnClickListener(clickListener);
        cancelBut.setOnClickListener(clickListener);

        // TODO:: 선택된 아이템에 따른 imageView_select_item 이미지와 가격 설정.
        ImageView           selectItem = (ImageView)findViewById(R.id.imageView_select_item);
        TextView            cash = (TextView)findViewById(R.id.textView_cash);
        TextView            passion = (TextView)findViewById(R.id.textView_passion);
        switch (mItem)
        {
            case 0:
                selectItem.setImageResource(R.drawable.thumb_nail_power_up);
                cash.setText("3000원");
                passion.setText("X 300");
                break;

            case 1:
                selectItem.setImageResource(R.drawable.thumb_nail_ticket_10);
                cash.setText("1000원");
                passion.setText("X 100");
                break;

            case 2:
                selectItem.setImageResource(R.drawable.thumb_nail_ticket_50);
                cash.setText("4500원");
                passion.setText("X 450");
                break;

            case 3:
                selectItem.setImageResource(R.drawable.thumb_nail_ticket_100);
                cash.setText("9000원");
                passion.setText("X 900");
                break;
        }

        // 인앱 헬퍼 클래스 인스턴스 생성.
        mHelper             = new cInAppBillingHelper(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjxWrRZK1nCYIHW9IPMRIqB0qc7UiL7ZnpwrUAj/GGqWQnhXyG03K2LslzeNZX/WYKYNLRepEv61uzrPLnKrf4DxCwDIx58iBgvPIeC+BOD/QDeF1VTTPGYrwNrLKMwYqd684HHBVxXw/Nfhc45jiHJbICkyirbJjHeAhwh20mlLCKRuCfJRtNt7bYK9Dah4xcBmYYGzEmLQZPhsGxiXtbDz2N3ukGTW005aOtvt78DsQJgdWF9nsbMpkE+awXBQAHJizqGAD1v7sy691OCoXFEzFuVlDbVGtktJIXy4Dh6EwdQjJzzCV7V1DtLDRl97blmxWSfMvY0EVgaR/hdQJEwIDAQAB")
        {
            @Override
            public void addInventory()
            {
                cUserData.getInstance().getTicketInstance().addCount(10);
                /*
                switch (mItem)
                {
                    case 0:
                        cUserData.getInstance().getTicketInstance().addCount(10);
                        break;
                }*/
                act.finish();
            }
        };
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHelper.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mHelper.activityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void buyItem_cash()
    {
        // TODO:: 현금으로 구매한 아이템.
        switch (mItem)
        {
            case 0:
                mHelper.buy("android.test.purchased");
                //mHelper.buy("test_01");
                break;
        }
    }

    private void buyItem_passion()
    {
        // TODO:: 열정으로 구매한 아이템.
        switch (mItem)
        {
            case 0: // 공격력
                if (check(300))
                {
                    cUserData.getInstance().getPassionInstance().subCount(300);
                    cUserData.getInstance().powerUp(1);
                    cLoader.getInstance().mPower.setString("공격력 < "+cUserData.getInstance().getPower()+" >");
                }
                break;

            case 1: // 티켓 10
                if (check(100))
                {
                    cUserData.getInstance().getPassionInstance().subCount(100);
                    cUserData.getInstance().getTicketInstance().addCount(10);
                }
                break;

            case 2: // 티켓 50
                if (check(450))
                {
                    cUserData.getInstance().getPassionInstance().subCount(450);
                    cUserData.getInstance().getTicketInstance().addCount(50);
                }
                break;

            case 3: // 티켓 100
                if (check(900))
                {
                    cUserData.getInstance().getPassionInstance().subCount(900);
                    cUserData.getInstance().getTicketInstance().addCount(100);
                }
                break;
        }
    }

    private boolean check(int _amount)
    {
        if (cUserData.getInstance().getPassionInstance().getPassionCount() - _amount < 0)
        {
            Toast.makeText(cInAppBillingPopup.this, "구매에 실패했습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(cInAppBillingPopup.this, "구매에 성공했습니다.", Toast.LENGTH_SHORT).show();
        return true;
    }

    ImageButton.OnClickListener clickListener   = new ImageButton.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.imageButton_item_buy_cash:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    buyItem_cash();
                    break;

                case R.id.imageButton_item_buy:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    buyItem_passion();
                    act.finish();
                    break;

                case R.id.imageButton_item_cancel:
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    act.finish();
                    break;
            }
        }
    };
}
