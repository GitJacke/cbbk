package example.com.networkthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class BitmapRequest extends Request<Bitmap> {
    public BitmapRequest(String url, String method, ResponeCallback callback) {
        super(url, method, callback);
    }

    @Override
    public void dispPatcher(byte[] respone) {
            onRespone(BitmapFactory.decodeByteArray(respone,0,respone.length));

    }

}
