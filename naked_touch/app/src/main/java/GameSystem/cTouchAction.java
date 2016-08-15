package GameSystem;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import FrameWork.cFPS;
import FrameWork.cFont;
import FrameWork.cGraphicObject;

/**
 * Created by AVG on 2016-04-18.
 */
public class cTouchAction
{
    private boolean         mClearEnable;
    private boolean         mActionEnable;                                                          // Action 상태 변수.
    private boolean         mHitEnable;

    private int             MAX_PARTS;                                                              // 최대 캐릭터 부위 수.
    private int             mCurrent_parts;                                                         // 현재 캐릭터 부위.
    private int             mCurrent_hit;                                                           // 현재 히트 수.
    private int[]           mHit;                                                                   // 부위 최대 히트 수.

    private Point[]         mOriginal_pos;                                                          // 진동되기 전 원래의 좌표.
    private Point[]         mVibrate_pos;                                                           // 진동 좌표.

    private cGraphicObject  mImage;
    private cGraphicObject[]    mPartsImg;

    private cFPS            mFPS;                                                                   // 부위의 알파값이 천천히 줄어드는 애니메이션을 표현하기 위한 fps.
    private cFPS            mVibrateFPS;                                                            // 진동을 위한 fps.

    private cFont[]         mPartsName;
    private cFont           mFont;


    /**
     * 캐릭터의 터치 부위 갯수와 캐릭터 메인 이미지를 생성. */
    public cTouchAction(Bitmap _bit, int _count)
    {
        //TODO:: _count = 캐릭터 부위 갯수.
        mClearEnable        = false;
        mActionEnable       = false;
        mHitEnable          = false;

        MAX_PARTS           = _count;
        mCurrent_parts      = MAX_PARTS-1;
        mCurrent_hit        = 0;
        mHit                = new int[MAX_PARTS];

        mOriginal_pos       = new Point[MAX_PARTS];
        mVibrate_pos        = new Point[MAX_PARTS];

        mImage              = new cGraphicObject(_bit, 0, 0);
        mPartsImg           = new cGraphicObject[MAX_PARTS];                                        // 부위 이미지.

        mFPS                = new cFPS(10000);
        mVibrateFPS         = new cFPS(1000);

        mPartsName          = new cFont[_count];
        mFont               = new cFont("", 10, 55, Color.WHITE, 15, "fonts/RIX_STAR_N_ME.TTF");
    }

    public void recycle()
    {
        mImage.recycle();
        for (int i = 0; i < MAX_PARTS; i++)
        {
            if (mPartsImg != null)
                mPartsImg[i].recycle();
        }
    }

    /**
     * 생성된 캐릭터의 터치 부위에 좌표와 히트수, 이미지를 생성.
     * 반드시 생성자에서 설정한 터치 부위의 갯수만큼 호출해서 부위를 생성해 줘야 함. */
    public void setPartsLocation(int _parts, String _name, Bitmap _bit, int _x, int _y, int _hit)
    {
        //TODO:: 요기해야함.
        mPartsImg[_parts]   = new cGraphicObject(_bit, _x, _y);
        mHit[_parts]        = _hit;
        mOriginal_pos[_parts]   = new Point(_x, _y);
        mVibrate_pos[_parts]= new Point(_x, _y);

        mPartsName[_parts]  = new cFont(_name, 80, 25, Color.WHITE, 20, "fonts/RIX_STAR_N_ME.TTF");
    }

    public boolean getClearEnable()
    {
        return mClearEnable;
    }

    public boolean getActionEnable()
    {
        return mActionEnable;
    }

    public Point getPartsPoint(int _parts)
    {
        return mPartsImg[_parts].getPosition();
    }

    public Point getCharPoint()
    {
        return mImage.getPosition();
    }

    public int getPartsWidth(int _parts)
    {
        return mPartsImg[_parts].getWidth();
    }

    public int getCharWidth()
    {
        return mImage.getWidth();
    }

    public int getPartsHeight(int _parts)
    {
        return mPartsImg[_parts].getHeight();
    }

    public int getCharHeight()
    {
        return mImage.getHeight();
    }

    public void vibration() {
        //TODO:: 부위를 터치하면 그 부위가 진동하는 효과를 냄.
        if (mVibrateFPS.tick(System.currentTimeMillis()) || mHitEnable == false)
        {
            mVibrate_pos[mCurrent_parts] = new Point(mOriginal_pos[mCurrent_parts]);
        }

        if (mHitEnable == true)
        {
            Random r = new Random();

            mVibrate_pos[mCurrent_parts].x = mOriginal_pos[mCurrent_parts].x + (r.nextInt(21) - 10);
            mVibrate_pos[mCurrent_parts].y = mOriginal_pos[mCurrent_parts].y + (r.nextInt(21) - 10);
            mHitEnable      = false;
        }
    }

    public void hit(MotionEvent _event, float _x, float _y)
    {
        //TODO:: 부위를 터치하면 hit 가 카운트됨.
        if (_event.getAction() == MotionEvent.ACTION_DOWN && mActionEnable == false && mClearEnable == false)
            if (mPartsImg[mCurrent_parts].getPosition().x <= _x && mPartsImg[mCurrent_parts].getPosition().y <=  _y&&
                    (mPartsImg[mCurrent_parts].getPosition().x + mPartsImg[mCurrent_parts].getWidth()) >= _x && (mPartsImg[mCurrent_parts].getPosition().y + mPartsImg[mCurrent_parts].getHeight()) >= _y)
            {
                mCurrent_hit += cUserData.getInstance().getPower();
                mHitEnable  = true;
            }
    }

    public void update(long _time)
    {
        //TODO:: mClearEnable false -> 게임 계속 진행. mClearEnable true -> 마지막 부위가 파괴됨, 게임 종료.
        /** 모든 부위가 클리어 되면.... 그 다음 이벤트 처리 */
        if (mClearEnable == true)
            return;

        /** 하나의 부위가 클리어 되면 클리어 된 부위의 알파값을 서서히 내리면서 다음 부위 활성화.*/
        if (mActionEnable == true)
        {
            if (mFPS.tick(_time))
            {
                mPartsImg[mCurrent_parts].setAlpha(mPartsImg[mCurrent_parts].getAlpha() - 5);
                if (mPartsImg[mCurrent_parts].getAlpha() <= 0)
                {
                    mCurrent_hit    = 0;
                    mCurrent_parts--;
                    mActionEnable   = false;
                    if (mCurrent_parts <= -1)
                        mClearEnable    = true;
                }
            }
            return;
        }

        vibration();

        /** 특정 부위의 정해진 히트수가 충족 되면, 액션 상태변수 활성화.*/
        if (mCurrent_hit >= mHit[mCurrent_parts])
        {
            if (mCurrent_parts > -1)
                mActionEnable   = true;
        }
    }

    public void draw(Canvas _canvas)
    {
        if (mImage != null)
            mImage.drawFullScreen(_canvas);

        for (int i = 0; i <  MAX_PARTS; i++)
        {
            if (mPartsImg[i] != null && mCurrent_parts > -1)
            {
                if (mCurrent_parts == i)
                    mPartsImg[i].draw(_canvas, mVibrate_pos[mCurrent_parts].x, mVibrate_pos[mCurrent_parts].y);
                else
                    mPartsImg[i].draw(_canvas, mVibrate_pos[i].x, mVibrate_pos[i].y);
            }
        }

     /*   //TODO:: 디버그 코드
        Paint test = new Paint();
        test.setColor(Color.WHITE);
        test.setTextSize(20);

        //_canvas.drawText("Alpha => " + String.valueOf(mPartsImg[mCurrent_parts].getAlpha()), 0, 40, test);
        _canvas.drawText(String.valueOf(mCurrent_parts) + " Touch => "+String.valueOf(mCurrent_hit), 0, 70, test);
        _canvas.drawText("Action =>"+String.valueOf(mActionEnable), 0, 100, test);
        _canvas.drawText("Clear =>"+String.valueOf(mClearEnable), 0, 130, test);*/
    }

    public void parts_info_draw(Canvas _canvas)
    {
        if (mCurrent_parts > -1)
        {
            mPartsName[mCurrent_parts].draw(_canvas);
            mFont.setString(mCurrent_hit + " / " + mHit[mCurrent_parts]);
            mFont.draw(_canvas);
        }
        else
        {
            mFont.setString("All Clear");
            mFont.draw(_canvas);
        }
    }
}
