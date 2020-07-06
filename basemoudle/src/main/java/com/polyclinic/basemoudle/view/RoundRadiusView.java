package com.polyclinic.basemoudle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.polyclinic.basemoudle.R;

/**
 * @author Lxg
 * @create 2020/5/22
 * @Describe
 */
public class RoundRadiusView extends View {
    private Paint paint;
    private String text;
    private int backgroundColor;
    private float textSize;
    private float radius;
    private int textColor;
    private Paint paintText;
    private boolean stroke;

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public String getText() {
        return text;
    }

    public RoundRadiusView(Context context) {
        this(context, null);
    }

    public RoundRadiusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRadiusView(Context context, @
            Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRadiusView);
        text = typedArray.getString(R.styleable.RoundRadiusView_radius_text);
        backgroundColor = typedArray.getColor(R.styleable.RoundRadiusView_backgroundcolor,
                Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.RoundRadiusView_radius_textsize,
                12);
        radius = typedArray.getDimension(R.styleable.RoundRadiusView_radius, 0);
        textColor = typedArray.getColor(R.styleable.RoundRadiusView_radius_textcolor, Color.BLACK);
        stroke = typedArray.getBoolean(R.styleable.RoundRadiusView_stroke, false);
        typedArray.recycle();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        if (!stroke) {
            paint.setStrokeWidth(getHeight());
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
        }
        paintText = new Paint();
        paintText.setColor(textColor);
        paintText.setStrokeWidth(6);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setAntiAlias(true);
        paintText.setTextSize(textSize);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF f = new RectF(paint.getStrokeWidth(), paint.getStrokeWidth(), getWidth() -
                paint.getStrokeWidth(), getHeight() - paint.getStrokeWidth());
        canvas.drawRoundRect(f, radius, radius, paint);
        Rect rect = new Rect();
        paintText.getTextBounds(text, 0, text.length(), rect);
        int i = (rect.top + rect.bottom) / 2;
        canvas.drawText(text, (getWidth() / 2) - (rect.width() / 2) - 6, getHeight() / 2 - i,
                paintText);
    }

    public void setBackground(int color) {
        this.backgroundColor = color;
        paint.setColor(backgroundColor);
        postInvalidate();
    }
}
