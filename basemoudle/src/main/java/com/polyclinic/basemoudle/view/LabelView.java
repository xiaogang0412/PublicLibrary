package com.polyclinic.basemoudle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.polyclinic.basemoudle.R;


/**
 * @author Lxg
 * @create 2020/2/10
 * @Describe
 */
public class LabelView extends LinearLayout {
    private String text;
    private float text_size;
    private int text_color;
    private int imageSrc;
    private float marginTop;
    private ImageView imageView;
    private TextView textView;
    private float width;
    private float height;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
        text = typedArray.getString(R.styleable.LabelView_text);
        text_size = typedArray.getDimension(R.styleable.LabelView_text_size, 12);
        imageSrc = typedArray.getResourceId(R.styleable.LabelView_image_src, 0);
        text_color = typedArray.getColor(R.styleable.LabelView_text_color, Color.parseColor("#000000"));
        marginTop = typedArray.getDimension(R.styleable.LabelView_padding_top, 0);
        width = typedArray.getDimension(R.styleable.LabelView_image_width, 12);
        height = typedArray.getDimension(R.styleable.LabelView_image_height, 12);
        init(context);
    }

    public void init(Context context) {
        imageView = new ImageView(context);
        imageView.setImageResource(imageSrc);
        LayoutParams layoutParams = new LayoutParams((int) width, (int) height);
        imageView.setLayoutParams(layoutParams);
        textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(text_color);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) marginTop;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
        textView.setLayoutParams(params);
        addView(imageView);
        addView(textView);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setScaleType() {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public ImageView getImageView() {
        return imageView;
    }
    public void setImage(int id){
        imageView.setImageResource(id);
    }
    public String getText(){
        return textView.getText().toString();
    }

}
