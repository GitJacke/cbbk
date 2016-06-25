package example.com.cbk.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/21.
 * http://www.tngou.net/api/lore/list
 */
 public  class  Info implements Parcelable{
    private String title;//资讯标题
    private int infoclass;//分类
    private String img;//图片
    private String description;//描述
    private String keywords;//关键字
    private int count ;//访问次数
    private int fcount;//收藏数
    private int rcount;//评论读数
    private long time;//need
    private long id;//详情需要该id

    protected Info(Parcel in) {
        img = in.readString();
        description = in.readString();
        rcount = in.readInt();
        time = in.readLong();
        id=in.readLong();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInfoclass() {
        return infoclass;
    }

    public void setInfoclass(int infoclass) {
        this.infoclass = infoclass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

   /* public Info(String title, int infoclass, String img, String description, String keywords,
                int count, int fcount, int rcount, long time) {
        this.title = title;
        this.infoclass = infoclass;
        this.img = img;
        this.description = description;
        this.keywords = keywords;
        this.count = count;
        this.fcount = fcount;
        this.rcount = rcount;
        this.time = time;
    }*/

    public Info() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(description);
        dest.writeInt(rcount);
        dest.writeLong(time);
        dest.writeLong(id);
    }
}