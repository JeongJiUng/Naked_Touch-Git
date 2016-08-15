package Test;

import android.graphics.Canvas;

import com.avg.naked_touch.R;

import FrameWork.cFPS;
import FrameWork.cGameManager;
import FrameWork.cSpriteAnimation;

/**
 * Created by AVG on 2015-09-20.
 */
public class cMyCharacter
{
    private int             mX;
    private int             mY;
    private int             mdX;
    private int             mdY;

    private cSpriteAnimation                    mSprite;
    private cFPS            mHealTick;       // 초당 체력 회복량


    public cMyCharacter(int _x, int _y)
    {
        mSprite             = new cSpriteAnimation(cGameManager.getInstance().getBitmap(R.drawable.spriteimage));
        mHealTick           = new cFPS();

        this.initCharacterData(_x, _y);
    }


    public void initCharacterData(int _x, int _y)
    {
        mX                  = _x;
        mY                  = _y;
        mSprite.initSpriteData(_x, _y, 30, 3);
        mHealTick.initFPS(1);
    }

    public void setSpeed(int _speed)
    {
        //mSprite.getFPSInstance().setFPS(_speed);

        // TODO:: 테스트용 코드. 실제 게임에서는 캐릭터의 이동속도를 특정 상황(물약을 먹고 이동속도 증가)에 지정된 값으로 설정해줌.
        // TODO:: 따라서 아래 코드는 화면을 터치했을때 증가하는지 안하는지에 대한 변화를 관측하기 위한 코드임.
        mSprite.getFPSInstance().setFPS(mSprite.getFPSInstance().getDrawPerSec() + _speed);
    }


    public void moved(int _dx, int _dy)
    {
        // TODO:: cSpriteAnimation 내부적으로 FPS에 맞춰 이미지 이동시킴.
        mdX                 = _dx;
        mdY                 = _dy;

        mX                  += mdX;
        mY                  += mdY;

        mSprite.setPosition(mX, mY);
    }


    public void update(long _gameTime)
    {
        if (mHealTick.tick(_gameTime) == true)
        {
            // TODO:: 캐릭터의 초당 체력회복 등 시간에 따른 상승량등을 업데이트.
        }

        /** 이동속도 / 공격속도등에 관한 정보는 스프라이트 애니메이션의 FPS를 증가시켜 이동속도를 상승시킴. */
        mSprite.update(_gameTime);
    }


    public void draw(Canvas _canvas)
    {
        mSprite.draw(_canvas);
    }
}
