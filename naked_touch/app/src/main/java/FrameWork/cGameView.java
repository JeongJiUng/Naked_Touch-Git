package FrameWork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import GameSystem.cUserData;
import GameView.cIntro;

/**
 * Created by AVG on 2015-09-06.
 */
public class cGameView extends SurfaceView implements SurfaceHolder.Callback
{
    private boolean         pause;
    private cGameViewThread mThread;
    private cGameState      mState;


    public cGameView(Context context)
    {
        super(context);
        this.setSoundEffectsEnabled(false);

        pause               = false;
        /** 키 입력을 받기 위해서*/
        setFocusable(true);

        /** 현재 생성자에 Callback 등록 */
        getHolder().addCallback(this);
        mThread             = new cGameViewThread(getHolder(), this);

        /** Observer pattern을 통해 cGameView를 제어하기 위해 설정.*/
        cGameManager.getInstance().setGameView(this);
        cGameManager.getInstance().setResources(getResources());

        /** Sound Manager / MediaManager 초기화 */
        cSoundManager.getInstance().init(context);
        cMediaManager.getInstance().init(context);

        /** 게임이 실행되면 가장 처음 나타낼 State 설정*/
        cGameManager.getInstance().getGameView().changeGameState(new cIntro());
    }

    /** 현재 보여 줄 게임의 State 를 설정한다. Menu, Game, Ending, Credit*/
    public synchronized void changeGameState(cGameState _state)
    {
        synchronized (_state)
        {
            cGameManager.getInstance().isLoad(false);
            if (mState != null)
                mState.destroy();
            _state.init();
            mState          = _state;
            cGameManager.getInstance().isLoad(true);
        }
    }

    public void onUpdate()
    {
        long                gameTime = System.currentTimeMillis();

        if (pause == false)
            mState.update(gameTime);

        cUserData.getInstance().getTicketInstance().update(gameTime);
        cUserData.getInstance().auto_save(gameTime);
    }

    public void onRender(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        mState.render(canvas);
        postInvalidate();
    }

    public boolean onKeyDown(int _keyCode, KeyEvent _event)
    {
        Log.d("KeyCode", String.valueOf(_keyCode));

        if (pause == false)
            mState.onKeyDown(_keyCode, _event);

        return super.onKeyDown(_keyCode, _event);
    }

    @Override
    public boolean onKeyUp(int _keyCode, KeyEvent _event)
    {
        if (pause == false)
            mState.onKeyUp(_keyCode, _event);

        return super.onKeyUp(_keyCode, _event);
    }

    public void onBackPressed()
    {
        mState.onBackPressed();
    }

    public boolean onTouchEvent(MotionEvent _event)
    {
        /** ACTION_DOWN : 화면을 터치했을때 발생하는 이벤트
         *  ACTION_MOVE : 화면을 누르고 있는 상태에서 움직일 때 발생하는 이벤트
         *  ACTION_UP : 화면을 누르고 있다가 뗄 때 발생하는 이벤트
         *  ACTION_CANCLE : 체스처를 취하다가 중단했을 때 발생하는 이벤트
         *  ACTION_OUTSIDE : 화면을 누르고 있는 상태에서 화면 밖으로 나가면 발생하는 이벤트 */
        if (pause == false)
            mState.onTouchEvent(_event);
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder _holder, int _format, int _width, int _height)
    {
        // TODO:: Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder _holder)
    {
        // TODO:: Auto-generated method stub
        try
        {
            // 일단 스레드를 실행해 본다.
            mThread.setRunning(true);
            mThread.start();
        }
        catch (Exception e) // 안되면...
        {
            // 스레드를 다시 만들고 실행한다. (====> 포커스를 잃었을때 스레드를 다시 실행시키는 방법)
            mThread         = null;
            mThread         = new cGameViewThread(getHolder(), this);
            mThread.setRunning(true);
            mThread.start();
            // 포커스를 다시 얻으면 사운드 다시 재생.
            cMediaManager.getInstance().autoResume();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder _holder)
    {
        // TODO:: Auto-generated method stub
        boolean             retry = true;
        mThread.setRunning(false);
        // 포커스를 잃으면 사운드 정지.
        cMediaManager.getInstance().autoPause();
        Log.d("Media Pause", "set");

        while(retry)
        {
            try
            {
                mThread.join();
                retry       = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPause(boolean _bEnable)
    {
        this.pause          = _bEnable;
    }

    public boolean isPause()
    {
        return pause;
    }

    public cGameState getGameState()
    {
        return mState;
    }
}
