package GameSystem;

import android.graphics.Color;

import com.avg.naked_touch.R;

import FrameWork.Button.BUTTON_STATE;
import FrameWork.Button.cImageButton;
import FrameWork.cFont;
import FrameWork.cGameManager;
import FrameWork.cGraphicObject;
import FrameWork.cMediaManager;
import FrameWork.cSoundManager;

/**
 * Created by AVG on 2016-03-22.
 */
public class cLoader
{
    // TODO:: image variable
    public cGraphicObject   mMainBagImg;
    public cGraphicObject   mPassionImg;
    public cGraphicObject   mTicketImg;
    public cGraphicObject   mTimerImg;
    public cGraphicObject   mPartsInfoImg;

    public cFont            mPower;
    /**
     * 게임 시작 카운트 이미지
     * 0 - 1
     * 1 - 2
     * 2 - 3
     * 3 - Go */
    public cGraphicObject[] mCountImg;


    // TODO::Button variable
    public cImageButton     mCloseBut;
    /**
     * mSwapBut[0] == left Swap Button
     * mSwapBut[1] == right Swap Button */
    public cImageButton[]   mSwapBut;
    /**
     * mMenuBut[0] == Game Start
     * mMenuBut[1] == Gallery
     * mMenuBut[2] == Shop
     * mMenuBut[3] == Help */
    public cImageButton[]   mMenuBut;
    /**
     * 캐릭터 썸네일 버튼.
     * mCharTumBut[0] ~ mCharTumBut[..]
     * */
    public cImageButton[]   mCharThumbBut;
    /**
     * 캐릭터 갤러리 버튼
     * mCharGalleBut[0] ~ mChar GalleBut[STAGE_COUNT * STAGE_LEVEL_COUNT]*/
    public cImageButton[]   mCharGalleBut;
    /**
     * 아이템 버튼
     * mItem[0] ~ mItem[ITEM_ID.size()] */
    public cImageButton[]   mItemBut;

    private static cLoader  mInstance;


    public static cLoader getInstance()
    {
        if (mInstance == null)
            mInstance       = new cLoader();
        return mInstance;
    }

    private void load()
    {
       /* cFPS                fps = new cFPS(10);
        while (true)
            if (fps.tick(System.currentTimeMillis()))
                break;*/
    }

    public void loadSoundPack()
    {
        //TODO:: Sound
        cSoundManager.getInstance().addSound(SOUND_LIST.BUTTON_CLICK.getSound(), R.raw.button_click);
        cSoundManager.getInstance().addSound(SOUND_LIST.START_BUTTON_CLICK.getSound(), R.raw.start_button_click);

        //TODO:: Media
        cMediaManager.getInstance().addMedia(MEDIA_LIST.MAIN.getMedia(), R.raw.main);
        cMediaManager.getInstance().addMedia(MEDIA_LIST.PLAY.getMedia(), R.raw.play);
    }

    public void loadMainSet()
    {
        mMainBagImg         = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.main_bag_ground));

        //TODO:: Passion, Ticket Image.
        mPassionImg         = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.passion_count),10, 10);
        mTicketImg          = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.ticket_count), 260, 10);

        mPower              = new cFont("", 10, 90, Color.BLACK, 20, "fonts/RIX_STAR_N_ME.TTF");

        //TODO:: Menu swap button
        mSwapBut            = new cImageButton[2];
        mSwapBut[0]         = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.main_left_swap_but_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.main_left_swap_but_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.main_left_swap_but_down),
                                                10, 487, BUTTON_STATE.BUTTON_NORMAL);
        mSwapBut[1]         = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.main_right_swap_but_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.main_right_swap_but_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.main_right_swap_but_down),
                                                600-60, 487, BUTTON_STATE.BUTTON_NORMAL);

        //TODO:: Menu button
        mMenuBut            = new cImageButton[4];
        mMenuBut[0]         = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_game_start), cGameManager.getInstance().getBitmap(R.drawable.button_game_start), cGameManager.getInstance().getBitmap(R.drawable.button_game_start_down),
                                                300-100, 512-50, BUTTON_STATE.BUTTON_NORMAL);
        mMenuBut[1]         = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_gallery), cGameManager.getInstance().getBitmap(R.drawable.button_gallery), cGameManager.getInstance().getBitmap(R.drawable.button_gallery_down),
                                                300-100, 512-50, BUTTON_STATE.BUTTON_NORMAL);
        mMenuBut[2]         = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_shop), cGameManager.getInstance().getBitmap(R.drawable.button_shop), cGameManager.getInstance().getBitmap(R.drawable.button_shop_down),
                                                300-100, 512-50, BUTTON_STATE.BUTTON_NORMAL);
        mMenuBut[3]         = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_help), cGameManager.getInstance().getBitmap(R.drawable.button_help), cGameManager.getInstance().getBitmap(R.drawable.button_help_down),
                                                300-100, 512-50, BUTTON_STATE.BUTTON_NORMAL);
    }

    public void loadPlaySet()
    {
        //TODO:: Close button
        mCloseBut           = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_close_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.button_close_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.button_close_down),
                                                600-120, 1024-120, BUTTON_STATE.BUTTON_NORMAL);

        //TODO:: Character thumbnail button
        mCharThumbBut       = new cImageButton[CHARACTER_LIST.values().length];
    }

    public void loadGallerySet()
    {
        mCloseBut           = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_close_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.button_close_normal),
                                                cGameManager.getInstance().getBitmap(R.drawable.button_close_down),
                                                600-120, 1024-120, BUTTON_STATE.BUTTON_NORMAL);
        mCharGalleBut       = new cImageButton[CHARACTER_LIST.values().length * 3];
    }

    public void loadShopSet()
    {
        mCloseBut           = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.button_close_normal),
                cGameManager.getInstance().getBitmap(R.drawable.button_close_normal),
                cGameManager.getInstance().getBitmap(R.drawable.button_close_down),
                600-120, 1024-120, BUTTON_STATE.BUTTON_NORMAL);

        //TODO:: 아이템 이미지 버튼
        mItemBut            = new cImageButton[ITEM_ID.values().length];
        mItemBut[ITEM_ID.TOUCH_UP.getItem()]   = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_power_up),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_power_up),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_power_up),
                                                                    300-100, 512-100, BUTTON_STATE.BUTTON_NORMAL);
        mItemBut[ITEM_ID.TICKET_10.getItem()]   = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_10),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_10),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_10),
                                                                    300-100, 512-100, BUTTON_STATE.BUTTON_NORMAL);
        mItemBut[ITEM_ID.TICKET_50.getItem()]   = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_50),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_50),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_50),
                                                                    300-100, 512-100, BUTTON_STATE.BUTTON_NORMAL);
        mItemBut[ITEM_ID.TICKET_100.getItem()]  = new cImageButton(cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_100),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_100),
                                                                    cGameManager.getInstance().getBitmap(R.drawable.thumb_nail_ticket_100),
                                                                    300-100, 512-100, BUTTON_STATE.BUTTON_NORMAL);
    }

    public void loadGameSet()
    {
        //TODO:: 게임 플레이에 필요한 이미지 로드.
        mCountImg           = new cGraphicObject[4];
        mCountImg[0]        = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.image_count_1), 250, 462);
        mCountImg[1]        = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.image_count_2), 250, 462);
        mCountImg[2]        = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.image_count_3), 250, 462);
        mCountImg[3]        = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.image_count_go), 250, 462);

        mTimerImg           = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.timer), 400, 824);
        mPartsInfoImg       = new cGraphicObject(cGameManager.getInstance().getBitmap(R.drawable.parts_infor_img),0, 0);
    }

    public void recycleMainSet()
    {
        mMainBagImg.recycle();
        mPassionImg.recycle();
        mTicketImg.recycle();

        mSwapBut[0].recycle();
        mSwapBut[1].recycle();

        for (int i = 0; i < 4; i++)
            if (mMenuBut[i] != null)
                mMenuBut[i].recycle();
    }

    public void recyclePlaySet()
    {
        if (mCloseBut != null)
            mCloseBut.recycle();

        for (int i = 0; i < CHARACTER_LIST.values().length; i++)
            if (mCharThumbBut[i] != null)
                mCharThumbBut[i].recycle();
    }

    public void recycleGallerySet()
    {
        if (mCloseBut != null)
            mCloseBut.recycle();

        for (int i = 0; i < CHARACTER_LIST.values().length *3; i++)
            if (mCharGalleBut[i] != null)
                mCharGalleBut[i].recycle();
    }

    public void recycleShopSet()
    {
        if (mCloseBut != null)
            mCloseBut.recycle();

        for (int i = 0; i < ITEM_ID.values().length; i++)
            if (mItemBut[i] != null)
                mItemBut[i].recycle();
    }

    public void recycleGameSet()
    {
        for (int i = 0; i < 4; i++)
        {
            if (mCountImg[i] != null)
                mCountImg[i].recycle();
        }

        mTimerImg.recycle();
        mPartsInfoImg.recycle();
    }
}
