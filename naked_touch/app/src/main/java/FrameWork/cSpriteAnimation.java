package FrameWork;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by AVG on 2015-09-13.
 */
public class cSpriteAnimation extends cGraphicObject
{
    private Rect            mRect;              // 그려줄 사각
    private Rect            mDest;              // 그려줄 위치
    private int             miFrames;           // 최대 프레임 수
    private int             mCurrentFrame;      // 현재 프레임
    private cFPS            mFPS;

    private int             mSpriteWidth;
    private int             mSpriteHeight;


    public cSpriteAnimation(Bitmap _bitmap)
    {
        super(_bitmap);
        mBitmap             = resizedBitmap(_bitmap);

        mRect               = new Rect(0, 0, 0, 0);
        mDest               = new Rect(0, 0, 0, 0);
        mFPS                = new cFPS();
        mCurrentFrame       = 0;
    }

    /** 넓이 / 높이 / 1초에 그려줄 프레임 수 / 프레임 총 갯수. */
    public void initSpriteData(int _x, int _y, int _fps, int _iFrame)
    {
        miFrames            = _iFrame;
        mFPS.initFPS(_fps);

        setSpriteSize(mBitmap.getWidth(), mBitmap.getHeight());

        mRect.top           = 0;
        mRect.bottom        = mSpriteHeight;
        mRect.left          = 0;
        mRect.right         = mSpriteWidth;

        setPosition(_x, _y);

        mDest.left          = getPosition().x;
        mDest.top           = getPosition().y;
        mDest.right         = getPosition().x + mSpriteWidth;
        mDest.bottom        = getPosition().y + mSpriteHeight;
    }

    public void setSpriteSize(int _w, int _h)
    {
        mSpriteWidth        = (_w / miFrames);
        mSpriteHeight       = _h;
    }

    public int getSpriteWidth()
    {
        return mSpriteWidth;
    }

    public int getSpriteHeight()
    {
        return mSpriteHeight;
    }

    public final cFPS getFPSInstance()
    {
        return mFPS;
    }

    public void update(long _gameTime)
    {
        /** 게임 시간 > 현재 프레임의 시간 + (1000/fps) */
        if (mFPS.tick(_gameTime) == true)
        {
            mCurrentFrame++;

            /** FPS 에 맞춰 이미지가 이동. */
            mDest.left      = getPosition().x;
            mDest.top       = getPosition().y;
            mDest.right     = getPosition().x + mSpriteWidth;
            mDest.bottom    = getPosition().y + mSpriteHeight;

            /** 이 프레임 값이 최대 프레임 값을 초과하면 다시 첫 번째 프레임을 그려라. */
            if (mCurrentFrame >= miFrames)
            {
                mCurrentFrame                   = 0;
                mRect.left  = 0;
                mRect.right = mSpriteWidth;
            }
            else
            {
                mRect.left  = mCurrentFrame * mSpriteWidth;
                mRect.right = mRect.left + mSpriteWidth;
            }
        }
    }

    public void draw(Canvas _canvas)
        {
            /** mRect.left / top 부터 시작해서 mRect.right / bottom 까지 mDest.left / top 부터 (그려줄 좌표 위치) right / bottom 만큼 영역을 설정해서 그려줌.*/
            if (mEnable == true && mBitmap != null)
            {
                _canvas.drawBitmap(mBitmap, mRect, mDest, null);
            }
    }
}