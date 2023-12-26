package com.letsmake.atoz.design.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.letsmake.atoz.design.app_utils.ViewElementInfo;
import com.letsmake.atoz.design.custom_sticker.TV_Info_Adapter;

public class DB_Handler extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_COMPONENT_INFO = "CREATE TABLE COMPONENT_INFO(COMP_ID INTEGER PRIMARY KEY,TEMPLATE_ID TEXT,POS_X TEXT,POS_Y TEXT,WIDHT TEXT,HEIGHT TEXT,ROTATION TEXT,Y_ROTATION TEXT,RES_ID TEXT,TYPE TEXT,ORDER_ TEXT,STC_COLOR TEXT,STC_OPACITY TEXT,XROTATEPROG TEXT,YROTATEPROG TEXT,ZROTATEPROG TEXT,STC_SCALE TEXT,STKR_PATH TEXT,COLORTYPE TEXT,STC_HUE TEXT,FIELD_ONE TEXT,FIELD_TWO TEXT,FIELD_THREE TEXT,FIELD_FOUR TEXT)";
    private static final String CREATE_TABLE_TEMPLATES = "CREATE TABLE TEMPLATES(TEMPLATE_ID INTEGER PRIMARY KEY,THUMB_URI TEXT,FRAME_NAME TEXT,RATIO TEXT,PROFILE_TYPE TEXT,SEEK_VALUE TEXT,TYPE TEXT,TEMP_PATH TEXT,TEMP_COLOR TEXT,OVERLAY_NAME TEXT,OVERLAY_OPACITY TEXT,OVERLAY_BLUR TEXT)";
    private static final String CREATE_TABLE_TEXT_INFO = "CREATE TABLE TEXT_INFO(TEXT_ID INTEGER PRIMARY KEY,TEMPLATE_ID TEXT,TEXT TEXT,FONT_NAME TEXT,TEXT_COLOR TEXT,TEXT_ALPHA TEXT,SHADOW_COLOR TEXT,SHADOW_PROG TEXT,BG_DRAWABLE TEXT,BG_COLOR TEXT,BG_ALPHA TEXT,POS_X TEXT,POS_Y TEXT,WIDHT TEXT,HEIGHT TEXT,ROTATION TEXT,TYPE TEXT,ORDER_ TEXT,XROTATEPROG TEXT,YROTATEPROG TEXT,ZROTATEPROG TEXT,CURVEPROG TEXT,FIELD_ONE TEXT,FIELD_TWO TEXT,FIELD_THREE TEXT,FIELD_FOUR TEXT)";

    private static final String BG_ALPHA = "BG_ALPHA";
    private static final String BG_COLOR = "BG_COLOR";
    private static final String BG_DRAWABLE = "BG_DRAWABLE";
    private static final String COLORTYPE = "COLORTYPE";
    private static final String COMPONENT_INFO = "COMPONENT_INFO";
    private static final String COMP_ID = "COMP_ID";
    private static final String CURVEPROG = "CURVEPROG";
    private static final String DATABASE_NAME = "POSTERMAKER_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String FIELD_FOUR = "FIELD_FOUR";
    private static final String FIELD_ONE = "FIELD_ONE";
    private static final String FIELD_THREE = "FIELD_THREE";
    private static final String FIELD_TWO = "FIELD_TWO";
    private static final String FONT_NAME = "FONT_NAME";
    private static final String FRAME_NAME = "FRAME_NAME";
    private static final String HEIGHT = "HEIGHT";
    private static final String ORDER = "ORDER_";
    private static final String OVERLAY_BLUR = "OVERLAY_BLUR";
    private static final String OVERLAY_NAME = "OVERLAY_NAME";
    private static final String OVERLAY_OPACITY = "OVERLAY_OPACITY";
    private static final String POS_X = "POS_X";
    private static final String POS_Y = "POS_Y";
    private static final String PROFILE_TYPE = "PROFILE_TYPE";
    private static final String RATIO = "RATIO";
    private static final String RES_ID = "RES_ID";
    private static final String ROTATION = "ROTATION";
    private static final String SEEK_VALUE = "SEEK_VALUE";
    private static final String SHADOW_COLOR = "SHADOW_COLOR";
    private static final String SHADOW_PROG = "SHADOW_PROG";
    private static final String STC_COLOR = "STC_COLOR";
    private static final String STC_HUE = "STC_HUE";
    private static final String STC_OPACITY = "STC_OPACITY";
    private static final String STC_SCALE = "STC_SCALE";
    private static final String STKR_PATH = "STKR_PATH";
    private static final String TEMPLATES = "TEMPLATES";
    private static final String TEMPLATE_ID = "TEMPLATE_ID";
    private static final String TEMP_COLOR = "TEMP_COLOR";
    private static final String TEMP_PATH = "TEMP_PATH";
    private static final String TEXT = "TEXT";
    private static final String TEXT_ALPHA = "TEXT_ALPHA";
    private static final String TEXT_COLOR = "TEXT_COLOR";
    private static final String TEXT_ID = "TEXT_ID";
    private static final String TEXT_INFO = "TEXT_INFO";
    private static final String THUMB_URI = "THUMB_URI";
    private static final String TYPE = "TYPE";
    private static final String WIDHT = "WIDHT";
    private static final String XROTATEPROG = "XROTATEPROG";
    private static final String YROTATEPROG = "YROTATEPROG";
    private static final String Y_ROTATION = "Y_ROTATION";
    private static final String ZROTATEPROG = "ZROTATEPROG";

    DB_Handler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DB_Handler getDatabaseHandler(Context context) {
        return new DB_Handler(context);
    }

    public long insert_row_template(Template_InfoData templateInfoData) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(THUMB_URI, templateInfoData.getTHUMB_INFO_URI());
        contentValues.put(FRAME_NAME, templateInfoData.getFRAME__INFO_NAME());
        contentValues.put(RATIO, templateInfoData.get_INFO_RATIO());
        contentValues.put(PROFILE_TYPE, templateInfoData.get_INFO_PROFILE_TYPE());
        contentValues.put(SEEK_VALUE, templateInfoData.get_INFO_SEEK_VALUE());
        contentValues.put(TYPE, templateInfoData.get__INFO_TYPE());
        contentValues.put(TEMP_PATH, templateInfoData.get_INFO_TEMP_PATH());
        contentValues.put(TEMP_COLOR, templateInfoData.get__INFO_TEMPCOLOR());
        contentValues.put(OVERLAY_NAME, templateInfoData.get_INFO_OVERLAY_NAME());
        contentValues.put(OVERLAY_OPACITY, Integer.valueOf(templateInfoData.get_INFO_OVERLAY_OPACITY()));
        contentValues.put(OVERLAY_BLUR, Integer.valueOf(templateInfoData.get_INFO_OVERLAY_BLUR()));
        Log.i("testing", "Before insertion ");
        long insert = writableDatabase.insert(TEMPLATES, null, contentValues);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID ");
        stringBuilder.append(insert);
        Log.i("testing", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Framepath ");
        stringBuilder.append(templateInfoData.getFRAME__INFO_NAME());
        Log.i("testing", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Thumb Path ");
        stringBuilder.append(templateInfoData.getTHUMB_INFO_URI());
        Log.i("testing", stringBuilder.toString());
        writableDatabase.close();
        return insert;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_TEMPLATES);
        sQLiteDatabase.execSQL(CREATE_TABLE_TEXT_INFO);
        sQLiteDatabase.execSQL(CREATE_TABLE_COMPONENT_INFO);
        Log.i("testing", "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS TEMPLATES");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS TEXT_INFO");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS COMPONENT_INFO");
        onCreate(sQLiteDatabase);
    }


    public void add_Component_Info_Row(ViewElementInfo viewElementInfo) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEMPLATE_ID, Integer.valueOf(viewElementInfo.getTEMPLATE_ID()));
        contentValues.put(POS_X, Float.valueOf(viewElementInfo.getPOS_X()));
        contentValues.put(POS_Y, Float.valueOf(viewElementInfo.getPOS_Y()));
        contentValues.put(WIDHT, Integer.valueOf(viewElementInfo.getWIDTH()));
        contentValues.put(HEIGHT, Integer.valueOf(viewElementInfo.getHEIGHT()));
        contentValues.put(ROTATION, Float.valueOf(viewElementInfo.getROTATION()));
        contentValues.put(Y_ROTATION, Float.valueOf(viewElementInfo.getY_ROTATION()));
        contentValues.put(RES_ID, viewElementInfo.getRES_ID());
        contentValues.put(TYPE, viewElementInfo.getTYPE());
        contentValues.put(ORDER, Integer.valueOf(viewElementInfo.getORDER()));
        contentValues.put(STC_COLOR, Integer.valueOf(viewElementInfo.getSTC_COLOR()));
        contentValues.put(STC_OPACITY, Integer.valueOf(viewElementInfo.getSTC_OPACITY()));
        contentValues.put(XROTATEPROG, Integer.valueOf(viewElementInfo.getXRotateProg()));
        contentValues.put(YROTATEPROG, Integer.valueOf(viewElementInfo.getYRotateProg()));
        contentValues.put(ZROTATEPROG, Integer.valueOf(viewElementInfo.getZRotateProg()));
        contentValues.put(STC_SCALE, Integer.valueOf(viewElementInfo.getScaleProg()));
        contentValues.put(STKR_PATH, viewElementInfo.getSTKR_PATH());

        if (viewElementInfo.getCOLORTYPE() != null) {
            contentValues.put(COLORTYPE, viewElementInfo.getCOLORTYPE());
        } else {
            contentValues.put(COLORTYPE, "colored");
        }

        contentValues.put(STC_HUE, Integer.valueOf(viewElementInfo.getSTC_HUE()));
        contentValues.put(FIELD_ONE, Integer.valueOf(viewElementInfo.getFIELD_ONE()));
        contentValues.put(FIELD_TWO, viewElementInfo.getFIELD_TWO());
        contentValues.put(FIELD_THREE, viewElementInfo.getFIELD_THREE());
        contentValues.put(FIELD_FOUR, viewElementInfo.getFIELD_FOUR());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(viewElementInfo.getPOS_X());
        stringBuilder.append(" ,");
        stringBuilder.append(viewElementInfo.getPOS_Y());
        stringBuilder.append(" ,");
        stringBuilder.append(viewElementInfo.getWIDTH());
        stringBuilder.append(" ,");
        stringBuilder.append(viewElementInfo.getHEIGHT());
        stringBuilder.append(" ,");
        stringBuilder.append(viewElementInfo.getROTATION());
        stringBuilder.append(" ,");
        stringBuilder.append(viewElementInfo.getY_ROTATION());
        stringBuilder.append(" ,");
        stringBuilder.append(viewElementInfo.getRES_ID());
        Log.e("insert sticker", stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(writableDatabase.insert(COMPONENT_INFO, null, contentValues));
        Log.e("insert id", stringBuilder2.toString());
        writableDatabase.close();
    }

    public void add_Text_Row(TV_Info_Adapter textViewInfoAdapter) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEMPLATE_ID, textViewInfoAdapter.getTV_TEMPLATE_ID());
        contentValues.put(TEXT, textViewInfoAdapter.getTEXT());
        contentValues.put(FONT_NAME, textViewInfoAdapter.get_TV_FONT_NAME());
        contentValues.put(TEXT_COLOR, textViewInfoAdapter.getTEXT_COLOR());
        contentValues.put(TEXT_ALPHA, textViewInfoAdapter.getTEXT_ALPHA());
        contentValues.put(SHADOW_COLOR, textViewInfoAdapter.getTV_SHADOW_COLOR());
        contentValues.put(SHADOW_PROG, textViewInfoAdapter.getTV_SHADOW_PROG());
        contentValues.put(BG_DRAWABLE, textViewInfoAdapter.getTV_BG_DRAWABLE());
        contentValues.put(BG_COLOR, textViewInfoAdapter.getTV_BG_COLOR());
        contentValues.put(BG_ALPHA, textViewInfoAdapter.getTV_BG_ALPHA());
        contentValues.put(POS_X, textViewInfoAdapter.getTVPOS_X());
        contentValues.put(POS_Y, textViewInfoAdapter.getTVPOS_Y());
        contentValues.put(WIDHT, textViewInfoAdapter.getTVWIDTH());
        contentValues.put(HEIGHT, textViewInfoAdapter.getTVHEIGHT());
        contentValues.put(ROTATION, textViewInfoAdapter.get_TV_ROTATION());
        contentValues.put(TYPE, textViewInfoAdapter.getTV_TYPE());
        contentValues.put(ORDER, textViewInfoAdapter.getTV_ORDER());
        contentValues.put(XROTATEPROG, textViewInfoAdapter.getTV_XRotateProg());
        contentValues.put(YROTATEPROG, textViewInfoAdapter.getTV_YRotateProg());
        contentValues.put(ZROTATEPROG, textViewInfoAdapter.getTV_ZRotateProg());
        contentValues.put(CURVEPROG, textViewInfoAdapter.getCurveRotateProg());
        contentValues.put(FIELD_ONE, textViewInfoAdapter.getFIELD_ONE());
        contentValues.put(FIELD_TWO, textViewInfoAdapter.getFIELD_TWO());
        contentValues.put(FIELD_THREE, textViewInfoAdapter.getFIELD_THREE());
        contentValues.put(FIELD_FOUR, textViewInfoAdapter.getFIELD_FOUR());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TEXT ID ");
        stringBuilder.append(writableDatabase.insert(TEXT_INFO, null, contentValues));
        writableDatabase.close();
    }

    public ArrayList<Template_InfoData> extractTemplateList(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM TEMPLATES WHERE TYPE='");
        stringBuilder.append(str);
        stringBuilder.append("' ORDER BY ");
        stringBuilder.append(TEMPLATE_ID);
        stringBuilder.append(" ASC;");
        str = stringBuilder.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(str, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(arrayList.size());
            Log.e("templateList size is", stringBuilder.toString());
        } else {
            do {
                Template_InfoData templateInfoData = new Template_InfoData();
                templateInfoData.setTEMPLATE_INFO_ID(rawQuery.getInt(0));
                templateInfoData.setTHUMB__INFO_URI(rawQuery.getString(1));
                templateInfoData.setFRAME_INFO_NAME(rawQuery.getString(2));
                templateInfoData.set_INFO_RATIO(rawQuery.getString(3));
                templateInfoData.set_INFO_PROFILE_TYPE(rawQuery.getString(4));
                templateInfoData.set_INFO_SEEK_VALUE(rawQuery.getString(5));
                templateInfoData.set_INFO_TYPE(rawQuery.getString(6));
                templateInfoData.set_INFOTEMP_PATH(rawQuery.getString(7));
                templateInfoData.set_INFO_TEMPCOLOR(rawQuery.getString(8));
                templateInfoData.set_INFO_OVERLAY_NAME(rawQuery.getString(9));
                templateInfoData.set_INFO_OVERLAY_OPACITY(rawQuery.getInt(10));
                templateInfoData.set_INFO_OVERLAY_BLUR(rawQuery.getInt(11));
                arrayList.add(templateInfoData);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(arrayList.size());
            Log.e("templateList size is", stringBuilder.toString());
        }
        return arrayList;
    }

    public ArrayList<Template_InfoData> find_Template_List_Des(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM TEMPLATES WHERE TYPE='");
        stringBuilder.append(str);
        stringBuilder.append("' ORDER BY ");
        stringBuilder.append(TEMPLATE_ID);
        stringBuilder.append(" DESC;");
        str = stringBuilder.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(str, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(arrayList.size());
            Log.e("templateList size is", stringBuilder.toString());
        } else {
            do {
                Template_InfoData templateInfoData = new Template_InfoData();
                templateInfoData.setTEMPLATE_INFO_ID(rawQuery.getInt(0));
                templateInfoData.setTHUMB__INFO_URI(rawQuery.getString(1));
                templateInfoData.setFRAME_INFO_NAME(rawQuery.getString(2));
                templateInfoData.set_INFO_RATIO(rawQuery.getString(3));
                templateInfoData.set_INFO_PROFILE_TYPE(rawQuery.getString(4));
                templateInfoData.set_INFO_SEEK_VALUE(rawQuery.getString(5));
                templateInfoData.set_INFO_TYPE(rawQuery.getString(6));
                templateInfoData.set_INFOTEMP_PATH(rawQuery.getString(7));
                templateInfoData.set_INFO_TEMPCOLOR(rawQuery.getString(8));
                templateInfoData.set_INFO_OVERLAY_NAME(rawQuery.getString(9));
                templateInfoData.set_INFO_OVERLAY_OPACITY(rawQuery.getInt(10));
                templateInfoData.set_INFO_OVERLAY_BLUR(rawQuery.getInt(11));
                arrayList.add(templateInfoData);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(arrayList.size());
            Log.e("templateList size is", stringBuilder.toString());
        }
        return arrayList;
    }

    public ArrayList<Template_InfoData> get_Template_List() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM TEMPLATES ORDER BY TEMPLATE_ID DESC;", null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                Template_InfoData templateInfoData = new Template_InfoData();
                templateInfoData.setTEMPLATE_INFO_ID(rawQuery.getInt(0));
                templateInfoData.setTHUMB__INFO_URI(rawQuery.getString(1));
                templateInfoData.setFRAME_INFO_NAME(rawQuery.getString(2));
                templateInfoData.set_INFO_RATIO(rawQuery.getString(3));
                templateInfoData.set_INFO_PROFILE_TYPE(rawQuery.getString(4));
                templateInfoData.set_INFO_SEEK_VALUE(rawQuery.getString(5));
                templateInfoData.set_INFO_TYPE(rawQuery.getString(6));
                templateInfoData.set_INFOTEMP_PATH(rawQuery.getString(7));
                templateInfoData.set_INFO_TEMPCOLOR(rawQuery.getString(8));
                templateInfoData.set_INFO_OVERLAY_NAME(rawQuery.getString(9));
                templateInfoData.set_INFO_OVERLAY_OPACITY(rawQuery.getInt(10));
                templateInfoData.set_INFO_OVERLAY_BLUR(rawQuery.getInt(11));
                arrayList.add(templateInfoData);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public ArrayList<ViewElementInfo> extract_Component_InfoList(int i, String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM COMPONENT_INFO WHERE TEMPLATE_ID='");
        stringBuilder.append(i);
        stringBuilder.append("' AND ");
        stringBuilder.append(TYPE);
        stringBuilder.append(" = '");
        stringBuilder.append(str);
        stringBuilder.append("'");
        String stringBuilder2 = stringBuilder.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(stringBuilder2, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                ViewElementInfo viewElementInfo = new ViewElementInfo();
                viewElementInfo.setCOMP_ID(rawQuery.getInt(0));
                viewElementInfo.setTEMPLATE_ID(rawQuery.getInt(1));
                viewElementInfo.setPOS_X(rawQuery.getFloat(2));
                viewElementInfo.setPOS_Y(rawQuery.getFloat(3));
                viewElementInfo.setWIDTH(rawQuery.getInt(4));
                viewElementInfo.setHEIGHT(rawQuery.getInt(5));
                viewElementInfo.setROTATION(rawQuery.getFloat(6));
                viewElementInfo.setY_ROTATION(rawQuery.getFloat(7));
                viewElementInfo.setRES_ID(rawQuery.getString(8));
                viewElementInfo.setTYPE(rawQuery.getString(9));
                viewElementInfo.setORDER(rawQuery.getInt(10));
                viewElementInfo.setSTC_COLOR(rawQuery.getInt(11));
                viewElementInfo.setSTC_OPACITY(rawQuery.getInt(12));
                viewElementInfo.setXRotateProg(rawQuery.getInt(13));
                viewElementInfo.setYRotateProg(rawQuery.getInt(14));
                viewElementInfo.setZRotateProg(rawQuery.getInt(15));
                viewElementInfo.setScaleProg(rawQuery.getInt(16));
                viewElementInfo.setSTKR_PATH(rawQuery.getString(17));
                viewElementInfo.setCOLORTYPE(rawQuery.getString(18));
                viewElementInfo.setSTC_HUE(rawQuery.getInt(19));
                viewElementInfo.setFIELD_ONE(rawQuery.getInt(20));
                viewElementInfo.setFIELD_TWO(rawQuery.getString(21));
                viewElementInfo.setFIELD_THREE(rawQuery.getString(22));
                viewElementInfo.setFIELD_FOUR(rawQuery.getString(23));
                arrayList.add(viewElementInfo);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public ArrayList<TV_Info_Adapter> extract_Text_Info_List(int i) {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM TEXT_INFO WHERE TEMPLATE_ID='");
        stringBuilder.append(i);
        stringBuilder.append("'");
        String stringBuilder2 = stringBuilder.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(stringBuilder2, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                TV_Info_Adapter textViewInfoAdapter = new TV_Info_Adapter();
                textViewInfoAdapter.setTV_TEXT_ID(rawQuery.getInt(0));
                textViewInfoAdapter.setTV_TEMPLATE_ID(rawQuery.getInt(1));
                textViewInfoAdapter.setTEXT(rawQuery.getString(2));
                textViewInfoAdapter.setTV_FONT_NAME(rawQuery.getString(3));
                textViewInfoAdapter.setTEXT_COLOR(rawQuery.getInt(4));
                textViewInfoAdapter.setTEXT_ALPHA(rawQuery.getInt(5));
                textViewInfoAdapter.setTV_SHADOW_COLOR(rawQuery.getInt(6));
                textViewInfoAdapter.setTVSHADOW_PROG(rawQuery.getInt(7));
                textViewInfoAdapter.setTV_BG_DRAWABLE(rawQuery.getString(8));
                textViewInfoAdapter.setTV_BG_COLOR(rawQuery.getInt(9));
                textViewInfoAdapter.setTV_BG_ALPHA(rawQuery.getInt(10));
                textViewInfoAdapter.setTVPOS_X(rawQuery.getFloat(11));
                textViewInfoAdapter.setTVPOS_Y(rawQuery.getFloat(12));
                textViewInfoAdapter.setTVWIDTH(rawQuery.getInt(13));
                textViewInfoAdapter.setTVHEIGHT(rawQuery.getInt(14));
                textViewInfoAdapter.setTV_ROTATION(rawQuery.getFloat(15));
                textViewInfoAdapter.setTV_TYPE(rawQuery.getString(16));
                textViewInfoAdapter.setTV_ORDER(rawQuery.getInt(17));
                textViewInfoAdapter.setTV_XRotateProg(rawQuery.getInt(18));
                textViewInfoAdapter.setTV_YRotateProg(rawQuery.getInt(19));
                textViewInfoAdapter.setTV_ZRotateProg(rawQuery.getInt(20));
                textViewInfoAdapter.setCurveRotateProg(rawQuery.getInt(21));
                textViewInfoAdapter.setFIELD_ONE(rawQuery.getInt(22));
                textViewInfoAdapter.setFIELD_TWO(rawQuery.getString(23));
                textViewInfoAdapter.setFIELD_THREE(rawQuery.getString(24));
                textViewInfoAdapter.setFIELD_FOUR(rawQuery.getString(25));
                arrayList.add(textViewInfoAdapter);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public boolean remove_Template_Info(int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM TEMPLATES WHERE TEMPLATE_ID='");
            stringBuilder.append(i);
            stringBuilder.append("'");
            writableDatabase.execSQL(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM COMPONENT_INFO WHERE TEMPLATE_ID='");
            stringBuilder.append(i);
            stringBuilder.append("'");
            writableDatabase.execSQL(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM TEXT_INFO WHERE TEMPLATE_ID='");
            stringBuilder.append(i);
            stringBuilder.append("'");
            writableDatabase.execSQL(stringBuilder.toString());
            writableDatabase.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove_Template_Info(String str) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM COMPONENT_INFO WHERE TEMPLATE_ID IN (SELECT TEMPLATE_ID FROM TEMPLATES WHERE TYPE ='");
            stringBuilder.append(str);
            stringBuilder.append("')");
            writableDatabase.execSQL(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM TEXT_INFO WHERE TEMPLATE_ID IN (SELECT TEMPLATE_ID FROM TEMPLATES WHERE TYPE ='");
            stringBuilder.append(str);
            stringBuilder.append("')");
            writableDatabase.execSQL(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM TEMPLATES WHERE TYPE ='");
            stringBuilder.append(str);
            stringBuilder.append("'");
            writableDatabase.execSQL(stringBuilder.toString());
            writableDatabase.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
