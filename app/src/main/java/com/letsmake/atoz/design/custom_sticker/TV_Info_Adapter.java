package com.letsmake.atoz.design.custom_sticker;

public class TV_Info_Adapter {

    public TV_Info_Adapter(int i, String str, String str2, int i2, int i3, int i4, int i5, String str3, int i6, int i7, float f, float f2, int i8, int i9, float f3, String str4, int i10, int i11, int i12, int i13, int i14, int i15, String str5, String str6, String str7) {
        this.TEMPLATE_ID = i;
        this.TEXT = str;
        this.FONT_NAME = str2;
        this.TEXT_COLOR = i2;
        this.TEXT_ALPHA = i3;
        this.SHADOW_COLOR = i4;
        this.SHADOW_PROG = i5;
        this.BG_DRAWABLE = str3;
        this.BG_COLOR = i6;
        this.BG_ALPHA = i7;
        this.POS_X = f;
        this.POS_Y = f2;
        this.WIDTH = i8;
        this.HEIGHT = i9;
        this.ROTATION = f3;
        this.TYPE = str4;
        this.ORDER = i10;
        this.XRotateProg = i11;
        this.YRotateProg = i12;
        this.ZRotateProg = i13;
        this.CurveRotateProg = i14;
        this.FIELD_ONE = i15;
        this.FIELD_TWO = str5;
        this.FIELD_THREE = str6;
        this.FIELD_FOUR = str7;
    }

    private int BG_ALPHA = 255;
    private int BG_COLOR = 0;
    private String BG_DRAWABLE = "0";
    int CurveRotateProg;
    private String FIELD_FOUR = "";
    int FIELD_ONE = 0;
    private String FIELD_THREE = "";
    private String FIELD_TWO = "0,0";
    private String FONT_NAME = "";
    private int HEIGHT;
    private int ORDER;
    private float POS_X = 0.0f;
    private float POS_Y = 0.0f;
    private float ROTATION;
    private int SHADOW_COLOR = 0;
    private int SHADOW_PROG = 0;
    private int TEMPLATE_ID;
    private String TEXT = "";
    private int TEXT_ALPHA = 100;
    private int TEXT_COLOR = -16777216;
    private int TEXT_ID;
    private String TYPE = "";
    private int WIDTH;
    int XRotateProg, ZRotateProg, YRotateProg;


    public TV_Info_Adapter() {

    }


    public int getTVWIDTH() {
        return this.WIDTH;
    }

    public void setTVWIDTH(int i) {
        this.WIDTH = i;
    }

    public int getTVHEIGHT() {
        return this.HEIGHT;
    }

    public void setTVHEIGHT(int i) {
        this.HEIGHT = i;
    }

    public String get_TV_FONT_NAME() {
        return this.FONT_NAME;
    }

    public void setTV_FONT_NAME(String str) {
        this.FONT_NAME = str;
    }

    public String getTEXT() {
        return this.TEXT;
    }

    public void setTEXT(String str) {
        this.TEXT = str;
    }

    public int getTEXT_COLOR() {
        return this.TEXT_COLOR;
    }

    public void setTEXT_COLOR(int i) {
        this.TEXT_COLOR = i;
    }

    public void setTV_TYPE(String str) {
        this.TYPE = str;
    }

    public int getTV_ORDER() {
        return this.ORDER;
    }

    public void setTV_ORDER(int i) {
        this.ORDER = i;
    }

    public int getTV_TEXT_ID() {
        return this.TEXT_ID;
    }

    public void setTV_TEXT_ID(int i) {
        this.TEXT_ID = i;
    }

    public int getTV_TEMPLATE_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTV_TEMPLATE_ID(int i) {
        this.TEMPLATE_ID = i;
    }

    public int getTV_XRotateProg() {
        return this.XRotateProg;
    }

    public void setTV_XRotateProg(int i) {
        this.XRotateProg = i;
    }

    public int getTV_YRotateProg() {
        return this.YRotateProg;
    }

    public void setTV_YRotateProg(int i) {
        this.YRotateProg = i;
    }

    public int getTEXT_ALPHA() {
        return this.TEXT_ALPHA;
    }

    public void setTEXT_ALPHA(int i) {
        this.TEXT_ALPHA = i;
    }

    public int getTV_SHADOW_PROG() {
        return this.SHADOW_PROG;
    }

    public void setTVSHADOW_PROG(int i) {
        this.SHADOW_PROG = i;
    }

    public int getTV_SHADOW_COLOR() {
        return this.SHADOW_COLOR;
    }

    public void setTV_SHADOW_COLOR(int i) {
        this.SHADOW_COLOR = i;
    }

    public String getTV_BG_DRAWABLE() {
        return this.BG_DRAWABLE;
    }

    public void setTV_BG_DRAWABLE(String str) {
        this.BG_DRAWABLE = str;
    }

    public int getTV_BG_COLOR() {
        return this.BG_COLOR;
    }

    public void setTV_BG_COLOR(int i) {
        this.BG_COLOR = i;
    }

    public int getTV_BG_ALPHA() {
        return this.BG_ALPHA;
    }

    public void setTV_BG_ALPHA(int i) {
        this.BG_ALPHA = i;
    }

    public int getCurveRotateProg() {
        return this.CurveRotateProg;
    }

    public void setCurveRotateProg(int i) {
        this.CurveRotateProg = i;
    }

    public int getFIELD_ONE() {
        return this.FIELD_ONE;
    }

    public void setFIELD_ONE(int i) {
        this.FIELD_ONE = i;
    }

    public String getFIELD_TWO() {
        return this.FIELD_TWO;
    }

    public void setFIELD_TWO(String str) {
        this.FIELD_TWO = str;
    }

    public float getTVPOS_X() {
        return this.POS_X;
    }

    public void setTVPOS_X(float f) {
        this.POS_X = f;
    }

    public float getTVPOS_Y() {
        return this.POS_Y;
    }

    public void setTVPOS_Y(float f) {
        this.POS_Y = f;
    }

    public float get_TV_ROTATION() {
        return this.ROTATION;
    }

    public void setTV_ROTATION(float f) {
        this.ROTATION = f;
    }

    public String getTV_TYPE() {
        return this.TYPE;
    }


    public int getTV_ZRotateProg() {
        return this.ZRotateProg;
    }

    public void setTV_ZRotateProg(int i) {
        this.ZRotateProg = i;
    }


    public String getFIELD_THREE() {
        return this.FIELD_THREE;
    }

    public void setFIELD_THREE(String str) {
        this.FIELD_THREE = str;
    }

    public String getFIELD_FOUR() {
        return this.FIELD_FOUR;
    }

    public void setFIELD_FOUR(String str) {
        this.FIELD_FOUR = str;
    }

}
