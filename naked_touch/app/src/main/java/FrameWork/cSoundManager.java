package FrameWork;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import GameSystem.SOUND_LIST;

/**
 * Created by AVG on 2015-09-09.
 * Singleton Pattern
 * SoundPool
 */
public class cSoundManager
{
    private static cSoundManager                mInstance;
    private SoundPool       mSoundPool;
    private HashMap         mSoundPoolMap_play;                                                     // 사운드 재생을 위해 사운드 스트림 아이디를 저장할 해쉬맵.
    private HashMap         mSoundPoolMap_stop;                                                     // 사운드 중지를 위해 사운드 스트림 아이디를 저장할 해쉬맵.
    private AudioManager    mAudioManager;
    private Context         mActivity;


    public void init(Context _context)
    {
        mSoundPool          = new SoundPool(SOUND_LIST.values().length, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap_play  = new HashMap();
        mSoundPoolMap_stop  = new HashMap();
        mAudioManager       = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
        mActivity           = _context;
    }

    public void addSound(int _index, int _soundID)
    {
        /** 사운드를 로드 */
        int                 id = mSoundPool.load(mActivity, _soundID, 1);
        /** 해시맵에 아이디 값을 받아온 인덱스로 저장 */
        mSoundPoolMap_play.put(_index, id);
    }

    public void play(int _index, int _priority)
    {
        /** TODO:: 오디오 매니저에서 스트림 값을 얻어와 최대 스트림 값과 연산해서 사용자의 불륨 값에 맞춰 불륨을 조절.*/
        /** 사운드 재생. */
        float               streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume        = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play((Integer) mSoundPoolMap_play.get(_index), streamVolume, streamVolume, _priority, 0, 1f);
    }

    public void playLooped(int _index, int _priority)
    {
        /** TODO:: 오디오 매니저에서 스트림 값을 얻어와 최대 스트림 값과 연산해서 사용자의 불륨 값에 맞춰 불륨을 조절.*/
        /** 사운드 재생. */
        float               streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume        = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSoundPoolMap_stop.put(_index, mSoundPool.play((Integer) mSoundPoolMap_play.get(_index), streamVolume, streamVolume, _priority, -1, 1f));
    }

    public void stop(int _index)
    {
        if (mSoundPoolMap_stop.get(_index) != null)
        {
            mSoundPool.stop((Integer) mSoundPoolMap_stop.get(_index));
            mSoundPoolMap_stop.remove(_index);
        }
    }

    public void autoPause()
    {
        mSoundPool.autoPause();
    }

    public void autoResume()
    {
        mSoundPool.autoResume();
    }

    public static cSoundManager getInstance()
    {
        if(mInstance == null)
        {
            mInstance       = new cSoundManager();
        }
        return mInstance;
    }
}
