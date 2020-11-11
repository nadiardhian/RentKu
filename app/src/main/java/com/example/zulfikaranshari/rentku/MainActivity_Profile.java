package com.example.zulfikaranshari.rentku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity_Profile extends AppCompatActivity {
    private ImageView mProfilePic;
    private TextView mNameProfile;
    private TextView mPhoneUser,email_user,alamat_user;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseRef;
    List<UserModel> userList = new ArrayList<>();
    Transformation transformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user");
        new DrawerBuilder().withActivity(this).build();
        alamat_user = (TextView) findViewById(R.id.alamat_user);
        email_user = (TextView) findViewById(R.id.email_user);
        mProfilePic = (ImageView) findViewById(R.id.profilepic);
        mNameProfile = (TextView) findViewById(R.id.name_profile);
        mPhoneUser = (TextView) findViewById(R.id.phone_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tablayout.addTab(tablayout.newTab().setText(R.string.tab_label1));
        tablayout.addTab(tablayout.newTab().setText(R.string.tab_label2));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
mProfilePic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity_Profile.this,ProfileImageUpdate.class);
        startActivity(i);
    }
});
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
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
                .withSelectedItem(2)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position){
                            case 1:
                                startActivity(new Intent(MainActivity_Profile.this, halamanUtama.class));
//                                Toast.makeText(MainActivity_Profile.this, "Home", Toast.LENGTH_SHORT).show();
                                break;

                            case 2:
                                startActivity(new Intent(MainActivity_Profile.this, MainActivity_Profile.class));
//                                Toast.makeText(MainActivity_Profile.this, "Profile", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity_Profile.this, riwayatHome.class));
//                                Toast.makeText(MainActivity_Profile.this, "Riwayat", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                startActivity(new Intent(MainActivity_Profile.this, about.class));
//                                Toast.makeText(MainActivity_Profile.this, "Tentang Kami", Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                startActivity(new Intent(MainActivity_Profile.this, faq.class));
//                                Toast.makeText(MainActivity_Profile.this, "FAQ", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                })
                .build();


        showData();

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
        if (id == R.id.signout) {
            mAuth.signOut();
            finish();

            // Redirect to Login Activity after click on logout button.
            Intent intent = new Intent(MainActivity_Profile.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showData() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //foreach memanggil data

                    UserModel userInfo = postSnapshot.getValue(UserModel.class); //get data dari firebase
                    String emailDB = mUser.getEmail().toString();
                    String emailModel = userInfo.getEmail().toString();

                    if(emailDB.equals(emailModel)){
                        userList.add(userInfo); //add data ke listviews
                        Glide.with(MainActivity_Profile.this).load(userInfo.getUrl().toString()).asBitmap().centerCrop().into(new BitmapImageViewTarget(mProfilePic) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(MainActivity_Profile.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                mProfilePic.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                        Toast.makeText(MainActivity_Profile.this, userInfo.getUrl().toString(), Toast.LENGTH_SHORT).show();
                        //Picasso.get().load(userInfo.getUrl().toString()).placeholder(R.drawable.profile).transform(new CircleTransform()).into(mProfilePic);
                        mPhoneUser.setText(userInfo.getHp().toString());
                        mNameProfile.setText(userInfo.getName().toString());
                        email_user.setText(userInfo.getEmail());
                        alamat_user.setText(userInfo.getAddress());

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void profile(View view) {
        Intent i = new Intent(this, ProfileImageUpdate.class);
        startActivity(i);
    }

}