package example.com.cbk.bean;

/**
 * Created by Administrator on 2016/6/21.
 * www.tngou.net/api/lore/classify
 */
public class Title {
        private int id;
        private String title;
        public  Title(int id,String title){
            this.title=title;
            this.id=id;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Title() {
    }

}
