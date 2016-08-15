package GameSystem;

import android.graphics.Canvas;
import android.graphics.Color;

import FrameWork.cFPS;
import FrameWork.cFont;
import FrameWork.cGraphicObject;

/**
 * Created by AVG on 2016-05-13.
 */
public class cTimer
{
    private int             MAX_TIME;
    private int             mTime;

    private cFPS            mFPS;
    private cFont           mTimer;

    public cTimer(int _time, float _x, float _y, int _size)
    {
        MAX_TIME            = _time;
        mTime               = MAX_TIME;

        mFPS                = new cFPS(10);
        mTimer              = new cFont("", _x, _y, Color.BLACK, _size, "fonts/RIX_STAR_N_ME.TTF");
    }

    public void counting()
    {
        // TODO:: mTime(게임경과시간)이 1초씩 감소한다.
        mTime--;
    }

    public boolean isEndTime()
    {
        if (mTime <= 0)
            return true;
        return false;
    }

    public int getTime()
    {
        return mTime;
    }

    public int getClearTime()
    {
        // TODO:: 클리어까지 걸린 시간 반환
        return MAX_TIME - mTime;
    }

    public void update(long _timer)
    {
        if (mFPS.tick(_timer))
        {
            counting();

            if (mTime <= (MAX_TIME/2))
                mTimer.setColor(Color.YELLOW);
            if (mTime <= (MAX_TIME/3))
                mTimer.setColor(Color.argb(255, 255, 123, 0));
            if (mTime <= (MAX_TIME/7))
                mTimer.setColor(Color.RED);
        }
    }

    public void draw(Canvas _canvas)
    {
        mTimer.setString(String.valueOf(mTime));
        mTimer.draw(_canvas);
    }
}
