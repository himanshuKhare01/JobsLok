package com.jobslok.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.jobslok.Fragment.AssignedFragment;
import com.jobslok.Fragment.CompletedFragment;
import com.jobslok.Fragment.FragmentOne;
import com.jobslok.Fragment.FragmentTwo;
import com.jobslok.Fragment.MyTaskFragment;
import com.jobslok.Fragment.OpenFragment;
import com.jobslok.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewTaskActivity extends AppCompatActivity {
 Toolbar toolbar;
 ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        toolbar=findViewById(R.id.viewtask_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        final NavigationTabStrip navigationTabStrip =  findViewById(R.id.nts_top);
        navigationTabStrip.setTabIndex(0,true);
        viewPager=findViewById(R.id.view_taskviewpager);
        setupViewPager(viewPager);
        navigationTabStrip.setViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewTaskPagerAdapter adapter = new ViewTaskPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OpenFragment());
        adapter.addFragment(new AssignedFragment());
        adapter.addFragment(new CompletedFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            getFragmentManager().popBackStack();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class ViewTaskPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewTaskPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

     }
}