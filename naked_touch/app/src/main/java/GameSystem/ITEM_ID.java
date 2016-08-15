package GameSystem;

/**
 * Created by AVG on 2016-05-12.
 */
public enum ITEM_ID
{
    TOUCH_UP(0),
    TICKET_10(1),
    TICKET_50(2),
    TICKET_100(3);

    private int             mItemID;

    ITEM_ID(int _i)
    {
        mItemID            = _i;
    }

    public int getItem()
    {
        return mItemID;
    }
}
