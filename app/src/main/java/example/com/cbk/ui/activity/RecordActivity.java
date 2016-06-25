package example.com.cbk.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import example.com.cbk.R;
import example.com.cbk.adapter.RecordListAdapter;
import example.com.cbk.bean.Detail;
import example.com.cbk.utils.database.DBhelper;
import example.com.cbk.utils.database.MyDBHelper;

public class RecordActivity extends FragmentActivity{

    private static final String TAG = RecordActivity.class.getSimpleName();
    private Toolbar hom_Tb;
    private List<Detail> details=new ArrayList<>();
    private DBhelper dBhelper;
    private RecordListAdapter adapter;
    private ListView collectListV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_list);
        initView();
        initData();
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                if(getDetails()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            details.clear();
                            details.addAll(getDetails());
                            if(adapter==null){
                                adapter=new RecordListAdapter(details);
                                collectListV.setAdapter(adapter);
                            }else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }.start();
    }
    private void initView() {
        hom_Tb = (Toolbar) findViewById(R.id.record_acty_toolbar);
        View view = View.inflate(getApplicationContext(), R.layout.toolbar_layout, null);

        view.findViewById(R.id.tb_share).setVisibility(View.GONE);
        view.findViewById(R.id.tb_plus).setVisibility(View.GONE);
        view.findViewById(R.id.tb_more).setVisibility(View.GONE);
        hom_Tb.addView(view);

        collectListV= (ListView) findViewById(R.id.centent_Lv);
        collectListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(details!=null&&details.size()!=0){
                   String url= details.get(position).getDetailUrl();
                   long classId= Long.parseLong(url.substring(url.lastIndexOf("=")+1));
                    Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private List<Detail> getDetails(){
        List<Detail> details=new ArrayList<>();
        if(dBhelper==null){
            dBhelper=DBhelper.getDBHelper(getApplicationContext());
        }
       Cursor cursor= dBhelper.queryAll(MyDBHelper.RECODE);
      if(cursor!=null){
         if(cursor.getCount()==0)return null;
          Detail detail=null;
          while (cursor.moveToNext()){
              detail=new Detail();
              int id=cursor.getColumnIndex("url");
              detail.setDetailUrl(cursor.getString(id));
              id=cursor.getColumnIndex("title");
              detail.setTitle(cursor.getString(id));
              details.add(detail);
          }
      }else {
        return null;
      }
    return details;
    }

}
