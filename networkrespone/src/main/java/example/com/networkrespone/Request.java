package example.com.networkrespone;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class Request {
    private String url;
    private String method;
    private GetStringCallback callback;

    private ResQueue resQueue;
    public Request(String url, String method, GetStringCallback callback,ResQueue resQueue) {
        this.url = url;
        this.method = method;
        this.callback = callback;
        this.resQueue=resQueue;
        resQueue.addRequestToQueue(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public GetStringCallback getCallback() {
        return callback;
    }

    public void setCallback(GetStringCallback callback) {
        this.callback = callback;
    }

    public static interface GetStringCallback{
       public abstract void data(String str);
        public abstract void error(Exception e);
    }

    public HashMap<String,String> getRequestParams(){
        return null;
    }
}
