package GameSystem;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import FrameWork.cFPS;
import FrameWork.cGameManager;

/**
 * Created by AVG on 2016-03-23.
 * 사용자의 데이터를 저장하고 불러오며 관리.
 * Ticket, Passion Manager Class.
 */
public class cUserData
{
    public final int        STAGE_COUNT = CHARACTER_LIST.values().length;
    public final int        STAGE_LEVEL_COUNT = 3;
    private final String    FILE_NAME = "adult_video_good.avg";

    private cFPS            fps = new cFPS(10);

    /**
     * 게임 플레이 데이터 */
    private int             mSelectCharNum;                                                         // 선택된 캐릭터.
    private int             mLevel;                                                                 // 선택된 난이도.
    private int             mPower;                                                                 // 캐릭터 터치 파워.

    /**
     * 게임 자원 */
    private cTicket         mTicket;
    private cPassion        mPassion;

    /**
     * 캐릭터 클리어 관련 데이터 */
    private boolean[][]     mStage;
    private boolean[]       mHidden;

    public static cUserData mInstance;


    public static cUserData getInstance()
    {
        if (mInstance == null)
            mInstance       = new cUserData();
        return mInstance;
    }

    public cTicket getTicketInstance()
    {
        return mTicket;
    }

    public cPassion getPassionInstance()
    {
        return mPassion;
    }

    public void setSelectCharNum(int _i)
    {
        mSelectCharNum      = _i;
    }

    public void setLevel(int _i)
    {
        mLevel              = _i;
    }

    public void setClearStage(int _stage, int _level, boolean _bClear)
    {
        // 0 : 노말, 1 : 하드, 2 : 헬
        mStage[_stage][_level]  = _bClear;
    }

    public void powerUp(int _i)
    {
        mPower              += _i;
    }

    public int getSelectCharNum()
    {
        return mSelectCharNum;
    }

    public int getLevel()
    {
        return mLevel;
    }

    public boolean getClearStage(int _stage, int _level)
    {
        return mStage[_stage][_level];
    }

    public int getPower()
    {
        return mPower;
    }

    public void load()
    {
        //TODO:: user data 생성.
        mTicket             = new cTicket();
        mPassion            = new cPassion();

        mStage              = new boolean[STAGE_COUNT][STAGE_LEVEL_COUNT];
        mHidden             = new boolean[STAGE_COUNT];

        try
        {
            FileInputStream stream = cGameManager.getInstance().getActivity().openFileInput(FILE_NAME);
            BufferedReader  buffer = new BufferedReader(new InputStreamReader(stream));

            //TODO:: user data load
            /**
             * 게임 플레이 데이터 */
            mSelectCharNum  = Integer.valueOf(buffer.readLine());
            mLevel          = Integer.valueOf(buffer.readLine());
            mPower          = Integer.valueOf(buffer.readLine());

            /**
             * 게임 자원 */
            mTicket.setCount(Integer.valueOf(buffer.readLine()));
            mTicket.setTime(Double.valueOf(buffer.readLine()));
            mPassion.setCount(Integer.valueOf(buffer.readLine()));

            mTicket.time_dif(Long.valueOf(buffer.readLine()));

            /**
             * 스테이지 및 히든 갤러리 */
            for (int i = 0; i < STAGE_COUNT; i++)
                for (int j = 0; j < STAGE_LEVEL_COUNT; j++)
                    mStage[i][j]    = Boolean.valueOf(buffer.readLine());
            for (int i = 0; i < STAGE_COUNT; i++)
                mHidden[i]  = Boolean.valueOf(buffer.readLine());

            buffer.close();
            stream.close();
        }
        catch (FileNotFoundException e)
        {
            mTicket.setCount(30);
            mPassion.setCount(0);
            mPower          = 1;

            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            FileOutputStream    stream = cGameManager.getInstance().getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            PrintWriter         out = new PrintWriter(stream);

            //TODO:: user data save
            /**
             * 게임 플레이 데이터 */
            out.println(mSelectCharNum);
            out.println(mLevel);
            out.println(mPower);

            /**
             * 게임 자원 */
            out.println(mTicket.getTicketCount());
            out.println(mTicket.getTime());
            out.println(mPassion.getPassionCount());

            long           close_time = System.currentTimeMillis()/1000;
            out.println(close_time);                                                                // 게임이 종료된 시간을 저장.

            /**
             * 스테이지 및 히든 갤러리 */
            for (int i = 0; i < STAGE_COUNT; i++)
                for (int j = 0; j < STAGE_LEVEL_COUNT; j++)
                    out.println(mStage[i][j]);
            for (int i = 0; i < STAGE_COUNT; i++)
                    out.println(mHidden[i]);

            out.close();
            stream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void auto_save(long _time)
    {
        if (fps.tick(_time))
        {
            save();
        }
    }
}
