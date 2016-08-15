package GameSystem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import FrameWork.cFont;
import FrameWork.cGameManager;

/**
 * Created by AVG on 2016-04-04.
 */
public class cPassion
{
    private int             mCount;

    private cFont           font = new cFont("", 0, 0, Color.WHITE, 20, "fonts/RIX_STAR_N_ME.TTF");


    cPassion()
    {
    }

    public void setCount(int _i)
    {
        mCount              = _i;
    }

    public void addCount(int _i)
    {
        mCount              += _i;
    }

    public void subCount(int _i)
    {
        mCount              -= _i;
        if (mCount <= 0)
            mCount          = 0;
    }

    public int getPassionCount()
    {
        return mCount;
    }

    public String getPasstionCount_to_String()
    {
        return String.valueOf(mCount);
    }

    public void draw(Canvas _canvas)
    {
        font.setString(String.valueOf(mCount));
        font.setLocation(20, 45);
        font.draw(_canvas);
    }
}
