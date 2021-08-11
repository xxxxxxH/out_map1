package net.entiy;

import java.io.Serializable;

/**
 * Created by heping on 2018/7/10.
 */

public class BigPlaceEntity implements Serializable {

    private String imageUrl;

    private String key;

    private String title;

    private String desc;
    private Double lat;
    private Double lng;

    private String panoid;

    private Boolean isFife = false;


    public Boolean getIsFife() {
        return isFife;
    }

    public void setIsFife(Boolean isFife) {
        this.isFife = isFife;
    }

    public String getPanoid() {
        return panoid;
    }

    public void setPanoid(String panoid) {
        this.panoid = panoid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
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

//    public List<SmallPlace> getSmallPlaceList() {
//        return smallPlaceList;
//    }
//
//    public void setSmallPlaceList(List<SmallPlace> smallPlaceList) {
//        this.smallPlaceList = smallPlaceList;
//    }
}
