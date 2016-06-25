package example.com.networkthread;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class HttpHelper {

    //饱汉式单例模式
    private static RequestQueue instance;
    private static RequestQueue getInstance(){
        if(instance==null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new RequestQueue();
                }
            }
        }
        return instance;
    }

    public static void addRequest(Request request){
        getInstance();
        instance.addRequestToQueue(request);
    }
    public static void stop(){
        instance.stopThreads();
    }
}
