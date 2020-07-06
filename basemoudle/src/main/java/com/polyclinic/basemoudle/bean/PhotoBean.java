package com.polyclinic.basemoudle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lxg
 * @create 2020/3/6
 * @Describe
 */
public class PhotoBean implements Serializable {
    private List<String> photos;
    private int postion;

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
