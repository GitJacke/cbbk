package example.com.cbk.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;

import example.com.cbk.R;

public class FeedBackActivity extends FragmentActivity {

    private static final String TAG = FeedBackActivity.class.getSimpleName();
    private Toolbar hom_Tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);

    }




}
