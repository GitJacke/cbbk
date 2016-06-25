package example.com.cbk.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import example.com.cbk.bean.Title;
import example.com.cbk.ui.fragment.ContentFragment;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
 public  class VpFragmentAdapter extends FragmentStatePagerAdapter {

    private Title[] titles;
    public VpFragmentAdapter(FragmentManager fm, Title[] titles) {
        super(fm);
        this.titles=titles;
    }
    @Override
    public Fragment getItem(int position) {
        ContentFragment cf=new ContentFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",titles[position].getId());
        cf.setArguments(bundle);
        return cf;
    }
    @Override
    public int getCount() {
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position].getTitle();
    }
}
