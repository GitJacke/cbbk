package example.com.cbk.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import example.com.cbk.R;
import example.com.cbk.bean.Detail;
import example.com.cbk.bean.Info;
import example.com.networkthread.BitmapRequest;
import example.com.networkthread.HttpHelper;
import example.com.networkthread.Request;

/**
 * Created by Administrator on 2016/6/22.
 */
public class CollectionListAdapter extends BaseAdapter {
    private static final String TAG =CollectionListAdapter.class.getSimpleName() ;
    List<Detail> infos;

    public CollectionListAdapter(List<Detail> infos) {
        this.infos = infos;
    }
    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Detail getItem(int position) {
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
        Detail info = getItem(position);
        long time = info.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        vHolder.item_iV.setImageResource(R.mipmap.ic_logo);
        vHolder.item_timeTv.setText(sdf.format(time));
        vHolder.item_desptV.setText(Html.fromHtml(info.getDescription()));
        vHolder.itemt_rectV.setText("阅读数:" + info.getRcount());
        return convertView;
    }
    class ViewHolder {
         ImageView item_iV;
        private TextView item_desptV;
        private TextView itemt_rectV;
        private TextView item_timeTv;

    }

}
