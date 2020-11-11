package com.example.zulfikaranshari.rentku;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class riwayatHome extends AppCompatActivity {

    //inisiasi variable
    ViewPager vp;
    TabLayout tab;
    FirebaseAuth auth;
    AppBarLayout abl;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_home);
        new DrawerBuilder().withActivity(this).build();
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set title
        setTitle("Riwayat Peminjaman");

        //referensi variable
        vp = (ViewPager)findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tabs);
        abl = (AppBarLayout)findViewById(R.id.appbar);
        auth = FirebaseAuth.getInstance();

        setupPager(vp);

        tab.setupWithViewPager(vp);
        tab.getTabAt(0).setText("BELUM SELESAI");
        tab.getTabAt(1).setText("SELESAI");
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            //tab dipilih
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    abl.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    abl.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withEmail(mAuth.getCurrentUser().getEmail()).withIcon(getResources().getDrawable(R.drawable.profileheader))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Profile");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Riwayat");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Tentang Kami");
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("FAQ");


//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5

                )
                .withSelectedItem(3)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position){
                            case 1:
                                startActivity(new Intent(riwayatHome.this, halamanUtama.class));
//                                Toast.makeText(riwayatHome.this, "Home", Toast.LENGTH_SHORT).show();
                                break;

                            case 2:
                                startActivity(new Intent(riwayatHome.this, MainActivity_Profile.class));
//                                Toast.makeText(riwayatHome.this, "Profile", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                startActivity(new Intent(riwayatHome.this, riwayatHome.class));
//                                Toast.makeText(riwayatHome.this, "Riwayat", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                startActivity(new Intent(riwayatHome.this, about.class));
//                                Toast.makeText(riwayatHome.this, "Tentang Kami", Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                startActivity(new Intent(riwayatHome.this, faq.class));
//                                Toast.makeText(riwayatHome.this, "FAQ", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                })
                .build();

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

        if(id ==R.id.signout){
            mAuth.signOut();
            finish();

            // Redirect to Login Activity after click on logout button.
            Intent intent = new Intent(riwayatHome.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


            return super.onOptionsItemSelected(item);
    }

    //Method logout dari Firebase
    private void logoutFirebase() {
        //Session logout dari firebase
        FirebaseAuth.getInstance().signOut();

        //Intent
        Intent intent = new Intent(riwayatHome.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Adapter untuk viewpager
    public void setupPager(ViewPager v){
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        adapter.addFragment(new riwayatBelumSelesai(), "BELUM SELESAI");
        adapter.addFragment(new riwayatSelesai(),"SELESAI");

        v.setAdapter(adapter);
    }

    //Subclass adapter untuk Viewpager dengan fragmentnya
    class VPAdapter extends FragmentPagerAdapter {
        private final List<Fragment> listfragment = new ArrayList<>();
        private final List<String> listfragmenttitle = new ArrayList<>();
        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listfragment.get(position);
        }

        public void addFragment(Fragment f, String title){
            listfragment.add(f);
            listfragmenttitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return listfragment.size();
        }
    }
}
