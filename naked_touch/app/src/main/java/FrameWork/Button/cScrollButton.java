package FrameWork.Button;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by AVG on 2016-03-15.
 * 버튼이 스크롤 형식으로 만들어짐.
 */
public class cScrollButton
{
    /**
     * left, top, right, bottom 을 이용해 버튼 이미지의 어떤 부분을 그려줄지 결정.(스크롤 효과)*/
    private int             mLeft;
    private int             mTop;
    private int             mRight;
    private int             mBottom;

    private cImageButton    mButton;

    // 스크롤 버튼이 그려질 위치와 스크롤 버튼의 넓이와 높이(left, top, right, bottom) 설정
    public cScrollButton(Bitmap _disable, Bitmap _normal, Bitmap _down, int _x, int _y, int _left, int _top, int _right, int _bottom, BUTTON_STATE _state)
    {
        mLeft               = _left;
        mTop                = _top;
        mRight              = _right;
        mBottom             = _bottom;

        mButton             = new cImageButton(_disable, _normal, _down, _x, _y, _left, _top, _right, _bottom, _state);
    }

    public void draw(Canvas _canvas)
    {
        mButton.draw(_canvas, mLeft, mTop, mRight, mBottom);
    }
}