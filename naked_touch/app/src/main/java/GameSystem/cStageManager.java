package GameSystem;

import android.widget.Toast;

import FrameWork.cGameManager;
import FrameWork.cGameState;
import GameView.GameStart.cPlay_Char1_Hard;
import GameView.GameStart.cPlay_Char1_Hell;
import GameView.GameStart.cPlay_Char1_Normal;

/**
 * Created by AVG on 2016-04-30.
 */
public class cStageManager
{
    private int             mDecrement = 0;                                                          // 티켓 감소량
    private cGameState[][]  mStage;

    private static cStageManager    mInstance;

    public cStageManager()
    {
        /**
        * 게임 스테이지 초기화.*/
        mStage              = new cGameState[cUserData.getInstance().STAGE_COUNT][cUserData.getInstance().STAGE_LEVEL_COUNT];
        mStage[0][0]        = new cPlay_Char1_Normal();
        mStage[0][1]        = new cPlay_Char1_Hard();
        mStage[0][2]        = new cPlay_Char1_Hell();
    }

    public static cStageManager getInstance()
    {
        if (mInstance == null)
            mInstance       = new cStageManager();
        return mInstance;
    }

    public void setDecrement(int _dec)
    {
        mDecrement          = _dec;
    }

    public boolean start()
    {
        // 티켓 소모
        if (check())
        {
            cUserData.getInstance().getTicketInstance().subCount(mDecrement);
            cGameManager.getInstance().getGameView().changeGameState(mStage[cUserData.getInstance().getSelectCharNum()][cUserData.getInstance().getLevel()]);
            return true;
        }
        return false;
    }

    public boolean check()
    {
        if ((cUserData.getInstance().getTicketInstance().getTicketCount() - mDecrement) >= 0)
        {
            return true;
        }
        Toast.makeText(cGameManager.getInstance().getActivity(), "티켓이 부족합니다.", Toast.LENGTH_SHORT).show();
        return false;
    }
}
