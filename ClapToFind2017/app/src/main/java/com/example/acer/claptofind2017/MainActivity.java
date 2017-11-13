package com.example.acer.claptofind2017;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
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
        toolbarMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
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
                    default: break;
                }
                return false;
            }
        });
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
        fragmentManager.beginTransaction().replace(R.id.contentFrame, settingFragment, getClass().getName()).commit();
    }

    private void displayHomeFragment() {
        getSupportActionBar().setTitle("Home");
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFrame, homeFragment, getClass().getName()).commit();

    }
}
