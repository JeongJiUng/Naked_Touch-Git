package FrameWork.Button;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import FrameWork.cSpriteAnimation;

/**
 * Created by AVG on 2015-10-05.
 */
public class cAnimationButton extends cButton
{
    private cSpriteAnimation[]                  mAnimation;


    public cAnimationButton(Bitmap _disable, Bitmap _normal, Bitmap _down)
    {
        super();

        mAnimation          = new cSpriteAnimation[3];
        mAnimation[0]       = new cSpriteAnimation(_disable);
        mAnimation[1]       = new cSpriteAnimation(_normal);
        mAnimation[2]       = new cSpriteAnimation(_down);
    }

    /** iFrame = 스프라이트 갯수 */
    public cAnimationButton(Bitmap _disable, Bitmap _normal, Bitmap _down, int _x, int _y,int _fps, int _iFrame, BUTTON_STATE _state)
    {
        this(_disable, _normal, _down);

        mAnimation[0].initSpriteData(_x, _y, _fps, _iFrame);
        mAnimation[1].initSpriteData(_x, _y, _fps, _iFrame);
        mAnimation[2].initSpriteData(_x, _y, _fps, _iFrame);

        initButtonData(mAnimation[1].getPosition().x, mAnimation[1].getPosition().y, mAnimation[1].getSpriteWidth(), mAnimation[1].getSpriteHeight());
        setState(_state);
    }


    public cAnimationButton(Bitmap _disable, Bitmap _normal, Bitmap _down, int _x, int _y, int _disable_fps, int _normal_fps, int _down_fps, int _disable_iFrame, int _normal_iFrame, int _down_iFrame, BUTTON_STATE _state)
    {
        this(_disable, _normal, _down);

        mAnimation[0].initSpriteData(_x, _y, _disable_fps, _disable_iFrame);
        mAnimation[1].initSpriteData(_x, _y, _normal_fps, _normal_iFrame);
        mAnimation[2].initSpriteData(_x, _y, _down_fps, _down_iFrame);

        initButtonData(mAnimation[1].getPosition().x, mAnimation[1].getPosition().y, mAnimation[1].getSpriteWidth(), mAnimation[1].getSpriteHeight());
        setState(_state);
    }

    public void recycle()
    {
        for (int i = 0; i < 3 ; i++)
        {
            if (mAnimation[i] != null)
                mAnimation[i].recycle();
        }
    }


    public void update(long _gameTime)
    {
        mAnimation[mState.getState()].update(_gameTime);
    }


    public void draw(Canvas _canvas)
    {
        if (mState != BUTTON_STATE.BUTTON_NODRAW)
            mAnimation[mState.getState()].draw(_canvas);
    }
}
