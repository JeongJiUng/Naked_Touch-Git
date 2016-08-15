package FrameWork.Button;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import FrameWork.cGraphicObject;

/**
 * Created by AVG on 2015-10-02.
 */
public class cImageButton extends cButton
{
    private cGraphicObject[]                    mImage;

    // 버튼이 그려질 위치 설정
    public cImageButton(Bitmap _disable, Bitmap _normal, Bitmap _down, int _x, int _y, BUTTON_STATE _state)
    {
        super();

        mImage              = new cGraphicObject[3];
        mImage[0]           = new cGraphicObject(_disable, _x, _y);
        mImage[1]           = new cGraphicObject(_normal, _x, _y);
        mImage[2]           = new cGraphicObject(_down, _x, _y);

        initButtonData(mImage[1].getPosition().x, mImage[1].getPosition().y, mImage[1].getWidth(), mImage[1].getHeight());
        setState(_state);
    }

    // 버튼이 그려질 위치와 버튼의 클릭 범위(넓이,높이 == left, top, right, bottom) 설정
    public cImageButton(Bitmap _disable, Bitmap _normal, Bitmap _down, int _x, int _y, int _left, int _top, int _right, int _bottom, BUTTON_STATE _state)
    {
        super();

        mImage              = new cGraphicObject[3];
        mImage[0]           = new cGraphicObject(_disable, _x, _y);
        mImage[1]           = new cGraphicObject(_normal, _x, _y);
        mImage[2]           = new cGraphicObject(_down, _x, _y);

        initButtonData(_left, _top, _right, _bottom);
        setState(_state);
    }

    public void recycle()
    {
        for (int i = 0; i < 3 ; i++)
        {
            if (mImage[i] != null)
                mImage[i].recycle();
        }
    }

    public void draw(Canvas _canvas)
    {
        if (mState != BUTTON_STATE.BUTTON_NODRAW)
            mImage[mState.getState()].draw(_canvas);
    }

    public void draw(Canvas _canvas, int _left, int _top, int _right, int _bottom)
    {
        if (mState != BUTTON_STATE.BUTTON_NODRAW)
        {
            mImage[mState.getState()].draw(_canvas, _left, _top, _right, _bottom);
        }
    }
}
