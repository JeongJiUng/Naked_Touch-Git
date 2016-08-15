package FrameWork.Button;

import android.graphics.Rect;
import android.view.MotionEvent;

public class cButton
{
    protected BUTTON_STATE  mState;
    protected Rect          mCheckBox;          // If come about onClick Event, check to button area.
    private boolean         mFlag;              // 버튼이 눌렸다가 뗏을 때 작동할 수 있도록 만들어진 플레그. true = 버튼이 눌림. false = 버튼이 안눌림.


    public cButton()
    {
        mCheckBox           = new Rect(0, 0, 0, 0);
    }


    protected void initButtonData(int _x, int _y, int _width, int _height)
    {
        mCheckBox.left      = _x;
        mCheckBox.top       = _y;
        mCheckBox.right     = _x + _width;
        mCheckBox.bottom    = _y + _height;
    }


    public void setState(BUTTON_STATE _state)
    {
        mState              = _state;
    }


    public BUTTON_STATE getState()
    {
        return mState;
    }


    private boolean checkArea(int _x, int _y)
    {
        if (_x >= mCheckBox.left && _x <= mCheckBox.right && _y >= mCheckBox.top && _y <= mCheckBox.bottom)
            return true;

        return false;
    }


    /** onTouchEvent(...) 에서 사용.
     *  반환값이 true 면 버튼 클릭 됨. -> 클릭 이벤트 실행
     *  false 면, 버튼 클릭 안 됨. */
    public boolean onClick(MotionEvent _event)
    {
        if (mState == BUTTON_STATE.BUTTON_NODRAW || mState == BUTTON_STATE.BUTTON_DISABLE)
            return false;

        if (_event.getAction() == MotionEvent.ACTION_DOWN && checkArea((int)_event.getX(), (int)_event.getY()))
        {
            mState          = BUTTON_STATE.BUTTON_DOWN;
            return true;
        }
        else if (_event.getAction() == MotionEvent.ACTION_UP)
            mState          = BUTTON_STATE.BUTTON_NORMAL;

        return false;
    }


    /** 버튼이 눌렸다가 떼어졌을 때 작동할 수 있도록 설계된 함수*/
    public boolean onClickedUp(MotionEvent _event)
    {
        if (mState == BUTTON_STATE.BUTTON_NODRAW || mState == BUTTON_STATE.BUTTON_DISABLE)
            return false;

        if (_event.getAction() == MotionEvent.ACTION_DOWN && mFlag == false && checkArea((int)_event.getX(), (int)_event.getY()))
        {
            mState          = BUTTON_STATE.BUTTON_DOWN;
            mFlag           = true;
            return false;
        }
        else if (_event.getAction() == MotionEvent.ACTION_UP && mFlag == true && checkArea((int)_event.getX(), (int)_event.getY()))
        {
            mState          = BUTTON_STATE.BUTTON_NORMAL;
            mFlag           = false;
            return true;
        }
        else if (_event.getAction() == MotionEvent.ACTION_UP)
        {
            mState          = BUTTON_STATE.BUTTON_NORMAL;
            mFlag           = false;
        }

        return false;
    }
}
