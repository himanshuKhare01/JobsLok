package com.jobslok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.common.collect.Lists;
import com.jobslok.Adapter.GuidePagerAdapter;
import com.jobslok.R;
import com.jobslok.ViewPagerManager.GuideModelManager;

import java.util.List;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class GuideActivity extends AppCompatActivity {
    ScrollerViewPager viewPager;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        viewPager =  findViewById(R.id.view_pager);
        imageView=findViewById(R.id.rightArrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
        SpringIndicator springIndicator =  findViewById(R.id.indicator);
        springIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==3) {
                    imageView.setVisibility(View.VISIBLE);
                }else
                    imageView.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        GuideModelManager manager = new GuideModelManager();
        manager.addCommonFragment(getBgRes(), getFragmentNumber(),getDescription(),getTitles());
        GuidePagerAdapter adapter = new GuidePagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);
    }

    private List<String> getDescription() {
        return Lists.newArrayList("Take a moment to set up your profile and let your potential taskers know who you are. Then get posting tasks!",
                "Tell us what your task is,where and when you'd like it done. Next, set a budget and see the offers roll in.",
                "Look over your offers then isremote Profiles/Reviews to pick the best Tasker for your task.",
                "Accept an offer, use JobsLok pay to hold the payment securely until the task is complete and then release it.");
    }
    private List<String> getTitles() {
        return Lists.newArrayList(
                "Hi Ugh, let's get\nStarted!",
                "Post a task",
                " Pick the person \nthat's right for you",
                "Time to get it\ndone!");
    }

    private List<String> getFragmentNumber(){
        return Lists.newArrayList("J", "O", "B", "S");
    }

    private List<Integer> getBgRes(){
        return Lists.newArrayList(R.drawable.illustration_booking_booked, R.drawable.illustration_hiw_post_your_task, R.drawable.illustration_hiw_review_offers, R.drawable.illustration_pat_what);
    }
}
