package com.polyclinic.basemoudle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Lxg
 * @create 2020/3/13
 * @Describe
 */
public class CircleTextView extends AppCompatTextView {
    private Paint paintText;

    public CircleTextView(Context context) {
        this(context, null);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, paintText);

    }
}
