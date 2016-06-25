package example.com.networkthread;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class StringRequest extends Request<String> {
    public StringRequest(String url, String method, ResponeCallback callback) {
        super(url, method, callback);
    }

    @Override
    public void dispPatcher(byte[] respone) {
            onRespone(new String(respone));

    }

}
