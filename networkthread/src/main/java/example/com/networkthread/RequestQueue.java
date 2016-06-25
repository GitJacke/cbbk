package example.com.networkthread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class RequestQueue {
    public static final  int MAX_THREAD=6;//三线程
    private static final String TAG =  RequestQueue.class.getSimpleName();
    private BlockingQueue<Request> requesQueue=new LinkedBlockingDeque<Request>();
    private NetWorkThread[] threads=new NetWorkThread[MAX_THREAD];

    /**
     * 初始化并启动线程组
     */
    public void initThreads(){
        for (int i = 0; i < threads.length; i++) {
            threads[i]=new NetWorkThread(requesQueue);
            //启动
            threads[i].start();

        }

    }

    public RequestQueue(){
        initThreads();
    }
    /**
    添加请求到队列中
     */
    public void addRequestToQueue(Request request){
        requesQueue.add(request);
    }


    /**
     * 停止线程
     */
    public void stopThreads(){
        for (int i = 0; i < threads.length; i++) {
            //停止
            threads[i].setFlag(false);
            threads[i].isInterrupted();
        }
        Log.i(TAG, "stopThreads: OK");
    }




}
