package example.com.cbk.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import example.com.cbk.R;
import example.com.cbk.bean.Info;
import example.com.networkthread.BitmapHttpHelper;
import example.com.networkthread.BitmapRequest;
import example.com.networkthread.Request;

/**
 * Created by Administrator on 2016/6/22.
 */
public class FragmentListAdapter extends BaseAdapter {
    private static final String TAG =FragmentListAdapter.class.getSimpleName() ;
    List<Info> infos;

    public FragmentListAdapter(List<Info> infos) {
        this.infos = infos;
    }
    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Info getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.detail_list_item, null);
            vHolder = new ViewHolder();
            vHolder.item_iV = (ImageView) convertView.findViewById(R.id.dt_item_iV);
            vHolder.item_desptV = (TextView) convertView.findViewById(R.id.dt_item_despt);
            vHolder.item_timeTv = (TextView) convertView.findViewById(R.id.dt_item_timeTv);
            vHolder.itemt_rectV = (TextView) convertView.findViewById(R.id.dt_item_reaC);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        Info info = getItem(position);
        long time = info.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        vHolder.item_timeTv.setText(sdf.format(time));
        vHolder.item_desptV.setText(info.getDescription());
        vHolder.itemt_rectV.setText("阅读数:" + info.getRcount());
        final String url = "http://tnfs.tngou.net/image" + info.getImg() + "_120x120";
        vHolder.item_iV.setTag(url);
        BitmapRequest request = new BitmapRequest(url, "GET", new Request.ResponeCallback<Bitmap>() {
            @Override
            public void error(Exception e) {
                Log.i(TAG, "error: "+e.getMessage());
            }
            int i=1;
            @Override
            public void data(final Bitmap bitmap) {
                    Log.i(TAG, "bitmap----------->: " + bitmap);
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        if(vHolder.item_iV.getTag().equals(url)&&bitmap!=null){
                            Log.i(TAG, "bitmap---->: "+bitmap);
                            vHolder.item_iV.setImageBitmap(bitmap);

                        }
                    }
                });

            }

        });
        BitmapHttpHelper.addRequest(request);
        return convertView;
    }
    class ViewHolder {
        private ImageView item_iV;
        private TextView item_desptV;
        private TextView itemt_rectV;
        private TextView item_timeTv;

    }


}
