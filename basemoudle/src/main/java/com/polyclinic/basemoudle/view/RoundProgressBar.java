package com.polyclinic.basemoudle.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.polyclinic.basemoudle.R;

import static java.lang.Math.PI;

/**
 * @author Lxg
 * @create 2020/5/15
 * @Describe
 */
public class RoundProgressBar extends View implements
        ValueAnimator.AnimatorUpdateListener {
    private Paint paintCircle;
    private int cricleColor;
    private float cricleWidth;
    private float radius;
    private Paint paintInsideCirlce;
    private int startColor;
    private int endColor;
    private float widht;
    private Paint paintSmall;
    private int[] colorlist = {};
    private int maxProgress;
    private int currentProgress;
    private int angleValue;
    private ValueAnimator animator;

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        postInvalidate();
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public void setColorlist(int[] colorlist) {
        this.colorlist = colorlist;
        postInvalidate();
    }

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        cricleColor = typedArray.getColor(R.styleable.RoundProgressBar_roundbar_ciclecolor,
                Color.GRAY);
        cricleWidth = typedArray.getDimension(R.styleable.RoundProgressBar_roundbar_width, 20);
        startColor = typedArray.getColor(R.styleable.RoundProgressBar_roundbar_startcolor,
                Color.BLUE);
        endColor = typedArray.getColor(R.styleable.RoundProgressBar_roundbar_endcolor, Color.BLACK);
        typedArray.recycle();

        init();

    }

    private void init() {
        /**
         * 外部圆环
         */
        paintCircle = new Paint();
        paintCircle.setStrokeWidth(cricleWidth);
        paintCircle.setColor(cricleColor);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        /**
         *
         */
        paintInsideCirlce = new Paint();
        paintInsideCirlce.setAntiAlias(true);
        paintInsideCirlce.setStrokeWidth(cricleWidth);
        paintInsideCirlce.setColor(startColor);
        paintInsideCirlce.setStyle(Paint.Style.STROKE);
        paintInsideCirlce.setStrokeCap(Paint.Cap.ROUND);

        /**
         * 小圆点
         */
        paintSmall = new Paint();
        paintSmall.setColor(Color.WHITE);
        paintSmall.setStrokeWidth(10);
        paintSmall.setAntiAlias(true);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widht = cricleWidth / 2;
        radius = getWidth() / 2 - widht;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCicleView(canvas);
        drawInsideCircle(canvas);

        drawSmall(canvas);
    }

    private void drawSmall(Canvas canvas) {
        float x1 = (float) (getWidth() / 2 + radius * Math.cos(angleValue * PI / 180));
        float y1 = (float) (getHeight() / 2 + radius * Math.sin(angleValue * PI / 180));
        canvas.drawCircle(x1, y1, 18, paintSmall);
    }

    private void drawInsideCircle(Canvas canvas) {
        RectF rectF = new RectF(widht, widht, getWidth() - widht,
                getHeight() - widht);
        float[] positionArray = new float[]{0.2f, 0.4f, 0.6f, 0.8f, 1.0f};
        if(positionArray.length!=colorlist.length){
            return;
        }
        SweepGradient sweepGradient = new SweepGradient(rectF.centerX(), rectF.centerY() - 20,
                colorlist, positionArray);
        paintInsideCirlce.setShader(sweepGradient);
        canvas.drawArc(rectF, 0, angleValue, false, paintInsideCirlce);

    }

    private void drawCicleView(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paintCircle);
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int animatedValue = (int) animation.getAnimatedValue();
        angleValue = animatedValue;
        postInvalidate();
    }

    public void startAnimator() {
        animator = ValueAnimator.ofInt(0, (int) ((360f / maxProgress) * currentProgress));
        animator.setDuration(1000);
        animator.addUpdateListener(this);
        animator.start();
    }
}
