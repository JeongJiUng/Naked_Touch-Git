package GameView;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.avg.naked_touch.Popup.cGameStartPopup;
import com.avg.naked_touch.R;

import FrameWork.Button.BUTTON_STATE;
import FrameWork.Button.cImageButton;
import FrameWork.cGameManager;
import FrameWork.cGameState;
import FrameWork.cSoundManager;
import GameSystem.CHARACTER_LIST;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-03-21.
 */
public class cPlay implements cGameState
{
    /**
     * 0 = No.1 character
     * 1 = No2. character
     *          .
     *          .           */
    int                     mSelectThumbNum;


    @Override
    public void init()
    {
        mSelectThumbNum     = cUserData.getInstance().getSelectCharNum();                           // 유저가 이전에 선택했던 캐릭터 넘버로 초기화.

        cLoader.getInstance().loadPlaySet();
        loadSelectCharThumb();
    }

    @Override
    public void destroy()
    {
        cLoader.getInstance().recyclePlaySet();
    }

    @Override
    public void update(long _gameTime)
    {

    }

    @Override
    public void render(Canvas _canvas)
    {
        cLoader.getInstance().mMainBagImg.drawFullScreen(_canvas);
        if (cLoader.getInstance().mPassionImg != null)
            cLoader.getInstance().mPassionImg.draw(_canvas);
        if (cLoader.getInstance().mTicketImg != null)
            cLoader.getInstance().mTicketImg.draw(_canvas);
        cLoader.getInstance().mPower.setString("공격력 < "+cUserData.getInstance().getPower()+" >");
        cLoader.getInstance().mPower.draw(_canvas);

        cUserData.getInstance().getTicketInstance().draw(_canvas);
        cUserData.getInstance().getPassionInstance().draw(_canvas);

        cLoader.getInstance().mSwapBut[0].draw(_canvas);
        cLoader.getInstance().mSwapBut[1].draw(_canvas);

        if (cLoader.getInstance().mCharThumbBut[mSelectThumbNum] != null)
            cLoader.getInstance().mCharThumbBut[mSelectThumbNum].draw(_canvas);

        cLoader.getInstance().mCloseBut.draw(_canvas);
    }

    @Override
    public boolean onKeyDown(int _keyCode, KeyEvent _event)
    {
        return true;
    }

    @Override
    public boolean onKeyUp(int _keyCode, KeyEvent _event)
    {
        return false;
    }

    @Override
    public void onBackPressed()
    {
        cGameManager.getInstance().getGameView().changeGameState(new cMain());
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event)
    {
        if (cLoader.getInstance().mSwapBut[0].onClickedUp(_event) == true)
        {
            mSelectThumbNum--;
            if (mSelectThumbNum == -1)
                mSelectThumbNum = CHARACTER_LIST.values().length - 1;
            loadSelectCharThumb();
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mSwapBut[1].onClickedUp(_event) == true)
        {
            mSelectThumbNum++;
            if (mSelectThumbNum == CHARACTER_LIST.values().length)
                mSelectThumbNum = 0;
            loadSelectCharThumb();
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mCharThumbBut[mSelectThumbNum].onClickedUp(_event))
        {
            cGameManager.getInstance().startPopup(cGameStartPopup.class);
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mCloseBut.onClickedUp(_event))
        {
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
            cGameManager.getInstance().getGameView().changeGameState(new cMain());
        }

        return true;
    }

    void loadSelectCharThumb()
    {
        //TODO:: 선택된 캐릭터의 썸네일만 메모리 생성하여 그려 줌.
        for (int i = 0; i < CHARACTER_LIST.values().length; i++)
            if (cLoader.getInstance().mCharThumbBut[i] != null)
                cLoader.getInstance().mCharThumbBut[i].recycle();

        // 0 -> 1번 캐릭터 , 1 -> 2번 캐릭터
        switch(mSelectThumbNum)
        {
            case 0:
                cLoader.getInstance().mCharThumbBut[0]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1),
                                                                            300 - 100, 512 - 150, BUTTON_STATE.BUTTON_NORMAL);
                break;

            case 1:
                cLoader.getInstance().mCharThumbBut[1]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_2),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_2),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_2),
                                                                            300 - 100, 512 - 150, BUTTON_STATE.BUTTON_NORMAL);
                break;

            case 2:
                cLoader.getInstance().mCharThumbBut[2]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_3),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_3),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_3),
                                                                            300 - 100, 512 - 150, BUTTON_STATE.BUTTON_NORMAL);
                break;
        }

        cUserData.getInstance().setSelectCharNum(mSelectThumbNum);
    }
}
