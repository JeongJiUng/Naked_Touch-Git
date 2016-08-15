package GameSystem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import FrameWork.cFPS;
import FrameWork.cFont;
import FrameWork.cGameManager;

/**
 * Created by AVG on 2016-04-04.
 */
public class cTicket
{
    /**
     * 10분부터 1초씩 mTime이 감소.
     * 0이 되면 다시 10분으로 설정되고, 티켓 카운트 1 증가.
     * 1초가 지났는지에 대한 정보는 cFPS를 사용. */
    private final int       MAXIMUM = 30;       // 소지 가능한 최대 티켓 갯수
    private final double    MAX_TIME = 600;     // 10분 (1/1000 = 1sec)

    private int             mCount;
    private double          mTime;

    private cFont           ticket = new cFont("", 0, 0, Color.WHITE, 20, "fonts/RIX_STAR_N_ME.TTF");
    private cFont           count = new cFont("", 0, 0, Color.BLACK, 20, "fonts/RIX_STAR_N_ME.TTF");

    private cFPS            mFPS;


    cTicket()
    {
        mFPS                = new cFPS(10);
        mTime               = MAX_TIME;
    }

    public void time_dif(long _closeTime)
    {
        //TODO:: 게임이 종료된 시간과 다시 시작된 시간 차이를 구하여 티켓 충전 시간 조정.
        long                dif = Math.abs((System.currentTimeMillis()/1000) - _closeTime);
        int                 d = (int)(dif / MAX_TIME);                                              // ex) 1270 / 600 = 2 => 나온 값 만큼 티켓 수 추가.

        /** 저장한 시기의 티켓의 갯수가 최대치이면 아래의 코드를 실행하지 않고 함수 빠져나옴. */
        if (mCount >= MAXIMUM)
            return;

        /** d의 값이 저장한 시기의 티켓의 갯수와 합쳤을 때, MAXIMUM을 넘으면 티켓 수량을 최대치로 고정. */
        if (mCount + d > MAXIMUM)
        {
            mCount          = MAXIMUM;
        }
        else
            mCount          += d;

        if (mCount == MAXIMUM)
        {
            mCount          = MAXIMUM;
            mTime           = MAX_TIME;
            return;
        }
        mTime               = Math.abs(mTime - (int)(dif % 60));
    }

    public void setTime(double _time)
    {
        mTime              = _time;
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

    public double getTime()
    {
        return mTime;
    }

    public int getTicketCount()
    {
        return mCount;
    }

    public String getTicketCount_to_String()
    {
        return String.valueOf(mCount);
    }

    public void update(long _gameTime)
    {
        if (mCount >= MAXIMUM)
        {
            mTime = MAX_TIME;
            return;
        }

        if (mFPS.tick(_gameTime))
        {
            if (mTime == 0)
            {
                mTime = MAX_TIME;
                mCount++;
            }
            mTime--;
        }
    }

    // x, y = 티켓의 수량 위치.
    // x2,y2 = 티켓 충전 시간 위치.
    public void draw(Canvas _canvas)
    {
        ticket.setString(String.valueOf(mCount));
        ticket.setLocation(270, 45);
        ticket.draw(_canvas);

        if (mCount >= MAXIMUM)
            return;
        /**
         * 현재 남은 시간을 String 분:초로 변환 */
        String              time;
        time                = String.valueOf((int)(mTime/60)) + ":";
        time                += String.valueOf((int)(mTime%60));

        count.setString(time);
        count.setLocation(320, 80);
        count.draw(_canvas);
    }
}
