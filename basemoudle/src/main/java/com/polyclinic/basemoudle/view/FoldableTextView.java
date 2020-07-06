package com.polyclinic.basemoudle.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.polyclinic.basemoudle.R;

/**
 * 可折叠 TextView
 */
public class FoldableTextView extends AppCompatTextView implements View.OnTouchListener {

    // 默认配置
    public static final int STATE_SHRINK = 0;
    public static final int STATE_EXPAND = 1;
    private static final String ELLIPSIS_HINT = "..";
    private static final int MAX_LINE_IN_SHRINK_DEFAULT = 3;
    private static final int EXPAND_HINT_COLOR = 0xaa00ffff;
    private static final int SHRINK_HINT_COLOR = 0xaa00ffff;
    private static final String EXPAND_HINT_DEFAULT = "展开";
    private static final String SHRINK_HINT_DEFAULT = "收起";


    private String mEllipsisHint = ELLIPSIS_HINT;
    private String mExpandHint = EXPAND_HINT_DEFAULT;
    private String mShrinkHint = SHRINK_HINT_DEFAULT;
    private String gapToExpandHint = " ";
    private String gapToShrinkHint = " ";
    private boolean mIsExpandHintShow = true;
    private boolean mIsShrinkHintShow = true;

    private int mMaxLineInShrink = MAX_LINE_IN_SHRINK_DEFAULT;
    private int mExpandHintColor = EXPAND_HINT_COLOR;
    private int mShrinkHintColor = SHRINK_HINT_COLOR;
    private int mCurrState;

    private NewClickableSpan mNewClickableSpan;
    private BufferType mBufferType = BufferType.NORMAL;
    private Layout mLayout;
    private CharSequence mOriginText;

    public FoldableTextView(Context context) {
        this(context, null);
    }

    public FoldableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FoldableTextView);
        if (typedArray == null) {
            return;
        }
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.FoldableTextView_maxLineInShrink) {
                mMaxLineInShrink = typedArray.getInteger(attr, MAX_LINE_IN_SHRINK_DEFAULT);
            } else if (attr == R.styleable.FoldableTextView_ellipsisHint) {
                mEllipsisHint = typedArray.getString(attr);
            } else if (attr == R.styleable.FoldableTextView_expandHint) {
                mExpandHint = typedArray.getString(attr);
            } else if (attr == R.styleable.FoldableTextView_shrinkHint) {
                mShrinkHint = typedArray.getString(attr);
            } else if (attr == R.styleable.FoldableTextView_isExpandHintShow) {
                mIsExpandHintShow = typedArray.getBoolean(attr, true);
            } else if (attr == R.styleable.FoldableTextView_isShrinkHintShow) {
                mIsShrinkHintShow = typedArray.getBoolean(attr, true);
            } else if (attr == R.styleable.FoldableTextView_expandHintColor) {
                mExpandHintColor = typedArray.getInteger(attr, EXPAND_HINT_COLOR);
            } else if (attr == R.styleable.FoldableTextView_shrinkHintColor) {
                mShrinkHintColor = typedArray.getInteger(attr, SHRINK_HINT_COLOR);
            } else if (attr == R.styleable.FoldableTextView_textState) {
                mCurrState = typedArray.getInteger(attr, STATE_SHRINK);
            } else if (attr == R.styleable.FoldableTextView_gapToExpandHint) {
                gapToExpandHint = typedArray.getString(attr);
            } else if (attr == R.styleable.FoldableTextView_gapToShrinkHint) {
                gapToShrinkHint = typedArray.getString(attr);
            }
        }
        typedArray.recycle();
    }

    private void init() {
        mNewClickableSpan = new NewClickableSpan();
        setMovementMethod(new LinkMovementMethod());
        if (TextUtils.isEmpty(mEllipsisHint)) {
            mEllipsisHint = ELLIPSIS_HINT;
        }
        if (TextUtils.isEmpty(mExpandHint)) {
            mExpandHint = "展开";
        }
        if (TextUtils.isEmpty(mShrinkHint)) {
            mShrinkHint = "收起";
        }
        this.setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setTextInternal(getFinallyText(), mBufferType);
    }

    /**
     * 获取文案
     * @return
     */
    private CharSequence getFinallyText() {
        if (TextUtils.isEmpty(mOriginText)) {
            return mOriginText;
        }

        int layoutWidth = 0;
        mLayout = getLayout();
        if (mLayout != null) {
            layoutWidth = mLayout.getWidth();
        }
        if (layoutWidth <= 0) {
            if (getWidth() == 0) {
                return mOriginText;
            } else {
                layoutWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            }
        }
        TextPaint textPaint = getPaint();
        int textLineCount = -1;
        mLayout = new DynamicLayout(mOriginText, textPaint, layoutWidth,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        textLineCount = mLayout.getLineCount();

        if (mCurrState == STATE_SHRINK) {
            return getTextInShrink(textPaint, textLineCount);
        } else if (mCurrState == STATE_EXPAND) {
            return getTextInExpand(textLineCount);
        }
        return mOriginText;
    }

    /**
     * 获取展开之后的文案
     * @param textLineCount
     * @return
     */
    private CharSequence getTextInExpand(int textLineCount) {
        if (!mIsShrinkHintShow) {
            return mOriginText;
        }

        if (textLineCount <= mMaxLineInShrink) {
            return mOriginText;
        }

        SpannableStringBuilder ssbExpand = new SpannableStringBuilder(mOriginText)
                .append(gapToShrinkHint).append(mShrinkHint);
        ssbExpand.setSpan(mNewClickableSpan, ssbExpand.length() - getLengthOfString(mShrinkHint),
                ssbExpand.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Log.d("lichaojun", "getTextInExpand: " + ssbExpand.toString());
        return ssbExpand;
    }

    /**
     * 获取折叠之后的文案
     * @param textPaint
     * @param textLineCount
     * @return
     */
    private CharSequence getTextInShrink(TextPaint textPaint, int textLineCount) {

        if (textLineCount <= mMaxLineInShrink) {
            return mOriginText;
        }
        int indexEnd = getValidLayout().getLineEnd(mMaxLineInShrink - 1);
        int indexStart = getValidLayout().getLineStart(mMaxLineInShrink - 1);
        int indexEndTrimmed = indexEnd
                - getLengthOfString(mEllipsisHint)
                - (mIsExpandHintShow ? getLengthOfString(mExpandHint) + getLengthOfString(gapToExpandHint) : 0);
        if (indexEndTrimmed <= 0) {
            return mOriginText.subSequence(0, indexEnd);
        }

        int remainWidth = getValidLayout().getWidth() -
                (int) (textPaint.measureText(mOriginText.subSequence(indexStart, indexEndTrimmed).toString()) + 0.5);
        float widthTailReplaced = textPaint.measureText(getContentOfString(mEllipsisHint)
                + (mIsExpandHintShow ? (getContentOfString(mExpandHint) + getContentOfString(gapToExpandHint)) : ""));

        int indexEndTrimmedRevised = indexEndTrimmed;
        if (remainWidth > widthTailReplaced) {
            int extraOffset = 0;
            int extraWidth = 0;
            while (remainWidth > widthTailReplaced + extraWidth) {
                extraOffset++;
                if (indexEndTrimmed + extraOffset <= mOriginText.length()) {
                    extraWidth = (int) (textPaint.measureText(
                            mOriginText.subSequence(indexEndTrimmed, indexEndTrimmed + extraOffset).toString()) + 0.5);
                } else {
                    break;
                }
            }
            indexEndTrimmedRevised += extraOffset - 1;
        } else {
            int extraOffset = 0;
            int extraWidth = 0;
            while (remainWidth + extraWidth < widthTailReplaced) {
                extraOffset--;
                if (indexEndTrimmed + extraOffset > indexStart) {
                    extraWidth = (int) (textPaint.measureText(mOriginText.subSequence(indexEndTrimmed + extraOffset,
                            indexEndTrimmed).toString()) + 0.5);
                } else {
                    break;
                }
            }
            indexEndTrimmedRevised += extraOffset;
        }

        SpannableStringBuilder ssbShrink = new SpannableStringBuilder(mOriginText, 0, indexEndTrimmedRevised)
                .append(mEllipsisHint);
        if (mIsExpandHintShow) {
            ssbShrink.append(getContentOfString(gapToExpandHint) + getContentOfString(mExpandHint));
            ssbShrink.setSpan(mNewClickableSpan, ssbShrink.length() - getLengthOfString(mExpandHint),
                    ssbShrink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ssbShrink;
    }

    private Layout getValidLayout() {
        return mLayout != null ? mLayout : getLayout();
    }

    private void switchStateMode() {
        switch (mCurrState) {
            case STATE_SHRINK:
                mCurrState = STATE_EXPAND;
                break;
            case STATE_EXPAND:
                mCurrState = STATE_SHRINK;
                break;
        }
        setTextInternal(getFinallyText(), mBufferType);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOriginText = text;
        mBufferType = type;
        setTextInternal(getFinallyText(), type);
    }

    private void setTextInternal(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    private int getLengthOfString(String str) {
        return str != null ? str.length() : 0;
    }

    private String getContentOfString(String str) {
        return str != null ? str : "";
    }

    private class NewClickableSpan extends ClickableSpan {

        /**
         * Spannable区域的点击事件
         * @param widget
         */
        @Override
        public void onClick(View widget) {
            switchStateMode();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            switch (mCurrState) {
                case STATE_SHRINK:
                    ds.setColor(mExpandHintColor);
                    break;
                case STATE_EXPAND:
                    ds.setColor(mShrinkHintColor);
                    break;
            }
            ds.setUnderlineText(false);
        }
    }

    /**
     * 实现该方法是为了解决 spannable文案区域的点击事件与onClickListener事件冲突的问题
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        TextView tv = (TextView) v;
        CharSequence text = tv.getText();
        if (text instanceof SpannableString) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= tv.getTotalPaddingLeft();
                y -= tv.getTotalPaddingTop();

                x += tv.getScrollX();
                y += tv.getScrollY();

                Layout layout = tv.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] links = ((SpannableString)text).getSpans(off, off, ClickableSpan.class);

                if (links.length != 0) {
                    links[0].onClick(tv);
                } else {
                    // TODO TextView的点击
                    if (mOnTextClickListener != null) {
                        mOnTextClickListener.onTextClick(tv);
                    }
                }
            }
        }
        return true;
    }

    private OnTextClickListener mOnTextClickListener;

    public void setOnTextClickListener(OnTextClickListener listener) {
        this.mOnTextClickListener = listener;
    }

    public interface OnTextClickListener {
        void onTextClick(View view);
    }
}
