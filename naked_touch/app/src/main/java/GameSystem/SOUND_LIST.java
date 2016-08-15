package GameSystem;

/**
 * Created by AVG on 2016-04-24.
 */
public enum SOUND_LIST
{
    BUTTON_CLICK(1),
    START_BUTTON_CLICK(2);

    private int             mSoundNum;

    SOUND_LIST(int _i)
    {
        mSoundNum            = _i;
    }

    public int getSound()
    {
        return mSoundNum;
    }
}
