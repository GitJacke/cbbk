package example.com.cbk.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import example.com.cbk.R;
import example.com.cbk.bean.Detail;
import example.com.cbk.utils.database.DBhelper;
import example.com.cbk.utils.database.MyDBHelper;
import example.com.networkthread.HttpHelper;
import example.com.networkthread.Request;
import example.com.networkthread.StringRequest;

public class DetailActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Toolbar hom_Tb;
    private ImageView shareIV;
    private ImageView collectIV;
    private Detail detail;
    private TextView titleTv, keyWorkTv, timeTv, detailTV;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_activity);
        ShareSDK.initSDK(getApplicationContext());
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            long id = intent.getLongExtra("id", 19573);//int--long..error1
            //Log.d(TAG, "onCreate: id="+id);
            getDataFromNet(id);
        }
    }

    private void initView() {

        hom_Tb = (Toolbar) findViewById(R.id.dt_toolbar);
        View view = View.inflate(getApplicationContext(), R.layout.toolbar_layout, null);
        shareIV = (ImageView) view.findViewById(R.id.tb_share);
        collectIV = (ImageView) view.findViewById(R.id.tb_plus);
        TextView textView = (TextView) view.findViewById(R.id.tb_more);
        textView.setVisibility(View.GONE);
        hom_Tb.addView(view);

        titleTv = (TextView) findViewById(R.id.dt_title);
        keyWorkTv = (TextView) findViewById(R.id.dt_kytv);
        timeTv = (TextView) findViewById(R.id.dt_timetV);
        detailTV = (TextView) findViewById(R.id.dt_dt);
        shareIV.setOnClickListener(this);
        collectIV.setOnClickListener(this);
    }

    private void getDataFromNet(final long classID) {
        url = "http://www.tngou.net/api/lore/show?id=" + classID;
        Log.i(TAG, "getDataFromNet: url=" + url);
        StringRequest request = new StringRequest(url, "GET", new Request.ResponeCallback<String>() {
            @Override
            public void error(Exception e) {
                Log.i(TAG, "error: " + e.getMessage());
            }
            @Override
            public void data(final String jsonstr) {
                //Log.d(TAG, "data: info");
                try {
                    JSONObject jo = new JSONObject(jsonstr);
                    detail = parserJson2Info(jo);
                    if (detail != null) {
                       runOnUiThread(new Runnable() {//runOnUi啥时候用？ 同步问题
                           @Override
                           public void run() {
                              updata(detail);
                           }
                       });
                    }
                    Log.d(TAG, "data: detail" + detail.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
       HttpHelper.addRequest(request);
    }

    /**
     * 下载完成后更新页面
     * @param detail
     *
     */
    private void updata(Detail detail) {
        titleTv.setText(this.detail.getTitle());
        String keyword= this.detail.getKeywords();
        keyWorkTv.setText("关键字:" + keyword);
        timeTv.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                format(this.detail.getTime()));
        detailTV.setText(Html.fromHtml(this.detail.getCotentText()));
        //显示两个图标
        shareIV.setVisibility(View.VISIBLE);
        collectIV.setVisibility(View.VISIBLE);
        //保存数据
        ContentValues values=new ContentValues();
        values.put("url", url);
        values.put("title", detail.getTitle());
        //添加到浏览历史
        if(dBhelper==null){
            dBhelper=DBhelper.getDBHelper(getApplication());
            dBhelper.insert(MyDBHelper.RECODE,values);
        }
    }

    private Detail parserJson2Info(JSONObject jo) throws JSONException {
        final Detail info = new Detail();
        info.setKeywords(jo.optString("keywords"));
        info.setTitle(jo.optString("title"));
        info.setTime(jo.optLong("time"));
        info.setCotentText(jo.optString("message"));
        info.setDescription(jo.optString("description"));
        info.setRcount(jo.optInt("rcount"));
        return info;
    }
    private DBhelper dBhelper;

    @Override
    public void onClick(View v) {
       // dBhelper= DBhelper.getDBHelper(getApplicationContext());//这里错过了，，不能用this
        switch (v.getId()) {
            case R.id.tb_share:
                    showShare();

                break;
        case R.id.tb_plus:
                if(detail!=null){
                    boolean isExists=dBhelper.isExists(MyDBHelper.COLLECTION,"url",url);
                    if(!isExists){
                        ContentValues values=new ContentValues();
                        values.put("title",detail.getTitle());
                        values.put("url", url);
                        values.put("time",detail.getTime()+"");
                        values.put("recount",detail.getRcount());
                        values.put("desc",detail.getCotentText());
                        boolean isSucceed=dBhelper.insert(MyDBHelper.COLLECTION, values);
                        if(isSucceed){
                            Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"已经收藏过了",Toast.LENGTH_SHORT).
                                show();
                        Log.i(TAG, "onClick: "+"hehe");
                        return;
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    private void queryAll(){
        if(dBhelper==null){
            dBhelper=DBhelper.getDBHelper(getApplicationContext());
        }
        Cursor cursor= dBhelper.queryAll(MyDBHelper.COLLECTION);
        while (cursor.moveToNext()){
            int id = cursor.getColumnIndex("title");
            String title=cursor.getString(id);
            id=cursor.getColumnIndex("url");
            String url=cursor.getString(id);
            Log.d(TAG, "onClick: cursor"+title+url);
        }
    }

    private void showShare() {
        if(detail!=null){
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle("我是分享");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(detail.getDetailUrl());
            // text是分享文本，所有平台都需要这个字段
            oks.setText("软件测试---》分享");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(url);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));

            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(detail.getDetailUrl());
// 启动分享GUI
            oks.show(this);


        }


    }

}
