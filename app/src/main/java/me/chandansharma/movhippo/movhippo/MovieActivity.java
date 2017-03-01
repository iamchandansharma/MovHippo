package me.chandansharma.movhippo.movhippo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import me.chandansharma.movhippo.R;
import me.chandansharma.movhippo.fragments.FavouriteMovieList;
import me.chandansharma.movhippo.fragments.PopularMovieList;
import me.chandansharma.movhippo.fragments.TopRatedMovieList;

public class MovieActivity extends AppCompatActivity {

    //get the Tag of the class
    private static final String TAG = MovieActivity.class.getSimpleName();

    private TabLayout mMovieTitleTab;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        setTitle(R.string.app_name);

        ViewPager moviePages = (ViewPager) findViewById(R.id.viewpager);
        if (moviePages != null)
            setViewPager(moviePages);

        mMovieTitleTab = (TabLayout) findViewById(R.id.tab_layout);
        mMovieTitleTab.setupWithViewPager(moviePages);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //set the drawerListener
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popular_movie:
                        mMovieTitleTab.getTabAt(0).select();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.top_rated_movie:
                        mMovieTitleTab.getTabAt(1).select();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.favourite_movie:
                        mMovieTitleTab.getTabAt(2).select();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter moviePagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        moviePagerAdapter.addNewFragment(new PopularMovieList(), getString(R.string.most_popular));
        moviePagerAdapter.addNewFragment(new TopRatedMovieList(), getString(R.string.top_rated));
        moviePagerAdapter.addNewFragment(new FavouriteMovieList(), getString(R.string.favourite));
        viewPager.setAdapter(moviePagerAdapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        //declare list variable to hold all the fragment and also fragment name
        List<Fragment> movieFragments = new ArrayList<>();
        List<String> movieFragmentsTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addNewFragment(Fragment fragment, String title) {
            movieFragments.add(fragment);
            movieFragmentsTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return movieFragments.get(position);
        }

        @Override
        public int getCount() {
            return movieFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return movieFragmentsTitles.get(position);
        }
    }
}
