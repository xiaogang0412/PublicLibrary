package com.polyclinic.basemoudle.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Lxg
 * @create 2020/6/10
 * @Describe
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

private int spanCount; //列数
private int spacing; //间隔
private boolean includeEdge; //是否包含边缘

public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        }

@Override
public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
        outRect.left = spacing - column * spacing / spanCount;
        outRect.right = (column + 1) * spacing / spanCount;

        if (position < spanCount) {
        outRect.top = spacing;
        }
        outRect.bottom = spacing;
        } else {
        outRect.left = column * spacing / spanCount;
        if (position >= spanCount) {
        outRect.top = spacing;
        }
        }
        }
}
