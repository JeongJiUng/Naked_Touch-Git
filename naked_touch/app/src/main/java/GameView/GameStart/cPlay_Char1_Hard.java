package GameView.GameStart;

import android.content.Intent;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.avg.naked_touch.Popup.cGameClearPopup;
import com.avg.naked_touch.Popup.cGameFailPopup;
import com.avg.naked_touch.Popup.cGamePausePopup;
import com.avg.naked_touch.R;

import FrameWork.cFPS;
import FrameWork.cGameManager;
import FrameWork.cGameState;
import FrameWork.cMediaManager;
import FrameWork.cSoundManager;
import GameSystem.CHARACTER_LIST;
import GameSystem.MEDIA_LIST;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameSystem.cTimer;
import GameSystem.cTouchAction;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-06-04.
 */
public class cPlay_Char1_Hard implements cGameState
{
    final int               PLAY = 0;
    final int               CLEAR = 1;
    final int               FAIL = 2;

    final int               MAX_COUNT = 4;                                                          // 게임 시작 카운트.
    final int               CLEAR_PASSION = 500;                                                    // 클리어시 얻는 열정 값.
    final int               MAX_TIME = 30;                                                          // 최대 시간 2분 (1/1000)

    int                     mClear;                                                                 // 0 : 게임 플레이, 1 : 게임 클리어, 2 : 클리어 실패
    int                     mCount;                                                                 // 게임 시작 카운트. MAX_COUNT 가 되면 게임 시작.

    int                     mPower;

    cTimer                  mTimer;
    cFPS                    mFPS;
    cTouchAction            action;


    @Override
    public void init()
    {
        // 게임 카운트다운 이미지.
        cLoader.getInstance().loadGameSet();
        cMediaManager.getInstance().stop(MEDIA_LIST.MAIN.getMedia());
        cMediaManager.getInstance().play(MEDIA_LIST.PLAY.getMedia());

        mClear              = PLAY;
        mCount              = 0;

        mPower              = cUserData.getInstance().getPower();

        mTimer              = new cTimer(MAX_TIME, 440, 980, 100);

        mFPS                = new cFPS(10);

        action              = new cTouchAction(cGameManager.getInstance().getBitmap(R.drawable.char01_nude_hard), 4);
        action.setPartsLocation(0, "팬티", cGameManager.getInstance().getBitmap(R.drawable.char01_pants_hard), 145, 676, 10);
        action.setPartsLocation(1, "브레지어", cGameManager.getInstance().getBitmap(R.drawable.char01_bra_hard), 201, 339, 10);
        action.setPartsLocation(2, "하의", cGameManager.getInstance().getBitmap(R.drawable.char01_bot_hard), 140, 677, 10);
        action.setPartsLocation(3, "상의", cGameManager.getInstance().getBitmap(R.drawable.char01_top_hard), 47, 324, 10);
    }

    @Override
    public void destroy()
    {
        cLoader.getInstance().recycleGameSet();
        cMediaManager.getInstance().stop(MEDIA_LIST.PLAY.getMedia());
        action.recycle();
    }

    private boolean fail()
    {
        if (mTimer.isEndTime())
        {
            //TODO:: 클리어 실패 애니메이션 update 추가.
            if (mClear == FAIL)
                return true;
            mClear          = FAIL;

            cGameManager.getInstance().startPopup(cGameFailPopup.class);
        }
        return false;
    }

    private boolean clear()
    {
        /** 게임 클리어 코드. */
        if (action.getClearEnable() == true)
        {
            //TODO:: 클리어 폭죽 애니메이션 update 추가.
            /** 게임 클리어시 클리어 팝업을 한번만 호출하기 위한 코드.*/
            if (mClear == CLEAR)
                return true;
            mClear          = CLEAR;

            Intent intent = new Intent(cGameManager.getInstance().getActivity(), cGameClearPopup.class);
            intent.putExtra("CLEAR_PASSION", String.valueOf(CLEAR_PASSION));
            int             temp = mTimer.getClearTime();
            String          time;
            time            = String.valueOf((temp/60)) + ":";
            time            += String.valueOf((temp%60));
            intent.putExtra("CLEAR_TIME", time);

            // User data에 클리어 정보 저장.
            cUserData.getInstance().setClearStage(CHARACTER_LIST.TEMP_CHAR1.getCharacter(), 1, true);
            cUserData.getInstance().getPassionInstance().setCount(cUserData.getInstance().getPassionInstance().getPassionCount() + CLEAR_PASSION);
            cGameManager.getInstance().startPopup(intent);
        }
        return false;
    }

    private void play(long _gameTime)
    {
        /** 게임 플레이 코드. */
        if (mCount <= MAX_COUNT)
        {
            if (mFPS.tick(_gameTime))
            {
                mCount++;
                if (mCount == MAX_COUNT)
                    cSoundManager.getInstance().play(SOUND_LIST.START_BUTTON_CLICK.getSound(), 0);
                else if (mCount < MAX_COUNT)
                {
                    cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
                    //TODO:: 게임 플레이 사운드 재생.
                }
            }
        }
        else if (mCount > MAX_COUNT)
        {
            if (action.getActionEnable() == false)
            {
                /** 게임 남은 시간 체크 */
                mTimer.update(_gameTime);
            }
            action.update(_gameTime);
        }
    }

    @Override
    public void update(long _gameTime)
    {
        if (fail() == true)
            return;

        if (clear() == true)
            return ;
        play(_gameTime);
    }

    @Override
    public void render(Canvas _canvas)
    {
        action.draw(_canvas);
        cLoader.getInstance().mTimerImg.draw(_canvas);
        cLoader.getInstance().mPartsInfoImg.draw(_canvas);
        action.parts_info_draw(_canvas);
        mTimer.draw(_canvas);

        if (0 < mCount && mCount <= MAX_COUNT)
        {
            if (cLoader.getInstance().mCountImg[mCount - 1] != null)
                cLoader.getInstance().mCountImg[mCount - 1].draw(_canvas);
        }

        if (mClear == CLEAR)
        {
            //TODO:: 클리어 애니메이션
        }
        else if (mClear == FAIL)
        {
            //TODO:: 클리어 실패 애니메이션
        }

        //TODO:: 디버그 코드.
      /*  Paint test = new Paint();
        test.setColor(Color.WHITE);
        test.setTextSize(20);
        _canvas.drawText("Count => " + String.valueOf(mCount), 0, 200, test);
        _canvas.drawText("Time => "+String.valueOf(mTimer.getTime()), 0, 220, test);
        _canvas.drawText("Pause => "+String.valueOf(cGameManager.getInstance().getGameView().isPause()), 0, 240, test);*/
    }

    @Override
    public boolean onKeyDown(int _keyCode, KeyEvent _event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int _keyCode, KeyEvent _event) {
        return false;
    }

    @Override
    public void onBackPressed()
    {
        cGameManager.getInstance().startPopup(cGamePausePopup.class);
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event)
    {
        if (mCount <= MAX_COUNT)
            return false;
        else
            action.hit(_event, _event.getX(), _event.getY());
        return false;
    }
}
