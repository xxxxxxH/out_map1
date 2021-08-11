package net.entiy;

import java.io.Serializable;

/**
 * Created by heping on 2018/7/10.
 */

public class SmallPlaceEntity implements Serializable {

    private String imageUrl;

    private String title;

    private String desc;

    private double lat;

    private double lng;

    private String pannoId;

    public String getPannoId() {
        return pannoId;
    }

    public void setPannoId(String pannoId) {
        this.pannoId = pannoId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
