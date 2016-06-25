package example.com.networkthread;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/20 0020.
 * 请求封装类
 */
public abstract class Request<T> {
    private String url;//url
    private String method;//请求方式
    private ResponeCallback callback;//回调接口


    public Request(String url, String method, ResponeCallback callback) {
        this.url = url;
        this.method = method;
        this.callback = callback;
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

    protected void onRespone(T t){
        callback.data(t);
    }

    public void onError(Exception e){
        callback.error(e);
    }
    public static interface ResponeCallback<T> {
        public void error(Exception e);
        public void data(T t);

    }

    //使用post请求时需要传递参数 需要重写该方法
    public HashMap<String,String> getPOSTParams(){
        return null;
    }

    /*
    实现该方法回传不同的数据
     */
   public abstract void dispPatcher(byte[] respone);



}
