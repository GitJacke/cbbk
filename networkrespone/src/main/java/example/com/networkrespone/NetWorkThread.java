package example.com.networkrespone;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class NetWorkThread extends Thread {
    private BlockingQueue<Request> requests;

    public NetWorkThread(BlockingQueue<Request> resQueue) {
        this.requests = resQueue;
    }

    private boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag && !isInterrupted()) {

            while (true) {
            try {
                Request request = requests.take();
                try {
                    String str = HttpClient.getResponeByGET(request);
                    if (str != null) {
                        request.getCallback().data(str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.getCallback().error(e);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
}
