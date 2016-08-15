package FrameWork.Button;

/**
 * Created by AVG on 2015-09-22.
 */
public enum BUTTON_STATE
{
    BUTTON_DISABLE(0),
    BUTTON_NORMAL(1),
    BUTTON_DOWN(2),
    BUTTON_NODRAW(3);

    private int             mStateNum;

    BUTTON_STATE(int _i)
    {
        mStateNum           = _i;
    }


    public int getState()
    {
        return mStateNum;
    }
}
