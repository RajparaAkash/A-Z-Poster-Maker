package com.letsmake.atoz.design.custom_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

public class TextView_ReSized extends AppCompatTextView {

    public static float _minTextSize;
    public float _maxTextSize, _spacingAdd, _spacingMult;
    public int _widthLimit, _maxLines;
    private final RectF _availableSpaceRect;
    private boolean _initialized;
    public TextPaint _paint;
    private final ReSizeTVTester _Re_sizeTVTester;


    public TextView_ReSized(Context context) {
        this(context, null, 16842884);
    }

    public TextView_ReSized(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public TextView_ReSized(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        _minTextSize = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._Re_sizeTVTester = new TextReSizeTV();
        this._initialized = true;
    }

    private interface ReSizeTVTester {
        int onTestSize(int i, RectF rectF);
    }


    public boolean isValidWordWrap(char c, char c2) {
        return c == ' ' || c == '-';
    }


    class TextReSizeTV implements ReSizeTVTester {
        final RectF textRect = new RectF();

        TextReSizeTV() {
        }

        public int onTestSize(int i, RectF rectF) {
            String charSequence;
            TextView_ReSized.this._paint.setTextSize((float) i);
            TransformationMethod transformationMethod = TextView_ReSized.this.getTransformationMethod();
            if (transformationMethod != null) {
                charSequence = transformationMethod.getTransformation(TextView_ReSized.this.getText(), TextView_ReSized.this).toString();
            } else {
                charSequence = TextView_ReSized.this.getText().toString();
            }
            if (TextView_ReSized.this.getMaxLines() == 1) {
                this.textRect.bottom = TextView_ReSized.this._paint.getFontSpacing();
                this.textRect.right = TextView_ReSized.this._paint.measureText(charSequence);
            } else {
                StaticLayout staticLayout = new StaticLayout(charSequence, TextView_ReSized.this._paint, TextView_ReSized.this._widthLimit, Alignment.ALIGN_NORMAL, TextView_ReSized.this._spacingMult, TextView_ReSized.this._spacingAdd, true);
                if (TextView_ReSized.this.getMaxLines() != -1 && staticLayout.getLineCount() > TextView_ReSized.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) staticLayout.getHeight();
                int lineCount = staticLayout.getLineCount();
                int i2 = -1;
                for (int i3 = 0; i3 < lineCount; i3++) {
                    int lineEnd = staticLayout.getLineEnd(i3);
                    if (i3 < lineCount - 1 && lineEnd > 0 && !TextView_ReSized.this.isValidWordWrap(charSequence.charAt(lineEnd - 1), charSequence.charAt(lineEnd))) {
                        return 1;
                    }
                    if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                    }
                }
                this.textRect.right = (float) i2;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            return rectF.contains(this.textRect) ? -1 : 1;
        }
    }


    @RequiresApi(api = 26)
    public void setJustify() {
        setJustificationMode(1);
    }

    @Override
    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        set_TV_TextSize();
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        set_TV_TextSize();
    }

    @Override
    public void setTextSize(float f) {
        this._maxTextSize = f;
        set_TV_TextSize();
    }

    @Override
    public int getMaxLines() {
        return this._maxLines;
    }

    @Override
    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._maxLines = i;
        set_TV_TextSize();
    }

    @Override
    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        set_TV_TextSize();
    }

    @Override
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        set_TV_TextSize();
    }

    @Override
    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        set_TV_TextSize();
    }

    @Override
    public void setTextSize(int i, float f) {
        Resources system;
        Context context = getContext();
        if (context == null) {
            system = Resources.getSystem();
        } else {
            system = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, system.getDisplayMetrics());
        set_TV_TextSize();
    }

    @Override
    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTVTextSize(float f) {
        _minTextSize = f;
        set_TV_TextSize();
    }


    private void set_TV_TextSize() {
        if (this._initialized) {
            int i = (int) _minTextSize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this._widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this._widthLimit > 0) {
                this._paint = new TextPaint(getPaint());
                RectF rectF = this._availableSpaceRect;
                rectF.right = (float) this._widthLimit;
                rectF.bottom = (float) measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, (float) binaryTVSearch(i, (int) this._maxTextSize, this._Re_sizeTVTester, this._availableSpaceRect));
    }

    private int binaryTVSearch(int i, int i2, ReSizeTVTester reSizeTVTester, RectF rectF) {
        i2--;
        int i3 = i;
        while (i <= i2) {
            i3 = (i + i2) >>> 1;
            int onTestSize = reSizeTVTester.onTestSize(i3, rectF);
            if (onTestSize < 0) {
                int i4 = i3 + 1;
                i3 = i;
                i = i4;
            } else if (onTestSize <= 0) {
                return i3;
            } else {
                i3--;
                i2 = i3;
            }
        }
        return i3;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        set_TV_TextSize();
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            set_TV_TextSize();
        }
    }
}
