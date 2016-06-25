package example.com.cbk.bean;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class Detail {

    private String description;
    private int fcount;
    private long id;
    private String imgurl;
    private String keywords;
    private int loreclass;
    private int rcount;
    //private boolean status;
    private long time;
    private String title;
    private String detailUrl;
    private String cotentText;//message
    public String getCotentText() {
        return cotentText;
    }

    public void setCotentText(String cotentText) {
        this.cotentText = cotentText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getLoreclass() {
        return loreclass;
    }

    public void setLoreclass(int loreclass) {
        this.loreclass = loreclass;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "detailUrl='" + cotentText + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
