package net.entiy;

public class NearbyInfo {
    private int resId;
    private String keyword;

    public NearbyInfo(int resId,String keyword){
        this.resId = resId;
        this.keyword = keyword;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
