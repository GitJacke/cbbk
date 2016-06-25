package example.com.networkthread;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class NetWorkThread extends Thread{
    private static final String TAG = NetWorkThread.class.getSimpleName();
    //引用请求队列
    private BlockingQueue<Request> netWorkQueue;//??同步？？异步？？
    private boolean flag=true;//退出线程的标记
    public NetWorkThread(BlockingQueue<Request> netWorkQueue){
        this.netWorkQueue=netWorkQueue;
    }

    @Override
    public void run() {
        /*
        如果退出标记和中断标记成立
         */
        if(flag&&!isInterrupted()){

            while(true){
                try {
                    /**
                    从队列中取出消息对象
                     */
                    Request req=netWorkQueue.take();
                    byte[] repone= null;
                    try {
                        repone = getResponeByGET(req);
                        if(repone!=null){
                            req.dispPatcher(repone);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        req.onError(e);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    flag=false;
                }

            }

        }

    }

public void  setFlag(boolean flag){
    this.flag=flag;
}
    public static byte[] getRespone(Request request) throws Exception {
        if (TextUtils.isEmpty(request.getUrl())) {
            throw new IllegalArgumentException("url is null");
        }
        if("GET".equals(request.getMethod())){
            return  getResponeByGET(request);
        }
        if("POST".equals(request.getMethod())){
            return  getResponeByPOST(request);
        }
        return null;
    }

    /**
     * GET请求方法
     *
     * @param request 请求对象
     * @return 字符串
     */
    public static byte[] getResponeByGET(Request request) throws Exception {

        InputStream is = null;
        URL url = new URL(request.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);

        int rescode = conn.getResponseCode();
        if (rescode != 200) {
            throw new IllegalArgumentException("net ResponseCode error,code=" + rescode);
        }

        is = conn.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int leng = -1;
        byte[] bytes = new byte[1024];
        while (true) {
            leng = is.read(bytes);
            if (leng < 0) {
                break;
            }
            baos.write(bytes, 0, leng);
        }
        is.close();
        //Log.d(TAG, "getResponeByGET: "+baos.toString());
        return baos.toByteArray();
    }


    /**
     * POST请求
     *
     * @param request 请求对象
     * @return 字符串
     * @throws Exception
     */
    public static byte[] getResponeByPOST(Request request) throws Exception {
        if (TextUtils.isEmpty(request.getUrl())) {
            throw new IllegalArgumentException("url is null");
        }
        InputStream is = null;
        OutputStream os = null;
        URL url = new URL(request.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);//1.打开输出流
        /*
        2.向服务器以流的形式传送参数 (首先要设定参数的字节长度)
         */
        String str = getPOSTParams(request);
        byte[] content = null;
        if (str != null) {
            content = str.getBytes();
            //设置参数字节长度
            conn.setRequestProperty("conentlenght", content.length + "");
            //3写入数据
            os = conn.getOutputStream();
            os.write(content);
            os.flush();
        }
        os.close();

        int rescode = conn.getResponseCode();
        if (rescode != 200) {
            throw new IllegalArgumentException("net respone error code=" + rescode);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int leng = -1;
        byte[] bytes = new byte[1024];
        is = conn.getInputStream();
        while (true) {
            leng=is.read(bytes);
            if(leng<0){
                break;
            }
            baos.write(bytes,0,leng);
        }
        is.close();
        return baos.toByteArray();
    }


    public static String getPOSTParams(Request request) {
        HashMap<String, String> params = request.getPOSTParams();
        if (params != null) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            StringBuffer buffer = new StringBuffer();
            int i = 0;
            while (iterator.hasNext()) {
                if (i > 0) {
                    buffer.append("&");
                }
                buffer.append(iterator.next().getKey() + "=" + iterator.next().getValue());
                i++;
            }
            return buffer.toString();
        }
        return null;
    }


}
