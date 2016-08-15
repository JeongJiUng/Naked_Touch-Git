package FrameWork;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by AVG on 2016-05-15.
 * MediaPlayer
 */
public class cMediaManager
{
    private static cMediaManager mInstance;

    private int             mCurrentMd;                                                             // 재생되고 있는 Media ID

    private MediaPlayer     mMedia;
    private HashMap         mMediaMap;

    private Context         mContext;


    public static cMediaManager getInstance()
    {
        if(mInstance == null)
        {
            mInstance       = new cMediaManager();
        }
        return mInstance;
    }

    public void init(Context _cont)
    {
        mMediaMap           = new HashMap();
        mContext            = _cont;
    }

    public void addMedia(int _id, int _mdID)
    {
        mMedia              = MediaPlayer.create(mContext, _mdID);
        mMedia.setLooping(true);
        mMediaMap.put(_id, mMedia);
    }

    public void setLooping(int _id, boolean _bEnable)
    {
        if (mMediaMap.get(_id) == null)
            return;

        mMedia              = (MediaPlayer)mMediaMap.get(_id);
        mMedia.setLooping(_bEnable);
    }

    public void play(int _id)
    {
        if (mMediaMap.get(_id) == null)
            return;

        mMedia              = (MediaPlayer)mMediaMap.get(_id);
        if (mMedia.isPlaying() == false)
            mMedia.start();
        mCurrentMd          = _id;
    }

    public void pause(int _id)
    {
        if (mMediaMap.get(_id) == null)
            return;

        mMedia              = (MediaPlayer)mMediaMap.get(_id);
        if (mMedia.isPlaying() == true)
            mMedia.pause();
    }

    public void stop(int _id)
    {
        if (mMediaMap.get(_id) == null)
            return;

        mMedia              = (MediaPlayer)mMediaMap.get(_id);
        if (mMedia.isPlaying() == true)
        {
            /** Media를 정지시킨 다음, 다시 준비 시키고, 다음에 재생될 때 처음부터 재생될 수있도록 설정. */
            mMedia.stop();
            try {
                mMedia.prepare();
                mMedia.seekTo(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void autoPause()
    {
        // TODO:: 화면의 포커스를 잃으면 현재 재생중인 Media 일시 정지.
        this.pause(mCurrentMd);
    }

    public void autoResume()
    {
        // TODO:: 화면의 포커스를 잃었다가 다시 얻고 음악 실행.
        this.play(mCurrentMd);
    }

    public void stop()
    {
        // TODO:: 모든 MediaPlayer 멈춤.
        for (int i = 0; i < mMediaMap.size(); i++)
        {
            stop(i);
        }
    }
}
