package com.letsmake.atoz.design.editor_intelligence;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

public class View_ID_Finder {

    private static final AtomicInteger sNext_VIEW_ID_GeneratedId = new AtomicInteger(1);

    @SuppressLint({"NewApi"})
    public static int generateViewId() {
        return View.generateViewId();
    }
}
