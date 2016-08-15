package GameView;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import FrameWork.cMediaManager;
import GameSystem.MEDIA_LIST;
import GameSystem.SOUND_LIST;
import FrameWork.cGameManager;
import FrameWork.cGameState;
import FrameWork.cSoundManager;
import GameSystem.cLoader;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-03-04.
 */
public class cMain implements cGameState
{
    /**
     * 0 = Game Start
     * 1 = Gallery
     * 2 = Shop
     * 3 = Help */
    int                     mSelectButNum;

    @Override
    public void init()
    {
        mSelectButNum       = 0;
    }

    @Override
    public void destroy()
    {
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

        if (cLoader.getInstance().mMenuBut[mSelectButNum] != null)
            cLoader.getInstance().mMenuBut[mSelectButNum].draw(_canvas);
    }

    @Override
    public boolean onKeyDown(int _keyCode, KeyEvent _event)
    {
        return false;
    }

    @Override
    public boolean onKeyUp(int _keyCode, KeyEvent _event) {
        return false;
    }

    @Override
    public void onBackPressed()
    {
        cMediaManager.getInstance().stop(MEDIA_LIST.MAIN.getMedia());
        cGameManager.getInstance().getActivity().finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event)
    {
        /*if (mSwapBut[0].onClickedUp(_event) == true)
            cGameManager.getInstance().getActivity().startActivity(new Intent(cGameManager.getInstance().getActivity(), cGameStartPopup.class));*/
        // TODO:: 메인뷰 스왑 버튼 이벤트 처리.
        if (cLoader.getInstance().mSwapBut[0].onClickedUp(_event) == true)
        {
            mSelectButNum--;
            if (mSelectButNum == -1)
                mSelectButNum   = 3;
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mSwapBut[1].onClickedUp(_event) == true)
        {
            mSelectButNum++;
            if (mSelectButNum == 4)
                mSelectButNum   = 0;
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        // TODO:: 메인뷰 메뉴 버튼 이벤트 처리.
        if (cLoader.getInstance().mMenuBut[mSelectButNum].onClickedUp(_event) == true)
        {
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
            switch (mSelectButNum)
            {
                case 0:
                    cGameManager.getInstance().getGameView().changeGameState(new cPlay());
                    break;

                case 1:
                    cGameManager.getInstance().getGameView().changeGameState(new cGallery());
                    break;

                case 2:
                    cGameManager.getInstance().getGameView().changeGameState(new cShop());
                    break;
            }
        }

        return true;
    }
}