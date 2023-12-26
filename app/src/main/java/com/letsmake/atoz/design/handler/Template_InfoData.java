package com.letsmake.atoz.design.handler;

public class Template_InfoData {

    public int OVERLAY_BLUR = 0, OVERLAY_OPACITY = 0, TEMPLATE_ID;
    public String TYPE, TEMP_PATH = "", THUMB_URI, SEEK_VALUE, TEMPCOLOR = "", RATIO, PROFILE_TYPE, FRAME_NAME, OVERLAY_NAME = "";

    public int getTEMPLATE_INFO_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTEMPLATE_INFO_ID(int i) {
        this.TEMPLATE_ID = i;
    }

    public String getTHUMB_INFO_URI() {
        return this.THUMB_URI;
    }

    public void setTHUMB__INFO_URI(String str) {
        this.THUMB_URI = str;
    }

    public String getFRAME__INFO_NAME() {
        return this.FRAME_NAME;
    }

    public void setFRAME_INFO_NAME(String str) {
        this.FRAME_NAME = str;
    }

    public String get_INFO_RATIO() {
        return this.RATIO;
    }

    public void set_INFO_RATIO(String str) {
        this.RATIO = str;
    }

    public String get_INFO_PROFILE_TYPE() {
        return this.PROFILE_TYPE;
    }

    public void set_INFO_PROFILE_TYPE(String str) {
        this.PROFILE_TYPE = str;
    }

    public String get_INFO_SEEK_VALUE() {
        return this.SEEK_VALUE;
    }

    public void set_INFO_SEEK_VALUE(String str) {
        this.SEEK_VALUE = str;
    }

    public String get__INFO_TYPE() {
        return this.TYPE;
    }

    public void set_INFO_TYPE(String str) {
        this.TYPE = str;
    }

    public int get_INFO_OVERLAY_OPACITY() {
        return this.OVERLAY_OPACITY;
    }

    public void set_INFO_OVERLAY_OPACITY(int i) {
        this.OVERLAY_OPACITY = i;
    }

    public String get__INFO_TEMPCOLOR() {
        return this.TEMPCOLOR;
    }

    public void set_INFO_TEMPCOLOR(String str) {
        this.TEMPCOLOR = str;
    }

    public String get_INFO_OVERLAY_NAME() {
        return this.OVERLAY_NAME;
    }

    public void set_INFO_OVERLAY_NAME(String str) {
        this.OVERLAY_NAME = str;
    }

    public String get_INFO_TEMP_PATH() {
        return this.TEMP_PATH;
    }

    public void set_INFOTEMP_PATH(String str) {
        this.TEMP_PATH = str;
    }

    public int get_INFO_OVERLAY_BLUR() {
        return this.OVERLAY_BLUR;
    }

    public void set_INFO_OVERLAY_BLUR(int i) {
        this.OVERLAY_BLUR = i;
    }
}
