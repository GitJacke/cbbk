package example.com.networkrespone;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class ResQueue {
    public static final int MAX_THREADS=3;//最大线程
    private BlockingQueue<Request> resQueue=new LinkedBlockingDeque<>();
    private  NetWorkThread[] netWorkThreads=new NetWorkThread[MAX_THREADS];

    public ResQueue(){
        initThreads();
    }


    public  void initThreads(){
        for (int i = 0; i < netWorkThreads.length; i++) {
            netWorkThreads[i]=new NetWorkThread(resQueue);
            netWorkThreads[i].start();
        }
    }


    public  void addRequestToQueue(Request request){
        resQueue.add(request);
    }

    public void  stopThreads(){
        for (int i = 0; i < netWorkThreads.length; i++) {
            netWorkThreads[i].setFlag(false);
        }
    }
}
