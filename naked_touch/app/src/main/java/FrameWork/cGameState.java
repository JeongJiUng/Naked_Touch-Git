package FrameWork;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by AVG on 2015-09-07.
 */
public interface cGameState
{
    void                    init();
    void                    destroy();
    void                    update(long _gameTime);
    void                    render(Canvas _canvas);
    boolean                 onKeyDown(int _keyCode, KeyEvent _event);
    boolean                 onKeyUp(int _keyCode, KeyEvent _event);
    void                    onBackPressed();
    boolean                 onTouchEvent(MotionEvent _event);
}
