package example.com.networkrespone;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class HttpClient {


    public static String getResponeByGET(Request request) throws Exception {
        if (TextUtils.isEmpty(request.getUrl())) {
            throw new IllegalArgumentException("url is null");
        }
        InputStream is = null;
        URL url = new URL(request.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        int rescode = connection.getResponseCode();

        if (rescode != 200) {
            throw new IllegalArgumentException("net responeException, code=" + rescode);
        }
        is = connection.getInputStream();
        int lenght = 0;
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            lenght = is.read(bytes);
            if (lenght < 0) {
                break;
            }
            baos.write(bytes, 0, lenght);
        }
        is.close();
        return baos.toString();


    }

    public static String getResponeByPOST(Request request) throws Exception {
        if (TextUtils.isEmpty(request.getUrl())) {
            throw new IllegalArgumentException("url is null");
        }
        InputStream is = null;
        OutputStream os=null;
        URL url = new URL(request.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setDoOutput(true);

        String str= getPostParams(request);
        if(str!=null){
            byte[] content=str.getBytes();
            connection.addRequestProperty("current",""+content.length);

            os=connection.getOutputStream();
            os.write(content);
            os.flush();
        }
        int rescode = connection.getResponseCode();

        if (rescode != 200) {
            throw new IllegalArgumentException("net responeException, code=" + rescode);
        }
        is = connection.getInputStream();
        int lenght = 0;
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            lenght = is.read(bytes);
            if (lenght < 0) {
                break;
            }
            baos.write(bytes, 0, lenght);
        }
        is.close();
        os.close();
        return baos.toString(Charset.defaultCharset().name());

    }


    public static String getPostParams(Request request) {
        HashMap<String, String> params = request.getRequestParams();
        if (params != null) {
            Iterator<Map.Entry<String,String>> iterator=params.entrySet().iterator();
           int i=0;
            StringBuffer buffer=new StringBuffer();
            while (iterator.hasNext()){
                if(i>0){
                    buffer.append("&");
                }
               String str= iterator.next().getKey()+"="+iterator.next().getValue();
                buffer.append(str);
                i++;
            }
            return buffer.toString();
        }

       return null;
    }
}
