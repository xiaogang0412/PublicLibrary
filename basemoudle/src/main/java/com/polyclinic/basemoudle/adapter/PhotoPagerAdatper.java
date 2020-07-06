package com.polyclinic.basemoudle.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * @author Lxg
 * @create 2020/3/6
 * @Describe
 */
public class PhotoPagerAdatper extends PagerAdapter {
    private List<PhotoView> photoViews;

    public PhotoPagerAdatper(List<PhotoView> photoViews) {
        this.photoViews = photoViews;
    }

    @Override
    public int getCount() {
        return photoViews == null ? 0 : photoViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(photoViews.get(position));
        return photoViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(photoViews.get(position));
    }
}
