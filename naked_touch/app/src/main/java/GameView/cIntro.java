package GameView;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.avg.naked_touch.R;

import FrameWork.cGameManager;
import FrameWork.cGameState;
import FrameWork.cGraphicObject;
import FrameWork.cMediaManager;
import FrameWork.cSpriteAnimation;
import GameSystem.MEDIA_LIST;
import GameSystem.cLoader;
import GameSystem.cUserData;

/**
 * Created by AVG on 2016-03-04.
 */
public class cIntro implements cGameState
{
    cGraphicObject          intro;
    cSpriteAnimation        text;

    @Override
    public synchronized void init()
    {
        // Intro Image Loading.
        intro               = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.intro), 0, 0);
        text                = new cSpriteAnimation(cGameManager.getInstance().getBitmap(R.drawable.press_any_key));
        text.initSpriteData(100, 612, 60, 2);

        cLoader.getInstance().loadSoundPack();
        cLoader.getInstance().loadMainSet();
        cUserData.getInstance().load();

        cMediaManager.getInstance().play(MEDIA_LIST.MAIN.getMedia());
    }

    @Override
    public void destroy()
    {
        intro.recycle();
        text.recycle();
    }

    @Override
    public void update(long _gameTime)
    {
        text.update(_gameTime);
    }

    @Override
    public void render(Canvas _canvas)
    {
        intro.drawFullScreen(_canvas);
        text.draw(_canvas);
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
        cGameManager.getInstance().getActivity().finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event)
    {
        cGameManager.getInstance().getGameView().changeGameState(new cMain());
        return true;
    }
}
