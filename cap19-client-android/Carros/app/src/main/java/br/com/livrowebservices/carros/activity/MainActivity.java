package br.com.livrowebservices.carros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.fragment.AboutDialog;
import br.com.livrowebservices.carros.fragment.CarrosFragment;
import br.com.livrowebservices.carros.utils.NavDrawerUtil;
import livroandroid.lib.utils.IntentUtils;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private CoordinatorLayout coordinatorLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        // Toolbar
        setupToolbar();

        setupNavDrawer(this);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);

        // FAB Button
        findViewById(R.id.btAddCarro).setOnClickListener(onClickAddCarro());
    }

    private View.OnClickListener onClickAddCarro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Compat
                Intent intent = new Intent(getActivity(), CarroActivity.class);
                intent.putExtra("editMode", true);
                //ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img, key);
                ActivityCompat.startActivity(getActivity(), intent, null);
            }
        };
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        snack(coordinatorLayout, "Clicou em: " + menuItem);
        return true;
    }

    /**
     * Tabs e ViewPager
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            if (position == 0) {
                args.putString("tipo", "classicos");
            } else if (position == 1) {
                args.putString("tipo", "esportivos");
            } else if (position == 2) {
                args.putString("tipo", "luxo");
            }
            Fragment f = new CarrosFragment();
            f.setArguments(args);
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.classicos);
                case 1:
                    return getString(R.string.esportivos);
                case 2:
                    return getString(R.string.luxo);
                default:
                    return getString(R.string.luxo);
            }
        }
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AboutDialog.showAbout(getSupportFragmentManager());
                return true;
            case R.id.action_site:
                IntentUtils.openBrowser(this, getString(R.string.site_livro_webservice));
                return true;
            case R.id.nav_item_sobre:
                IntentUtils.openBrowser(this, getString(R.string.site_livro_webservice));
                return true;
            case R.id.nav_item_config:
                snack(drawerLayout, "Clicou em config");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
