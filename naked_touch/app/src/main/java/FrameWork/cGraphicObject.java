package FrameWork;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by AVG on 2015-09-10.
 */
public class cGraphicObject {
    protected boolean       mEnable;            // true : Enable, false : Disable

    protected float         mX;
    protected float         mY;
    protected float         mW;
    protected float         mH;

    protected int           mDPI;
    protected double        mViewWidth;
    protected double        mViewHeight;

    protected Paint         mPaint;
    protected Bitmap        mBitmap;


    public cGraphicObject(Bitmap _bitmap)
    {
        mEnable = true;
        mPaint = new Paint();
        mBitmap = _bitmap;

        /** ldpi(120dpi /기타 소형단말기) : 240 x 320
         *  mdpi(160dpi/G1, 옵티머스원): 320 x 480
         *  mdpi(160dpi/G1, 갤럭시 탭 7.0,):600 x 1024
         *  mdpi(160dpi/G1, 모토롤라 Xoom 10,갤럭시탭 7.7 & 10.1):1280 x 800
         *  hdpi(240dpi / 갤럭시 S/S2) : 480 x 800
         *  hdpi(240dpi / 모토로라 드로이드, XPERIA X10) : 480 x 854
         *  xhdpi(320dpi / 갤럭시 S3/노트II ) : 720 x 1280
         *  xhdpi(320dpi / 삼성 갤럭시 넥서스 ) : 720 x 1194 or 1280
         *  xhdpi(320dpi / 삼성 갤럭시 노트I  : 800 x 1280
         *  xhdpi(320dpi / LG 옵티머스G, 넥서스4) : 768 x 1280
         *  xxhdpi(480dpi / 갤럭시 S4 & 옵티머스G프로) : 1080×1920
         *  xxxhdpi(640dpi / LG G3) : 1440×2560 */
        /** 이미지를 그려줄때 기반이 되는 모바일 디스플레이 사이즈. (이걸 기반으로 이미지를 리사이징해줌.) 현재 160dpi 기반. */
        mViewWidth = cGameManager.getInstance().getWidth();
        mViewHeight = cGameManager.getInstance().getHeight();
        mDPI = cGameManager.getInstance().getDPI();          // dp로 변환하는것은 160dpi에서 픽셀의 물리적 크기에 대응하는 밀도 독립형 픽셀로 만든다는 것임. 따라서 160dpi를 사용.
    }

    public cGraphicObject(Bitmap _bitmap, int _x, int _y) {
        this(_bitmap);
        setPosition(_x, _y);
        setSize(_bitmap.getWidth(), _bitmap.getHeight());
    }

    public void recycle() {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    // 모바일 화면의 사이즈에 맞게 이미지 리사이징.
    public Bitmap resizedBitmap(Bitmap _bitmap) {
        this.setSize(_bitmap.getWidth(), _bitmap.getHeight());
        return Bitmap.createScaledBitmap(_bitmap, getWidth(), getHeight(), true);
    }

    public void setBitmap(Bitmap _bitmap) {
        mBitmap = _bitmap;
    }

    public void setAlpha(int _a) {
        mPaint.setAlpha(_a);
    }

    public void setPosition(int _x, int _y) {
        mX = (float) (_x / mViewWidth);
        mY = (float) (_y / mViewHeight);
    }

    public void setSize(int _w, int _h) {
        //TODO:: 이미지를 DPI에 맞게 기반이 되는 모바일 디스플레이 DPI의 사이즈 비율로 반환.
        mW = (float) ((_w / (cGameManager.getInstance().getDisplayMetrics().densityDpi / mDPI)) / mViewWidth);
        mH = (float) ((_h / (cGameManager.getInstance().getDisplayMetrics().densityDpi / mDPI)) / mViewHeight);
    }

    public Bitmap getBitmap() {
        return Bitmap.createScaledBitmap(mBitmap, getWidth(), getHeight(), true);
    }

    public int getAlpha() {
        return mPaint.getAlpha();
    }

    public Point getPosition() {
        //TODO:: 원하는 디스플레이 DPI의 사이즈 비율로 변환된 값을 현재 디스플레이의 값을 곱해 현재 디스플레이의 크기에 알맞도록 이미지 위치 설정.
        Point pos = new Point();
        pos.x = (int) (mX * cGameManager.getInstance().getDisplayMetrics().widthPixels);
        pos.y = (int) (mY * cGameManager.getInstance().getDisplayMetrics().heightPixels);
        return pos;
    }

    public int getWidth() {
        int w = (int) (mW * cGameManager.getInstance().getDisplayMetrics().widthPixels);
        return w;
    }

    public int getHeight() {
        int h = (int) (mH * cGameManager.getInstance().getDisplayMetrics().heightPixels);
        return h;
    }

    public void setEnable(boolean _enable) {
        mEnable = _enable;
    }

    public boolean getEnable() {
        return mEnable;
    }

    public void draw(Canvas _canvas) {
        if (mEnable == true && mBitmap != null && this != null) {
            Rect dst = new Rect();
            dst.set(getPosition().x, getPosition().y, getPosition().x + getWidth(), getPosition().y + getHeight());
            _canvas.drawBitmap(mBitmap, null, dst, mPaint);
        }
    }

    public void draw(Canvas _canvas, int _x, int _y)
    {
        if (mEnable == true && mBitmap != null && this != null)
        {
            setPosition(_x, _y);
            Rect dst = new Rect();
            dst.set(getPosition().x, getPosition().y, getPosition().x + getWidth(), getPosition().y + getHeight());
            _canvas.drawBitmap(mBitmap, null, dst, mPaint);
        }
    }

    // 이미지에서 얼마만큼의 영역만 그려줄 지 설정 가능.(기본 이미지 사이즈에서 확대해줌.)
    public void draw(Canvas _canvas, int _left, int _top, int _right, int _bottom)
    {
        if (mEnable == true && mBitmap != null && this != null)
        {
            Rect            rect = new Rect();
            Rect            dst = new Rect();

            rect.set(_left, _top, _right, _bottom);
            dst.set(getPosition().x, getPosition().y, getPosition().x + getWidth(), getPosition().y + getHeight());
            _canvas.drawBitmap(mBitmap, rect, dst, mPaint);
        }
    }

    // 이미지를 확대하여 그려줌.
    public void draw(Canvas _canvas, int _width, int _height, int _left, int _top, int _right, int _bottom)
    {
        if (mEnable == true && mBitmap != null && this != null)
        {
            Rect            rect = new Rect();
            Rect            dst = new Rect();

            rect.set(_left, _top, _right, _bottom);
            dst.set(getPosition().x, getPosition().y, getPosition().x + _width, getPosition().y + _height);
            _canvas.drawBitmap(mBitmap, rect, dst, mPaint);
        }
    }

    public void drawFullScreen(Canvas _canvas)
    {
        if (mEnable == true && mBitmap != null && this != null)
        {
            Rect            dst = new Rect();
            dst.set(0, 0, cGameManager.getInstance().getDisplayMetrics().widthPixels, cGameManager.getInstance().getDisplayMetrics().heightPixels);
            _canvas.drawBitmap(mBitmap, null, dst, mPaint);
        }
    }
}
