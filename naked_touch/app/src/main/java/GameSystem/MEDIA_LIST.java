package GameSystem;

/**
 * Created by AVG on 2016-05-15.
 */
public enum MEDIA_LIST
{
    MAIN(0),
    PLAY(1);

    private int             mMpNum;

    MEDIA_LIST(int _i)
    {
        mMpNum            = _i;
    }

    public int getMedia()
    {
        return mMpNum;
    }
}
