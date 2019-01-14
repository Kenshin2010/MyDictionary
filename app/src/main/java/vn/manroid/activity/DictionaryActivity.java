package vn.manroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.manroid.adapter.DictionaryAdapter;
import vn.manroid.fragment.InforFragment;
import vn.manroid.model.Dictionary;
import vn.manroid.mydictionary.R;
import vn.manroid.utils.RuntimeWord;
import yami.blackcode.database.Sqlite;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String str;
    public static DictionaryAdapter adapter;
    public static ArrayList<Dictionary> arrDictionary;
    private ListView lvDictionary;
    public static ProgressBar progressBar;
    public static Activity activity;
    private TextView tvContent;
    private Cursor cursor;
    private InforFragment fragment;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(this, "Nhập từ bạn muốn tra", Toast.LENGTH_SHORT).show();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvContent = (TextView) findViewById(R.id.tv_enter_content);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String mobile = "0978731634";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mobile));
                    intent.putExtra("sms_body", "Bạn muốn liên hệ với tôi chứ ???");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi , vui lòng thử lại !!!", Toast.LENGTH_LONG).show();
                }

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        activity = this;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadWidget();
        loadDataForDictionary();
        setEventForWord();

    }

    private void setEventForWord() {
        lvDictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dictionary model = arrDictionary.get(position);
                cursor = Sqlite.searchWord(DictionaryActivity.this, model.getMean());
                cursor.moveToFirst();
                String s = cursor.getString(2);
                Intent intent = new Intent(DictionaryActivity.this, WordActivity.class);
                intent.putExtra("head", model.getMean().toString());
                intent.putExtra("body", s);
                startActivity(intent);
                cursor.close();
            }
        });
    }

    private void loadDataForDictionary() {
        Sqlite.createDatabase(this, "Dictionary");
        adapter = new DictionaryAdapter(getLayoutInflater(), arrDictionary);
        lvDictionary.setAdapter(adapter);
    }

    private void loadWidget() {
        lvDictionary = (ListView) findViewById(R.id.lv_dictionary);
        progressBar = (ProgressBar) findViewById(R.id.prg_loading);
        arrDictionary = new ArrayList<>();

        //-------------------------------------//

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dictionary, menu);
        MenuItem item = menu.findItem(R.id.mnSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        if (searchView != null) {
            searchView.setOnQueryTextListener(onWordChange_Click);
        } else {
        }

        return true;
    }

    SearchView.OnQueryTextListener onWordChange_Click = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            progressBar.setVisibility(View.VISIBLE);
            new RuntimeWord().execute(newText);
            if (newText.length() > 0)
                tvContent.setVisibility(View.GONE);
            else
                tvContent.setVisibility(View.VISIBLE);
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            if (fragment instanceof InforFragment) {
                Toast.makeText(this, "Nhập từ bạn muốn tra", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                fab.show();

            }

            // Handle the camera action
        } else if (id == R.id.infor) {
            fragment = new InforFragment();
            changeLayout(fragment);
            progressBar.setVisibility(View.INVISIBLE);
            fab.hide();
        } else if (id == R.id.exit) {
            finish();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Updating ... !!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Updating ... !!!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeLayout(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentPanel, fragment);
        transaction.commit();
    }
}
