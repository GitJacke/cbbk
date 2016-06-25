package example.com.cbk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.cbk.R;
import example.com.cbk.adapter.VpFragmentAdapter;
import example.com.cbk.bean.Title;

public class HomeActivity extends FragmentActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private TabLayout hom_infotitle;
    private VpFragmentAdapter fragdapter;
    private ViewPager fragVp;
    private Toolbar hom_Tb;
    private TextView hom_moreTv;
    private SharedPreferences pref;
    private Title[] titles=new Title[]{
            new Title(1,"老人健康"),
            new Title(2,"孩子健康"),
            new Title(3,"健康饮食"),
            new Title(4,"男性健康"),
            new Title(5,"女性保养"),
            new Title(6,"孕婴手册"),
            new Title(7,"私密生活"),
            new Title(8,"育儿宝典"),
            new Title(9,"心里健康"),
            new Title(10,"四季养生"),
            new Title(11,"减肥瘦身"),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    private void initView() {
        hom_infotitle= (TabLayout) findViewById(R.id.home_Title);
        fragVp= (ViewPager) findViewById(R.id.home_Vp);
        hom_Tb= (Toolbar) findViewById(R.id.home_tobar);
        View view =View.inflate(getApplicationContext(),R.layout.toolbar_layout,null);
        ImageView shareImage= (ImageView) view.findViewById(R.id.tb_share);
        ImageView plusImage= (ImageView) view.findViewById(R.id.tb_plus);
        hom_moreTv= (TextView) view.findViewById(R.id.tb_more);

        hom_moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到SettingActivity
                Intent setIntent=new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(setIntent);
            }
        });
        //隐藏两个图标
        shareImage.setVisibility(View.GONE);
        plusImage.setVisibility(View.GONE);
        //将局部添加到toolbar
        hom_Tb.addView(view);

        fragdapter=new VpFragmentAdapter(getSupportFragmentManager(),titles);
        hom_infotitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        fragVp.setAdapter(fragdapter);
        hom_infotitle.setupWithViewPager(fragVp);

    }

    @Override
    protected void onResume() {
        super.onResume();
       getSharedPreferences("system", Context.MODE_PRIVATE).edit().putBoolean("isfirst",false).
               commit();

    }
}
