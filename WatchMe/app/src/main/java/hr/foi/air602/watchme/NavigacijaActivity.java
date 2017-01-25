package hr.foi.air602.watchme;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import hr.foi.air602.notification.service.MyFirebaseMessagingService;
import hr.foi.air602.watchme.fragments.PocetnaFragment;
import hr.foi.air602.watchme.fragments.FavoritiFragment;
import hr.foi.air602.watchme.fragments.PreporucenoFragment;
import hr.foi.air602.watchme.strategies.ScheduledNotificationStrategy;

/**
 * Created by markopc on 11/2/2016.
 */

public class NavigacijaActivity extends AppCompatActivity {
    private static final String TAG = NavigacijaActivity.class.getSimpleName();

    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<AHBottomNavigationItem> mBottomNavigationItems = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;

    // UI
    private AHBottomNavigationViewPager mNavigationViewPager;
    private AHBottomNavigation mBottomNavigation;
    private FloatingActionButton mFloatingActionButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private FavoritiFragment mFavoritiFragment;
    private PreporucenoFragment mPreporucenoFragment;
    private PocetnaFragment mPocetnaFragment;
    private Fragment fragment;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        mContext = NavigacijaActivity.this;
        mActivity = NavigacijaActivity.this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initUI();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mFavoritiFragment = new FavoritiFragment();
        mPreporucenoFragment = new PreporucenoFragment();
        mPocetnaFragment = new PocetnaFragment();

        mIntent = getIntent();
        MyFirebaseMessagingService.getInstance().setContext(getApplicationContext());
        MyFirebaseMessagingService.getInstance().setup(getApplicationContext());
        MyFirebaseMessagingService.getInstance().schedulingNotifs(ScheduledNotificationStrategy.getInstance(getApplicationContext()));

    }



    /*Options menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }



    /*Akcija na selektirani item*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId() == R.id.action_settings){
           Intent i = new Intent(getApplicationContext(), Postavke.class);
           startActivity(i);

       }
        if(item.getItemId() == R.id.action_about){
            Intent i = new Intent(getApplicationContext(), Informacije.class);
            startActivity(i);
       }
        if(item.getItemId() == R.id.action_odjava){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
       }

        return super.onOptionsItemSelected(item);
    }


    private void initUI() {

        mBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        mNavigationViewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.tab_1), ContextCompat.getDrawable(mContext, R.drawable.ic_home_black_24dp), ContextCompat.getColor(mContext, R.color.color_tab_1));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.tab_2), ContextCompat.getDrawable(mContext, R.drawable.ic_stars_black_24dp), ContextCompat.getColor(mContext, R.color.color_tab_2));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.tab_3), ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp), ContextCompat.getColor(mContext, R.color.color_tab_3));

        mBottomNavigationItems.add(item1);
        mBottomNavigationItems.add(item2);
        mBottomNavigationItems.add(item3);

        mBottomNavigation.addItems(mBottomNavigationItems);
        mBottomNavigation.setOnTabSelectedListener(new TabSelectedListener());

        mBottomNavigation.setColored(true);
        if (!mBottomNavigation.isColored()) {
            mBottomNavigation.setAccentColor(Color.GREEN);
            mBottomNavigation.setInactiveColor(Color.BLUE);
        }


        mNavigationViewPager.setOffscreenPageLimit(4);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (getIntent().getBooleanExtra("reload", false)) {
            //mBottomNavigation.setCurrentItem(2);
            mNavigationViewPager.setCurrentItem(2);
        }

        mNavigationViewPager.setAdapter(mViewPagerAdapter);
        //mBottomNavigation.setNotification("16", 1);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mBottomNavigation, "mFloatingActionButton", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void showSnackBar() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mBottomNavigation, "Snackbar with bottom navigation", Snackbar.LENGTH_SHORT).show();
            }
        }, 3000);
    }


    private void showFloatingActionButton(boolean show) {
        if (show) {
            mFloatingActionButton.setVisibility(View.VISIBLE);
            mFloatingActionButton.setAlpha(0f);
            mFloatingActionButton.setScaleX(0f);
            mFloatingActionButton.setScaleY(0f);
            mFloatingActionButton.animate()
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(300)
                    .setInterpolator(new OvershootInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mFloatingActionButton.animate()
                                    .setInterpolator(new LinearOutSlowInInterpolator())
                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        } else {

            if (mFloatingActionButton.getVisibility() == View.VISIBLE) {
                mFloatingActionButton.animate()
                        .alpha(0)
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(300)
                        .setInterpolator(new LinearOutSlowInInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mFloatingActionButton.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                mFloatingActionButton.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .start();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyFirebaseMessagingService.getInstance().registerBroadcast();
        Log.e(TAG, "onResume: Setting up bsckground service..");

    }

    @Override
    protected void onPause() {
        MyFirebaseMessagingService.getInstance().unregisterBroadcast();
        super.onPause();
    }

    public void showBottomNavigation(boolean show) {
        if (show) {
            mBottomNavigation.restoreBottomNavigation(true);
        } else {
            mBottomNavigation.hideBottomNavigation(true);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("BottomNavigation Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new Fragment();
            switch (position) {
                case 0:
                   // fragment = new PocetnaFragment();
                    fragment = mPocetnaFragment;
                    break;

                case 1:
                    fragment = mFavoritiFragment;
                    break;

                case 2:
                    fragment = mPreporucenoFragment;

                    break;
            }


            return fragment;
        }

        @Override
        public int getCount() {
            return mBottomNavigation.getItemsCount();
        }
    }

    private class TabSelectedListener implements AHBottomNavigation.OnTabSelectedListener {

        @Override
        public boolean onTabSelected(int position, boolean wasSelected) {

            mNavigationViewPager.setCurrentItem(position, false);

            if (position == 1) {

                mFavoritiFragment.init();

            } else if(position == 2){

                mPreporucenoFragment.initialize();

            } else if(position == 0){

                mPocetnaFragment.initialize();
              //  finish();
              //  Intent intent = new Intent(mActivity, NavigacijaActivity.class);
              //  intent.putExtra("reload", true);
              //  startActivity(intent);
            }
            else {
                showFloatingActionButton(false);
            }

            return true;
        }
    }



}
