package com.polyclinic.basemoudle.activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.polyclinic.basemoudle.R;
import com.polyclinic.basemoudle.adapter.PhotoPagerAdatper;
import com.polyclinic.basemoudle.bean.PhotoBean;
import com.polyclinic.library.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends BaseActivity {
    ViewPager pager;
    TextView tvPhotoCurrent;
    private PhotoPagerAdatper adatper;
    private List<PhotoView> photoViews = new ArrayList<>();
    private List<String> photos;
    private PhotoBean photoBean;
    private ImageView ivBack;
    Serializable serializable;
    private Serializable photo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @Override
    public void initView() {
        tvPhotoCurrent=findViewById(R.id.tv_photo_current);
       pager= findViewById(R.id.pager);
       ivBack=findViewById(R.id.iv_back);
        serializable = extras.getSerializable("photo");
        setFullScreen();
        photoBean = (PhotoBean) serializable;
        photos = photoBean.getPhotos();
        int postion = photoBean.getPostion();
        tvPhotoCurrent.setText((photoBean.getPostion() + 1) + "/" + photos.size());
        for (int i = 0; i < photos.size(); i++) {
            PhotoView photoView = new PhotoView(this);
            Glide.with(this).load(photos.get(i)).into(photoView);
            Log.i("wweew", photos.get(i));
            photoViews.add(photoView);

        }
        adatper = new PhotoPagerAdatper(photoViews);
        pager.setAdapter(adatper);
        pager.setCurrentItem(photoBean.getPostion());

    }

    @Override
    public void loadData() {

    }

    @Override
    public void setListener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tvPhotoCurrent.setText((i + 1) + "/" + photos.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
