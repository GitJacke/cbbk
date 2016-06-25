package example.com.cbk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import example.com.cbk.R;

public class SettingActivity extends FragmentActivity implements View.OnClickListener {


    private Toolbar set_Tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        initView();


    }

    private void initView() {
        set_Tb = (Toolbar) findViewById(R.id.st_tb);
        View view = View.inflate(getApplicationContext(), R.layout.toolbar_layout, null);
        view.findViewById(R.id.tb_share).setVisibility(View.GONE);
        view.findViewById(R.id.tb_plus).setVisibility(View.GONE);
        view.findViewById(R.id.tb_more).setVisibility(View.GONE);

        findViewById(R.id.st_record).setOnClickListener(this);
        findViewById(R.id.st_collect).setOnClickListener(this);
        findViewById(R.id.st_ver).setOnClickListener(this);
        findViewById(R.id.st_sugets).setOnClickListener(this);
        set_Tb.addView(view);
    }

    @Override
    public void onClick(View v) {
        Intent intent =null;
        switch (v.getId()) {
            case R.id.st_collect:
            intent = new Intent(getApplicationContext(), CollectionActivity.class);
                break;
            case R.id.st_record:
                intent =new Intent(getApplicationContext(),RecordActivity.class);
                break;
            case R.id.st_ver:
                intent=new Intent(getApplicationContext(),VersionActivity.class);
                break;
            case R.id.st_sugets:
                intent =new Intent(getApplicationContext(),FeedBackActivity.class);
                break;
        }
        startActivity(intent);
    }
}
