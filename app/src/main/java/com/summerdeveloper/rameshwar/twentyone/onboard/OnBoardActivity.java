package com.summerdeveloper.rameshwar.twentyone.onboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.summerdeveloper.rameshwar.twentyone.HomeActivity;
import com.summerdeveloper.rameshwar.twentyone.R;

public class OnBoardActivity extends FragmentActivity {

    private ViewPager pager;
    private SmartTabLayout indicator;
    private Button skip,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        pager=(ViewPager)findViewById(R.id.pager);
        indicator=(SmartTabLayout)findViewById(R.id.indicator);
        skip=(Button)findViewById(R.id.skip);
        next=(Button)findViewById(R.id.next);

        FragmentStatePagerAdapter adapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position)
                {
                    case 0:
                        return new OnBoard1();
                    case 1:
                        return new OnBoard2();
                    case 2:
                        return new OnBoard3();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOnBoarding();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem()==2)
                {
                    finishOnBoarding();
                }
                else
                {
                    pager.setCurrentItem(pager.getCurrentItem()+1,true);

                }
            }
        });

        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    skip.setVisibility(View.GONE);
                    next.setText("DONE");
                } else {
                    skip.setVisibility(View.VISIBLE);
                    next.setText("NEXT");
                }
            }
        });
    }

    private void finishOnBoarding()
    {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
       //preferences.edit().putBoolean("onboarding_complete",true).apply();
        preferences.edit().putBoolean("onboarding_complete",true).apply();
        Intent main=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(main);
        finish();
    }
}