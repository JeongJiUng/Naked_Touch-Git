package GameView;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.avg.naked_touch.Popup.cGalleryPopup;
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
 * Created by AVG on 2016-05-02.
 */
public class cGallery implements cGameState
{
    public boolean          mEnable = false;
    int                     mSelectThumbNum;

    Paint pnt               = new Paint();

    @Override
    public void init()
    {
        mSelectThumbNum     = 0;

        cLoader.getInstance().loadGallerySet();
        loadSelectCharThumb();
    }

    @Override
    public void destroy()
    {
        cLoader.getInstance().recycleGallerySet();
    }

    @Override
    public void update(long _gameTime)
    {
    }

    @Override
    public void render(Canvas _canvas)
    {
        if (cLoader.getInstance().mMainBagImg != null)
            cLoader.getInstance().mMainBagImg.drawFullScreen(_canvas);

        if (cLoader.getInstance().mPassionImg != null)
        {
            cLoader.getInstance().mPassionImg.draw(_canvas);
            cUserData.getInstance().getPassionInstance().draw(_canvas);
        }
        if (cLoader.getInstance().mTicketImg != null)
        {
            cLoader.getInstance().mTicketImg.draw(_canvas);
            cUserData.getInstance().getTicketInstance().draw(_canvas);
        }
        if (cLoader.getInstance().mPower != null)
        {
            cLoader.getInstance().mPower.setString("공격력 < " + cUserData.getInstance().getPower() + " >");
            cLoader.getInstance().mPower.draw(_canvas);
        }

        if (cLoader.getInstance().mSwapBut[0] != null && cLoader.getInstance().mSwapBut[1] != null)
        {
            cLoader.getInstance().mSwapBut[0].draw(_canvas);
            cLoader.getInstance().mSwapBut[1].draw(_canvas);
        }

        //TODO:: 캐릭터 섬네일
        if (cLoader.getInstance().mCharGalleBut[mSelectThumbNum] != null)
            cLoader.getInstance().mCharGalleBut[mSelectThumbNum].draw(_canvas);

        if (cLoader.getInstance().mCloseBut != null)
            cLoader.getInstance().mCloseBut.draw(_canvas);

        if (mEnable == true)
        {
            pnt.setColor(Color.BLACK);
            _canvas.drawRect(0, 0, cGameManager.getInstance().getDisplayMetrics().widthPixels, cGameManager.getInstance().getDisplayMetrics().heightPixels, pnt);
        }
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
        cGameManager.getInstance().getGameView().changeGameState(new cMain());
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event)
    {
        if (cLoader.getInstance().mSwapBut[0].onClickedUp(_event) == true)
        {
            mSelectThumbNum--;
            if (mSelectThumbNum == -1)
                mSelectThumbNum = (CHARACTER_LIST.values().length * 3) - 1;
            loadSelectCharThumb();
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mSwapBut[1].onClickedUp(_event) == true)
        {
            mSelectThumbNum++;
            if (mSelectThumbNum == (CHARACTER_LIST.values().length * 3))
                mSelectThumbNum = 0;
            loadSelectCharThumb();
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mCharGalleBut[mSelectThumbNum].onClickedUp(_event))
        {
            cLoader.getInstance().recycleMainSet();

            Intent          intent = new Intent(cGameManager.getInstance().getActivity(), cGalleryPopup.class);
            intent.putExtra("SELECT_GALLERY", String.valueOf(mSelectThumbNum));
            cGameManager.getInstance().startPopup(intent);
            mEnable         = true;

            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
        }

        if (cLoader.getInstance().mCloseBut.onClickedUp(_event))
        {
            cSoundManager.getInstance().play(SOUND_LIST.BUTTON_CLICK.getSound(), 0);
            cGameManager.getInstance().getGameView().changeGameState(new cMain());
        }

        return false;
    }

    private void loadSelectCharThumb()
    {
        for (int i = 0; i < CHARACTER_LIST.values().length * 3; i++)
            if (cLoader.getInstance().mCharGalleBut[i] != null)
                cLoader.getInstance().mCharGalleBut[i].recycle();

        switch (mSelectThumbNum)
        {
            case 0:                                                                                 // 1번 캐릭 normal 갤러리.
                cLoader.getInstance().mCharGalleBut[0]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_normal_disable),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1),
                                                                            300 - 100, 512 - 150, BUTTON_STATE.BUTTON_DISABLE);
                if (cUserData.getInstance().getClearStage(CHARACTER_LIST.TEMP_CHAR1.getCharacter(), 0) == true)
                    cLoader.getInstance().mCharGalleBut[0].setState(BUTTON_STATE.BUTTON_NORMAL);
                break;

            case 1:                                                                                 // 1번 캐릭 hard 갤러리.
                cLoader.getInstance().mCharGalleBut[1]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_hard_disable),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_hard),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_hard),
                                                                            300 - 100, 512 - 150, BUTTON_STATE.BUTTON_DISABLE);
                if (cUserData.getInstance().getClearStage(CHARACTER_LIST.TEMP_CHAR1.getCharacter(), 1) == true)
                    cLoader.getInstance().mCharGalleBut[1].setState(BUTTON_STATE.BUTTON_NORMAL);
                break;

            case 2:                                                                                 // 1번 캐릭 hell 갤러리.
                cLoader.getInstance().mCharGalleBut[2]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_hell_disable),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_hell),
                                                                            cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_1_hell),
                                                                            300 - 100, 512 - 150, BUTTON_STATE.BUTTON_DISABLE);
                if (cUserData.getInstance().getClearStage(CHARACTER_LIST.TEMP_CHAR1.getCharacter(), 2) == true)
                    cLoader.getInstance().mCharGalleBut[2].setState(BUTTON_STATE.BUTTON_NORMAL);
                break;
        }
    }
}
