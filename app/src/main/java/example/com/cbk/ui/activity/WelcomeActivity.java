package example.com.cbk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import example.com.cbk.R;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WelcomPagerAdapter adapter;
    private int[] imageRescour={R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3};
    private ImageView[] imageViews=new ImageView[imageRescour.length];
    private LinearLayout indicator_layout;
    private int lastIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welecom);
        initView();


    }


    private void initView() {

        viewPager= (ViewPager) this.findViewById(R.id.welcom_Vp);
        indicator_layout= (LinearLayout) findViewById(R.id.welcom_Indicator);

        /*
        数据的获取
         */
        //欢迎页图片布局参数
        ViewGroup.LayoutParams vlp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //小圆圈布局参数
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(10,10);
        llp.leftMargin=10;
        for (int i = 0; i < imageRescour.length; i++) {
            imageViews[i]=new ImageView(this);
            imageViews[i].setLayoutParams(vlp);
            imageViews[i].setImageResource(imageRescour[i]);
            imageViews[i].setScaleType(ImageView.ScaleType.FIT_XY);
            ImageView indicator=new ImageView(this);
            indicator.setLayoutParams(llp);
            //启动时是第一张图片
            if(i==0){
                indicator.setBackgroundResource(R.mipmap.page_now);
            }else {
                indicator.setBackgroundResource(R.mipmap.page);
            }
            indicator_layout.addView(indicator);
            //给最后一张图片设定监听
            if(i==imageRescour.length-1){
                imageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转
                        Intent intent=new Intent(WelcomeActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
        /*
        设置数据
         */
        adapter=new WelcomPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter);

    }



   public class WelcomPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

        @Override
        public int getCount() {
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

       @Override
       public Object instantiateItem(ViewGroup container, int position) {
          container.addView( imageViews[position]);
           return imageViews[position];
       }

       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
       }

       @Override
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

       }

       @Override
       public void onPageSelected(int position) {
            indicator_layout.getChildAt(lastIndex).setBackgroundResource(R.mipmap.page);
            indicator_layout.getChildAt(position).setBackgroundResource(R.mipmap.page_now);
            lastIndex=position;
       }

       @Override
       public void onPageScrollStateChanged(int state) {

       }
   }


}
