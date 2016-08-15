package FrameWork;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by AVG on 2015-09-07.
 * Singleton Pattern
 */
public class cGameManager
{
    private boolean         bLoad;
    private cGameView       mGameView;
    private Resources       mResources;
    private DisplayMetrics  mMetrics;
    private Activity        mActivity;

    /**
     * 베이스가 되는 디바이스의 DPI, 넓이, 높이
     */
    private int             mBaseDPI = 160;
    private double          mBaseWidth = 600;
    private double          mBaseHeight = 1024;

    private static cGameManager                 mInstance;


    public void setGameView(cGameView _gameView)
    {
        mGameView           = _gameView;
    }

    public void setResources(Resources _resources)
    {
        mResources          = _resources;
    }

    public void setDisplayMetrics(DisplayMetrics _metrics)
    {
        mMetrics            = _metrics;
    }

    public void setActivity(Activity _act)
    {
        mActivity           = _act;
    }

    public synchronized void isLoad(boolean _bEnable)
    {
        bLoad               = _bEnable;
        Log.d("Test is Load ==>", String.valueOf(_bEnable));
    }

    public cGameView getGameView()
    {
        return mGameView;
    }

    public Resources getResources()
    {
        return mResources;
    }

    public DisplayMetrics getDisplayMetrics()
    {
        return mMetrics;
    }

    public Activity getActivity()
    {
        return mActivity;
    }

    public Bitmap getBitmap(int _r)
    {
        return BitmapFactory.decodeResource(mResources, _r);
    }

    public synchronized boolean isLoad()
    {
        return bLoad;
    }

    public void pause(boolean _bEnable)
    {
        mGameView.setPause(_bEnable);
    }

    public void startPopup(Class<?> _c)
    {
        mActivity.startActivity(new Intent(mActivity, _c));
    }

    public void startPopup(Intent _int)
    {
        mActivity.startActivity(_int);
    }

    public int getDPI()
    {
        return mBaseDPI;
    }

    public double getWidth()
    {
        return mBaseWidth;
    }

    public double getHeight()
    {
        return mBaseHeight;
    }

    public static cGameManager getInstance()
    {
        if (mInstance == null)
        {
            mInstance       = new cGameManager();
        }
        return mInstance;
    }
}
