package com.example.acer.claptofind2017;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static int PERMISSION_CODE = 7890;
    DrawerLayout drawerLayout;
    Toolbar toolbarMain;
    NavigationView navigationMenu;

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addActionBar();
        addEvents();
    }

    private void addEvents() {


    }

    private void addActionBar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMain.setNavigationIcon(R.drawable.ic_gear);
        toolbarMain.setTitleTextColor(getResources().getColor(R.color.color_text_header));
        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        displayHomeFragment();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_setting:
                        displaySettingFragment();
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.menu_contact:
                        displayContact();
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    default: break;
                }
                return false;
            }
        });
    }

    private void displayContact(){
        getSupportActionBar().setTitle("Contact");
        ContactFragment contactFragment = new ContactFragment();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_show, R.anim.fragment_out).replace(R.id.contentFrame, contactFragment).commit();
    }

    private void addControls() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbarMain = (Toolbar) findViewById(R.id.toolbarMain);
        navigationMenu = (NavigationView) findViewById(R.id.navigationMenu);

        HomeFragment homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFrame, homeFragment).commit();
    }

    private void displaySettingFragment() {
        SettingFragment settingFragment = new SettingFragment();
        getSupportActionBar().setTitle("Setting");
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_show, R.anim.fragment_out).replace(R.id.contentFrame, settingFragment, getClass().getName()).commit();
    }

    private void displayHomeFragment() {
        getSupportActionBar().setTitle("Home");
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_show, R.anim.fragment_out).replace(R.id.contentFrame, homeFragment, getClass().getName()).commit();

    }

}
