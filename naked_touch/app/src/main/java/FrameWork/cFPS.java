package FrameWork;

/**
 * Created by AVG on 2015-09-20.
 */
public class cFPS
{
    private int             mDrawPerSec;        // 1초에 그려줄 프레임 개수
    private int             mFPS;               // 초당 프레임
    private long            mFrameTimer;        // 누적 시간량

    public cFPS()
    {
        mDrawPerSec  = 0;
        mFPS                = 0;
        mFrameTimer         = 0;
    }


    public cFPS(int _fps)
    {
        mDrawPerSec  = _fps;
        mFPS                = 10000 / _fps;
        mFrameTimer         = 0;
    }


    public void initFPS(int _fps)
    {
        mDrawPerSec  = _fps;
        mFPS                = 10000 / _fps;
    }


    /** 새롭게 넘어온 _fps 를 이용하여 mFPS 재설정.
     *  속도를 빠르게, 느리게 할 수 있음.
     *  getDrawPerSec() + 10 => 기존의 1초에 그려줄 프레임 개수에서 + 10
     *  mFPS = 1000 / 기존의 1초에 그려줄 프레임개수 + 10
     *  속도가 빨라짐 */
    public void setFPS(int _fps)
    {
        mDrawPerSec  = _fps;
        mFPS                = 1000 / _fps;
    }


    /** 현재 초당 얼마나 그려지는지 반환. */
    public int getFPS()
    {
        return mFPS;
    }


    /** 초당 그려지는 프레임 개수 반환 */
    public int getDrawPerSec()
    {
        return mDrawPerSec;
    }


    public boolean tick(long _gameTime)
    {
        if (mFrameTimer == 0)
            mFrameTimer		= _gameTime;
        if ( _gameTime > mFrameTimer + mFPS)
        {
            mFrameTimer     = _gameTime;
            return true;
        }
        return false;
    }
}
