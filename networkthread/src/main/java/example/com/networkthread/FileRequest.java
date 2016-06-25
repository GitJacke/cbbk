package example.com.networkthread;

import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/6/23.
 */
public class FileRequest extends Request<File> {
    public FileRequest(String url, String method, ResponeCallback callback) {
        super(url, method, callback);
    }

    @Override
    public void dispPatcher(byte[] respone) {
        File file = new File(Environment.getExternalStorageDirectory(), "ak");
        if(respone!=null&&respone.length!=0) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(respone, 0, respone.length);
                fileOutputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        onRespone(file);
    }
}
