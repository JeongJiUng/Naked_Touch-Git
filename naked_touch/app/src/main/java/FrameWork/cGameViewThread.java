package FrameWork;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by AVG on 2015-09-06.
 */
public class cGameViewThread extends Thread
{
    private SurfaceHolder   mHolder;
    private cGameView       mGameView;
    private boolean         mRun = false;


    public cGameViewThread(SurfaceHolder _surfaceHolder, cGameView _gameView)
    {
        mHolder             = _surfaceHolder;
        mGameView           = _gameView;
    }

    public void setRunning(boolean _run)
    {
        mRun                = _run;
    }

    public void run()
    {
        Canvas              _canvas;

        while(mRun)
        {
            _canvas         = null;
            try
            {
                _canvas     = mHolder.lockCanvas();
                if (_canvas == null)
                    return;
                synchronized (mHolder)
                {
                    if (cGameManager.getInstance().isLoad() == true)
                    {
                        mGameView.onRender(_canvas);
                        mGameView.onUpdate();
                    }
                    else
                    {
                        /** 게임이 로딩중일 때 화면에 반투명한 이미지 띄움. */
                        Paint pnt = new Paint();
                        pnt.setColor(Color.BLACK);
                        pnt.setAlpha(100);
                        _canvas.drawRect(0, 0, cGameManager.getInstance().getDisplayMetrics().widthPixels, cGameManager.getInstance().getDisplayMetrics().heightPixels, pnt);
                    }
                }
            }
            finally
            {
                if (_canvas != null)
                    mHolder.unlockCanvasAndPost(_canvas);
            }
        }
    }
}
