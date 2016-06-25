package example.com.cbk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import example.com.cbk.R;

public class LoadingActivity extends AppCompatActivity {

    private Handler handler=new Handler();
    private SharedPreferences pref;
    //boolean isf=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
       pref= getSharedPreferences("system", Context.MODE_PRIVATE);
        //延时跳转
      handler.postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent = new Intent();
              //如果是第一次
              if (isFirst()) {
                  intent.setClass(LoadingActivity.this, WelcomeActivity.class);
              } else {
                  intent.setClass(LoadingActivity.this, HomeActivity.class);
              }
              startActivity(intent);
              finish();
          }
      }, 1000);
    }



    private  boolean isFirst(){
       return pref.getBoolean("isfirst",true);
    }
}
