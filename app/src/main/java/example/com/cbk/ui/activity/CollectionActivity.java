package example.com.cbk.ui.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.com.cbk.R;
import example.com.cbk.adapter.CollectionListAdapter;
import example.com.cbk.bean.Detail;
import example.com.cbk.utils.database.DBhelper;
import example.com.cbk.utils.database.MyDBHelper;

public class CollectionActivity extends FragmentActivity {

    private static final String TAG = CollectionActivity.class.getSimpleName();
    private Toolbar hom_Tb;
    private List<Detail> details = new ArrayList<>();
    private DBhelper dBhelper;
    private CollectionListAdapter adapter;
    private ListView cllectLv;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_list);
        initView();
        initData();
    }

    private void initData() {

      thread = new Thread() {
            @Override
            public void run() {
                if (getDetails() != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            details.clear();
                            details.addAll(getDetails());
                            if (adapter == null) {
                                adapter = new CollectionListAdapter(details);
                                cllectLv.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        };
        thread.start();
    }

    private void initView() {
        hom_Tb = (Toolbar) findViewById(R.id.collect_acty_toolbar);
        View view = View.inflate(getApplicationContext(), R.layout.toolbar_layout, null);

        view.findViewById(R.id.tb_share).setVisibility(View.GONE);
        view.findViewById(R.id.tb_plus).setVisibility(View.GONE);
        view.findViewById(R.id.tb_more).setVisibility(View.GONE);
        hom_Tb.addView(view);
        cllectLv = (ListView) findViewById(R.id.centent_Lv);

        cllectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (details != null && details.size() != 0) {
                    String url = details.get(position).getDetailUrl();
                    long classId = Long.parseLong(url.substring(url.lastIndexOf("=") + 1));
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        cllectLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
                builder.setTitle("提示");
                builder.setIcon(R.mipmap.logo);
                builder.setMessage("是否删除这条记录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (details != null && details.size() != 0) {
                            String url = details.get(position).getDetailUrl();
                            //long classId = Long.parseLong(url.substring(url.lastIndexOf("=") + 1));
                            boolean isDete = dBhelper.delete(MyDBHelper.COLLECTION, "url=?", new String[]{url});

                            if (isDete) {
                                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                initData();
                            }
                        }
                    }
                });
                builder.setNegativeButton("取消", null);

                builder.create().show();
                return true;
            }
        });

    }

    private List<Detail> getDetails() {
        List<Detail> details = new ArrayList<>();
        if (dBhelper == null) {
            dBhelper = DBhelper.getDBHelper(getApplicationContext());
        }
        Cursor cursor = dBhelper.queryAll(MyDBHelper.COLLECTION);
        if (cursor != null) {
            if (cursor.getCount() == 0) return details;
            Detail detail = null;
            while (cursor.moveToNext()) {
                detail = new Detail();
                int id = cursor.getColumnIndex("url");
                detail.setDetailUrl(cursor.getString(id));
                id = cursor.getColumnIndex("time");
                detail.setTime(Long.parseLong(cursor.getString(id)));
                id = cursor.getColumnIndex("recount");
                detail.setRcount(cursor.getInt(id));
                id = cursor.getColumnIndex("title");
                detail.setDescription(cursor.getString(id));
                details.add(detail);
            }
            return details;
        } else {
            return details;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        thread.isInterrupted();
    }
}
