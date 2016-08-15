package com.avg.naked_touch.Popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.avg.naked_touch.R;

import FrameWork.cGameManager;
import GameSystem.cLoader;
import GameSystem.cTouchAction;
import GameView.cGallery;

/**
 * Created by AVG on 2016-05-13.
 */
public class cGalleryPopup extends Activity
{
    CheckBox                mCheckTop;
    CheckBox                mCheckBot;
    CheckBox                mCheckPants;
    CheckBox                mCheckBra;

    ImageView               mView;
    ImageView               mPanty;
    ImageView               mBra;
    ImageView               mBot;
    ImageView               mTop;

    cTouchAction            mTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.7f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_gallery_popup);

        getWindow().getAttributes().width   = (cGameManager.getInstance().getDisplayMetrics().widthPixels * 1);
        getWindow().getAttributes().height  = (cGameManager.getInstance().getDisplayMetrics().heightPixels * 1);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mView               = (ImageView)findViewById(R.id.imageView_gallery);
        mPanty              = (ImageView)findViewById(R.id.imageView_panty);
        mBra                = (ImageView)findViewById(R.id.imageView_bra);
        mBot                = (ImageView)findViewById(R.id.imageView_bot);
        mTop                = (ImageView)findViewById(R.id.imageView_top);

        setCheckBox();
        gallery();
    }

    @Override
    protected void onDestroy()
    {
        if (((BitmapDrawable)mView.getBackground()).getBitmap() != null)
            ((BitmapDrawable)mView.getBackground()).getBitmap().recycle();
        if (((BitmapDrawable)mPanty.getBackground()).getBitmap() != null)
            ((BitmapDrawable)mPanty.getBackground()).getBitmap().recycle();
        if (((BitmapDrawable)mBra.getBackground()).getBitmap() != null)
            ((BitmapDrawable)mBra.getBackground()).getBitmap().recycle();
        if (((BitmapDrawable)mBot.getBackground()).getBitmap() != null)
            ((BitmapDrawable)mBot.getBackground()).getBitmap().recycle();
        if (((BitmapDrawable)mTop.getBackground()).getBitmap() != null)
            ((BitmapDrawable)mTop.getBackground()).getBitmap().recycle();

        mView               = null;
        mPanty              = null;
        mBra                = null;
        mBot                = null;
        mTop                = null;

        mCheckPants.destroyDrawingCache();
        mCheckBra.destroyDrawingCache();
        mCheckTop.destroyDrawingCache();
        mCheckBot.destroyDrawingCache();

        mTemp.recycle();

        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        cLoader.getInstance().loadMainSet();
        ((cGallery)cGameManager.getInstance().getGameView().getGameState()).mEnable = false;
        super.onBackPressed();
    }

    private void gallery()
    {
        int                 select = Integer.valueOf(getIntent().getStringExtra("SELECT_GALLERY"));
        switch (select)
        {
            case 0:
                mTemp       = new cTouchAction(cGameManager.getInstance().getBitmap(R.drawable.char01_nude_normal), 4);
                mTemp.setPartsLocation(0, "팬티", cGameManager.getInstance().getBitmap(R.drawable.char01_pants_normal), 211, 660, 10);
                mTemp.setPartsLocation(1, "브레지어", cGameManager.getInstance().getBitmap(R.drawable.char01_bra_normal), 109, 316, 10);
                mTemp.setPartsLocation(2, "하의", cGameManager.getInstance().getBitmap(R.drawable.char01_bot_normal), 214, 653, 10);
                mTemp.setPartsLocation(3, "상의", cGameManager.getInstance().getBitmap(R.drawable.char01_top_normal), 103, 339, 10);

                mView.setBackgroundResource(R.drawable.char01_nude_normal);
                mPanty.setBackgroundResource(R.drawable.char01_pants_normal);
                mBra.setBackgroundResource(R.drawable.char01_bra_normal);
                mBot.setBackgroundResource(R.drawable.char01_bot_normal);
                mTop.setBackgroundResource(R.drawable.char01_top_normal);

                setDetail();
                break;

            case 1:
                mTemp       = new cTouchAction(cGameManager.getInstance().getBitmap(R.drawable.char01_nude_hard), 4);
                mTemp.setPartsLocation(0, "팬티", cGameManager.getInstance().getBitmap(R.drawable.char01_pants_hard), 145, 676, 10);
                mTemp.setPartsLocation(1, "브레지어", cGameManager.getInstance().getBitmap(R.drawable.char01_bra_hard), 201, 339, 10);
                mTemp.setPartsLocation(2, "하의", cGameManager.getInstance().getBitmap(R.drawable.char01_bot_hard), 140, 677, 10);
                mTemp.setPartsLocation(3, "상의", cGameManager.getInstance().getBitmap(R.drawable.char01_top_hard), 47, 324, 10);

                mView.setBackgroundResource(R.drawable.char01_nude_hard);
                mPanty.setBackgroundResource(R.drawable.char01_pants_hard);
                mBra.setBackgroundResource(R.drawable.char01_bra_hard);
                mBot.setBackgroundResource(R.drawable.char01_bot_hard);
                mTop.setBackgroundResource(R.drawable.char01_top_hard);

                setDetail();
                break;

            case 2:
                mTemp       = new cTouchAction(cGameManager.getInstance().getBitmap(R.drawable.char01_nude_hell), 4);
                mTemp.setPartsLocation(0, "팬티", cGameManager.getInstance().getBitmap(R.drawable.char01_pants_hell), 266, 724, 10);
                mTemp.setPartsLocation(1, "브레지어", cGameManager.getInstance().getBitmap(R.drawable.char01_bra_hell), 192, 444, 10);
                mTemp.setPartsLocation(2, "하의", cGameManager.getInstance().getBitmap(R.drawable.char01_bot_hell), 215, 356, 10);
                mTemp.setPartsLocation(3, "상의", cGameManager.getInstance().getBitmap(R.drawable.char01_top_hell), 191, 420, 10);

                mView.setBackgroundResource(R.drawable.char01_nude_hell);
                mPanty.setBackgroundResource(R.drawable.char01_pants_hell);
                mBra.setBackgroundResource(R.drawable.char01_bra_hell);
                mBot.setBackgroundResource(R.drawable.char01_bot_hell);
                mTop.setBackgroundResource(R.drawable.char01_top_hell);

                setDetail();
                break;
        }
    }

    void setDetail()
    {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(mTemp.getPartsPoint(0).x, mTemp.getPartsPoint(0).y, 0, 0);
        mPanty.setLayoutParams(lp);
        mPanty.getLayoutParams().width  = mTemp.getPartsWidth(0);
        mPanty.getLayoutParams().height = mTemp.getPartsHeight(0);

        lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(mTemp.getPartsPoint(1).x, mTemp.getPartsPoint(1).y, 0, 0);
        mBra.setLayoutParams(lp);
        mBra.getLayoutParams().width  = mTemp.getPartsWidth(1);
        mBra.getLayoutParams().height = mTemp.getPartsHeight(1);

        lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(mTemp.getPartsPoint(2).x, mTemp.getPartsPoint(2).y, 0, 0);
        mBot.setLayoutParams(lp);
        mBot.getLayoutParams().width  = mTemp.getPartsWidth(2);
        mBot.getLayoutParams().height = mTemp.getPartsHeight(2);

        lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(mTemp.getPartsPoint(3).x, mTemp.getPartsPoint(3).y, 0, 0);
        mTop.setLayoutParams(lp);
        mTop.getLayoutParams().width  = mTemp.getPartsWidth(3);
        mTop.getLayoutParams().height = mTemp.getPartsHeight(3);

        mTemp.recycle();
    }

    void setCheckBox()
    {
        mCheckTop           = (CheckBox)findViewById(R.id.checkBox_top);
        mCheckBot           = (CheckBox)findViewById(R.id.checkBox_bot);
        mCheckBra           = (CheckBox)findViewById(R.id.checkBox_bra);
        mCheckPants         = (CheckBox)findViewById(R.id.checkBox_pants);

        mCheckTop.setOnClickListener(listener);
        mCheckBot.setOnClickListener(listener);
        mCheckBra.setOnClickListener(listener);
        mCheckPants.setOnClickListener(listener);
    }

    CheckBox.OnClickListener listener = new CheckBox.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.checkBox_top:
                    if (mCheckTop.isChecked())
                        mTop.setVisibility(View.VISIBLE);
                    else
                        mTop.setVisibility(View.INVISIBLE);
                    break;

                case R.id.checkBox_bot:
                    if (mCheckBot.isChecked())
                        mBot.setVisibility(View.VISIBLE);
                    else
                        mBot.setVisibility(View.INVISIBLE);
                    break;

                case R.id.checkBox_bra:
                    if (mCheckBra.isChecked())
                        mBra.setVisibility(View.VISIBLE);
                    else
                        mBra.setVisibility(View.INVISIBLE);
                    break;

                case R.id.checkBox_pants:
                    if (mCheckPants.isChecked())
                        mPanty.setVisibility(View.VISIBLE);
                    else
                        mPanty.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };
}
