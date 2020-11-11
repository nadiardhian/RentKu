package com.example.zulfikaranshari.rentku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

public class ActivityProfilePenyewa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_penyewa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tablayout = (TabLayout) findViewById(R.id.tab_layout2);
        tablayout.addTab(tablayout.newTab().setText("Profile"));
        tablayout.addTab(tablayout.newTab().setText("Kendaraan"));

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager2);
        final PagerAdapterPenyewa adapter = new PagerAdapterPenyewa
                (getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.home) {
//            startActivity(new Intent(ActivityProfilePenyewa.this, halamanUtama.class ));
//            return true;
//        }else if (id == R.id.profile){
//            ///startActivity(new Intent(halamanUtama.this, MainActivity_Profile.class ));
//            return true;
//        }else if(id ==R.id.pembayaran){
//            startActivity(new Intent(ActivityProfilePenyewa.this, Activity_Pembayaran.class));
//            return true;
//        }else{
//            startActivity(new Intent(ActivityProfilePenyewa.this, riwayatHome.class));
//
//        }

        return super.onOptionsItemSelected(item);
    }


}