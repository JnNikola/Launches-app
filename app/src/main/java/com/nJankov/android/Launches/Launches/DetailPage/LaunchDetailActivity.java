package com.nJankov.android.Launches.Launches.DetailPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nJankov.android.Launches.Launches.MainLaunches.LaunchItem;
import com.nJankov.android.Launches.MainActivity;
import com.nJankov.android.Launches.R;
import com.squareup.picasso.Picasso;

/**
 * Created by nJankov on 2/26/2020.
 */





public class LaunchDetailActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {



    private int mMaxScrollSize;
    private boolean activityClosed;
    private Context mContext = this;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_coord_layout);
        activityClosed = false;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.detailpage_tabs);
        ViewPager viewPager  = (ViewPager) findViewById(R.id.detailpage_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.detailpage_appbar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.detailpage_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (activityClosed) {
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                } else {
                    onBackPressed();
                }
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();


        Intent i = getIntent();
        LaunchItem currentLaunchItem = (LaunchItem) i.getSerializableExtra("currentLaunchItem");


        TextView mLaunchTitleTextView = (TextView) findViewById(R.id.detail_page_launch_title);
        mLaunchTitleTextView.setText(currentLaunchItem.getmLaunchName());

        TextView mLaunchPadLocationTextView = (TextView) findViewById(R.id.detail_page_launchpad_location);
        mLaunchPadLocationTextView.setText(currentLaunchItem.getmLaunchLocation());

        ImageView mDetailBigImageView = (ImageView) findViewById(R.id.detailpage_profile_backdrop);

        // slika od raketata
        String currentImageURl = currentLaunchItem.getmRocketImageUrl();
        String smallerImageUrl = currentImageURl.replace("2560", "640");
        smallerImageUrl = smallerImageUrl.replace("1920", "640");

        Picasso.with(tabLayout.getContext()).load(smallerImageUrl).into(mDetailBigImageView);


        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), currentLaunchItem));
        tabLayout.setupWithViewPager(viewPager);

    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, LaunchDetailActivity.class));
    }


    @Override
    protected void onStop() {
        super.onStop();
        activityClosed = true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();


    }

}
