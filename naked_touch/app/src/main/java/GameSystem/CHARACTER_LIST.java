package GameSystem;

/**
 * Created by AVG on 2016-03-21.
 */
public enum CHARACTER_LIST
{
    //TODO:: Temporary Character List.
    TEMP_CHAR1(0),
    TEMP_CHAR2(1),
    TEMP_CHAR3(2);

    private int             mCharNum;

    CHARACTER_LIST(int _i)
    {
        mCharNum            = _i;
    }

    public int getCharacter()
    {
        return mCharNum;
    }
}
