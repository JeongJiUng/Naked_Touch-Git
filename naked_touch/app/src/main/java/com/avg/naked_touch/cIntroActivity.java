package com.avg.naked_touch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * Created by AVG on 2015-09-09.
 */
public class cIntroActivity extends Activity
{
    Handler                 mHandler;
    Runnable                mRun = new Runnable()
    {
        public void run()
        {
            Intent          i = new Intent(cIntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();

            /** fad in 으로 시작하여 fade out 으로 인트로 화면이 꺼지게 애니메이션 추가 */
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    public void onCreate(Bundle _saveInstanceState)
    {
        super.onCreate(_saveInstanceState);
        setContentView(R.layout.activity_intro);

        mHandler            = new Handler();
        mHandler.postDelayed(mRun, 1000);
    }


    /** 인트로 화면 중간에 뒤로가기 버튼을 눌러서 꺼졌을 시 2초 후 메인 페이지가 뜨는 것을 방지. */
    public void onBackPressed()
    {
        super.onBackPressed();
        mHandler.removeCallbacks(mRun);
    }
}
