package com.pankajkcodes.videodownloader.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.pankajkcodes.videodownloader.R;
import com.pankajkcodes.videodownloader.adapters.PagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Toolbar toolbar;
    private PagerAdapter adapter;

    private int requestCode = 10;
    private ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // PERMISSION
        checkPermission();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Instagram Video Downloader");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        drawerNavMethod();
        // VIEWPAGER AND TAB LAYOUT START HERE //
        viewPager = findViewById(R.id.fragment_container);
        tabLayout = findViewById(R.id.tab_layout);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new PagerAdapter(fm, getLifecycle());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);

        }


    }


    @SuppressLint("NonConstantResourceId")
    private void drawerNavMethod() {

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.drawer_home:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    break;
                case R.id.drawer_about:
                    Toast.makeText(MainActivity.this, R.string.app_name, Toast.LENGTH_LONG).show();
                    break;
                case R.id.drawer_share:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "                     ");
                    startActivity(Intent.createChooser(intent, "Share to"));
                    break;
                case R.id.drawer_more:
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("              ")));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.drawer_privacy:
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.drawer_feedback:
                case R.id.drawer_updates:
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("  ")));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;

            }

            return true;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        drawerToggle.onDrawerOpened(navigationView);
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);

        return true;
    }
}