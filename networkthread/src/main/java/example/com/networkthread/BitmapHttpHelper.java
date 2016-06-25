package example.com.networkthread;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class BitmapHttpHelper {

    //饱汉式单例模式
    private static BitmapReQueue instance;
    private static BitmapReQueue getInstance(){
        if(instance==null) {
            synchronized (BitmapHttpHelper.class) {
                if (instance == null) {
                    instance = new BitmapReQueue();
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
