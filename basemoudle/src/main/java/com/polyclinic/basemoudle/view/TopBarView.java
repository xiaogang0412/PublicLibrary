package com.polyclinic.basemoudle.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polyclinic.basemoudle.R;
import com.polyclinic.library.utils.AppBarUtil;
import com.polyclinic.library.utils.DensityUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author Lxg
 * @create 2020/2/10
 * @Describe
 */
public class TopBarView extends RelativeLayout implements View.OnClickListener {

    private int backSrc;
    private float titleSize;
    private String title;
    private int titleColor;
    private float dimension;
    private boolean backVisable;
    private String fullSreen;
    private TextView textView;
    private String tvRight;
    private float tvRightSize;
    private int tvRightColor;
    private boolean ishShowRight;
    private int paddingRight = 10;
    private TextView right;
    private rightClickListener listener;
    private int backgroundColor;
    private ImageView imageView;

    public void setListener(rightClickListener listener) {
        this.listener = listener;
    }

    public TopBarView(Context context) {
        this(context, null);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarView);
        backVisable = typedArray.getBoolean(R.styleable.TopBarView_backvisiable, true);
        titleColor = typedArray.getColor(R.styleable.TopBarView_title_color, Color.parseColor(
                "#333333"));
        backSrc = typedArray.getResourceId(R.styleable.TopBarView_back_src,
                R.mipmap.img_kangyang_left_back);
        titleSize = typedArray.getDimension(R.styleable.TopBarView_title_size, 18);
        title = typedArray.getString(R.styleable.TopBarView_title);
        fullSreen = typedArray.getString(R.styleable.TopBarView_fullenscreen);
        dimension = typedArray.getDimension(R.styleable.TopBarView_margin_left,
                DensityUtils.dp2px(context, 20));
        backgroundColor = typedArray.getColor(R.styleable.TopBarView_topbarbackgroundcolor,
                Color.WHITE);
        /**
         * 右侧
         */
        tvRight = typedArray.getString(R.styleable.TopBarView_righttext);
        tvRightSize = typedArray.getDimensionPixelSize(R.styleable.TopBarView_righttextsize, 12);
        tvRightColor = typedArray.getColor(R.styleable.TopBarView_righttextcolor, Color.BLACK);
        ishShowRight = typedArray.getBoolean(R.styleable.TopBarView_isshowright, false);
        typedArray.recycle();
        init(context);
    }

    private void init(final Context context) {
        ImageView imageView = setLeft(context);
        setCenter(context);
        setGravity(Gravity.CENTER_VERTICAL);
        if (TextUtils.equals(fullSreen, "full")) {
            RelativeLayout relativeLayout = setFull(context, imageView);
            setRight(context, relativeLayout);
        } else {
            addView(imageView);
            addView(this.textView);
        }
        setBackgroundColor(backgroundColor);
    }

    /**
     * 设置右边
     *
     * @param context
     * @param relativeLayout
     */
    private void setRight(Context context, RelativeLayout relativeLayout) {
        if (ishShowRight) {
            LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
            rightParams.rightMargin = DensityUtils.dp2px(context,paddingRight);
            right = new TextView(context);
            right.setText(tvRight);
            right.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvRightSize);
            right.setTextColor(tvRightColor);
            right.setLayoutParams(rightParams);
            relativeLayout.addView(right);
            right.setOnClickListener(this);

        }
    }

    /**
     * 设置全屏
     *
     * @param context
     * @param imageView
     * @return
     */
    @NotNull
    private RelativeLayout setFull(Context context, ImageView imageView) {
        AppBarUtil.setStatusBarFullTransparent((Activity) context);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT,
                DensityUtils.dp2px(context, 44));
        layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.addView(imageView);
        relativeLayout.addView(this.textView);
        LinearLayout.LayoutParams layoutParams3 =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                DensityUtils.dp2px(context, 44) + DensityUtils.getAppBarHeight(context));
        layoutParams3.gravity = Gravity.BOTTOM;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.BOTTOM);
        linearLayout.setLayoutParams(layoutParams3);
        linearLayout.addView(relativeLayout);
        addView(linearLayout);
        return relativeLayout;
    }

    /**
     * 中间标题
     *
     * @param context
     */
    private void setCenter(Context context) {
        textView = new TextView(context);
        textView.setTextSize(titleSize);
        textView.setText(title);
        textView.setTextColor(titleColor);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) dimension;
        textView.setLayoutParams(layoutParams);
    }

    /**
     * 左边返回按钮
     *
     * @param context
     * @return
     */
    @NotNull
    private ImageView setLeft(final Context context) {
        imageView = new ImageView(context);
        imageView.setImageResource(backSrc);
        int padding = DensityUtils.dp2px(context,5);
        imageView.setPadding(padding, padding, padding, padding);
        LayoutParams layoutParamsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParamsLeft.addRule(CENTER_VERTICAL);
        layoutParamsLeft.leftMargin = (int) dimension;
        imageView.setLayoutParams(layoutParamsLeft);
        if (!backVisable) {
            imageView.setVisibility(GONE);
        }
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        return imageView;
    }

    public void setText(String title) {
        textView.setText(title);
    }

    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text))
            right.setText(text);
    }


    public void setIshShowRight(boolean ishShowRight) {
        right.setVisibility(ishShowRight ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick();
        }
    }

    public interface rightClickListener {
        void onClick();
    }

    public void setSingle(boolean single) {

    }
    public void setTextColor(int color){
        textView.setTextColor(color);
    }
    public void setLeftSrc(int src){
        imageView.setImageResource(src);

    }
    public void setRightVisiable(boolean visiable) {
        if (visiable) {
            right.setVisibility(VISIBLE);
        } else {
            right.setVisibility(GONE);
        }
    }

    public void setRightEnable(boolean enable) {
        right.setEnabled(enable);
    }
}
