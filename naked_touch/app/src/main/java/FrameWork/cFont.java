package FrameWork;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by AVG on 2016-05-13.
 */
public class cFont
{
    private float           mX, mY;
    private String          mString;
    private Paint           mPaint;

    public cFont()
    {
        mPaint              = new Paint();
        mPaint.setAntiAlias(true);
    }

    public cFont(String _str, float _x, float _y, int _color, int _size, String _font)
    {
        this();
        mString             = _str;

        setLocation(_x, _y);

        mPaint.setColor(_color);
        this.setSize(_size);

        Typeface            typeface = Typeface.createFromAsset(cGameManager.getInstance().getActivity().getAssets(), _font);
        mPaint.setTypeface(typeface);
    }

    public cFont(String _str, float _x, float _y, int _color, int _size)
    {
        this();
        mString             = _str;

        setLocation(_x, _y);

        mPaint.setColor(_color);
        this.setSize(_size);
    }

    public void setAlpha(int _a)
    {
        mPaint.setAlpha(_a);
    }

    public void setSize(float _size)
    {
        // 사이즈 * (실행되는 디바이스 dpi / 베이스가 되는 디바이스 dpi) ==> px -> dp로 변환.
        float               size = _size * (cGameManager.getInstance().getDisplayMetrics().densityDpi / 160);
        mPaint.setTextSize(size);
    }

    public void setString(String _str)
    {
        mString             = _str;
    }

    public void setLocation(float _x, float _y)
    {
        // 베이스가 되는 디바이스의 넓이와 높이로 좌표를 비율로 설정.
        float x             = (float)(_x / cGameManager.getInstance().getWidth());
        float y             = (float)(_y / cGameManager.getInstance().getHeight());

        // 설정된 비율을 실행되는 디바이스의 넓이와 높이를 사용하여 비율에 맞게 좌표 재설정.
        mX                  = x * cGameManager.getInstance().getDisplayMetrics().widthPixels;
        mY                  = y * cGameManager.getInstance().getDisplayMetrics().heightPixels;
    }

    public void setColor(int _color)
    {
        mPaint.setColor(_color);
    }

    public int getAlpha()
    {
        return mPaint.getAlpha();
    }

    public float getSize()
    {
        return mPaint.getTextSize();
    }

    public String getString()
    {
        return mString;
    }

    public void draw(Canvas _canvas)
    {
        _canvas.drawText(mString, mX, mY, mPaint);
    }
}
