package example.com.cbk.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import example.com.cbk.R;
import example.com.cbk.bean.Detail;

/**
 * Created by Administrator on 2016/6/22.
 */
public class RecordListAdapter extends BaseAdapter {
    private static final String TAG =RecordListAdapter.class.getSimpleName() ;
    List<Detail> infos;

    public RecordListAdapter(List<Detail> infos) {
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
            convertView = View.inflate(parent.getContext(), R.layout.recorde_list_item, null);
            vHolder = new ViewHolder();
            vHolder.item_titletV = (TextView) convertView.findViewById(R.id.record_acty_list_title);
            vHolder.itemt_urlV = (TextView) convertView.findViewById(R.id.record_acty_list_url);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        Detail info = getItem(position);
        vHolder.item_titletV.setText(info.getTitle());
        vHolder.itemt_urlV.setText(Html.fromHtml(info.getDetailUrl()));
        return convertView;
    }
    class ViewHolder {
        private TextView item_titletV;
        private TextView itemt_urlV;


    }

}
