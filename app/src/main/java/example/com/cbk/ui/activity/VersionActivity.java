package example.com.cbk.ui.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import example.com.cbk.R;

public class VersionActivity extends FragmentActivity {


    private static final String TAG = VersionActivity.class.getSimpleName();
    private Toolbar set_Tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_activity);
        initView();

    }

    private void initView() {

       set_Tb= (Toolbar) findViewById(R.id.vs_tb);
        View view =View.inflate(getApplicationContext(),R.layout.toolbar_layout,null);
        view.findViewById(R.id.tb_share).setVisibility(View.GONE);
        view.findViewById(R.id.tb_plus).setVisibility(View.GONE);
        view.findViewById(R.id.tb_more).setVisibility(View.GONE);
        set_Tb.addView(view);

        TextView textView= (TextView) findViewById(R.id.vs_version_num);
        try {
           PackageManager pm = getPackageManager();
            PackageInfo info =     pm.getPackageInfo(getApplicationContext().getPackageName(), 0);
            textView.setText("软件当前版本：" + info.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




    }

    public static int getVersionCode(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }catch(PackageManager.NameNotFoundException e){
            return 0;
        }
    }
}
