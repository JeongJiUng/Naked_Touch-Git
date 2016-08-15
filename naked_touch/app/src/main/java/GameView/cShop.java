package GameView;

import android.content.Intent;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.avg.naked_touch.Popup.cInAppBillingPopup;

import FrameWork.cGameManager;
import FrameWork.cGameState;
import FrameWork.cSoundManager;
import GameSystem.ITEM_ID;
import GameSystem.SOUND_LIST;
import GameSystem.cLoader;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-05-12.
 */
public class cShop implements cGameState
{
    int                     mSelectItem;


    @Override
    public void init()
    {
        mSelectItem         = 0;

        cLoader.getInstance().loadShopSet();
    }

    @Override
    public void destroy()
    {
        cLoader.getInstance().recycleShopSet();
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

        //TODO:: 아이템 썸네일
        cLoader.getInstance().mItemBut[mSelectItem].draw(_canvas);

        cLoader.getInstance().mCloseBut.draw(_canvas);
    }

    @Override
    public boolean onKeyDown(int _keyCode, KeyEvent _event)
    {
        return false;
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
            mSelectItem--;
            if (mSelectItem == -1)
                mSelectItem = ITEM_ID.values().length - 1;

            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mSwapBut[1].onClickedUp(_event) == true)
        {
            mSelectItem++;
            if (mSelectItem == ITEM_ID.values().length)
                mSelectItem = 0;

            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mCloseBut.onClickedUp(_event))
        {
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
            cGameManager.getInstance().getGameView().changeGameState(new cMain());
        }

        if (cLoader.getInstance().mItemBut[mSelectItem].onClickedUp(_event) == true)
        {
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);

            Intent          intent = new Intent(cGameManager.getInstance().getActivity(), cInAppBillingPopup.class);
            intent.putExtra("SELECT_ITEM", String.valueOf(mSelectItem));
            cGameManager.getInstance().startPopup(intent);
        }
        return false;
    }
}
