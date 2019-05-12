package com.dtec.helloatu.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtec.helloatu.R;
import com.dtec.helloatu.fragment.AddInfoFragment;
import com.dtec.helloatu.fragment.EditInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentBaseActivity extends FragmentActivity implements View.OnClickListener {

    FragmentBaseActivity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int passedPosition;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        activity = this;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            passedPosition = bundle.getInt("positionFragmentActivity");
        }
        Toast.makeText(activity, String.valueOf(passedPosition), Toast.LENGTH_SHORT).show();


    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.new_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        tabOne.setTextColor(Color.WHITE);
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add_info, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.info_edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        tabTwo.setTextColor(Color.WHITE);
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_info_edit, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }


    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddInfoFragment(), "নতুন তথ্য");
        adapter.addFrag(new EditInfoFragment(), "তথ্য সংশোধন");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {

        finish();

    }


}
