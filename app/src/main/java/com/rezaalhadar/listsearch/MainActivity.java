package com.rezaalhadar.listsearch;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rezaalhadar.R;
import com.rezaalhadar.listsearch.adapter.ViewPagerAdapter;
import com.rezaalhadar.listsearch.fragment.GridViewFragment;
import com.rezaalhadar.listsearch.fragment.ListViewFragment;
import com.rezaalhadar.listsearch.fragment.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //setting tab over viewpager
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ListViewFragment(), "ListView");
        adapter.addFrag(new GridViewFragment(), "GridView");
        adapter.addFrag(new RecyclerViewFragment(), "RecyclerView");
        viewPager.setAdapter(adapter);
    }


}
