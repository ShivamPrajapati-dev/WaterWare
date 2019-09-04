package com.shivamprajapati.waterware;

import android.content.Context;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class CustomTypeFace extends TypefaceSpan {

    Typeface typeface;
    Context context;

    CustomTypeFace(String family, Typeface typeface, Context context) {
        super(family);
        this.typeface=typeface;
        this.context=context;

    }

    @Override
    public void updateDrawState( TextPaint ds) {


        applyCustomTypeFace(ds,typeface);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint,typeface);

    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
