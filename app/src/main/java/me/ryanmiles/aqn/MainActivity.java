package me.ryanmiles.aqn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.events.ChangeFragmentEvent;
import me.ryanmiles.aqn.events.DataUpdateEvent;
import me.ryanmiles.aqn.events.LogUpdateEvent;
import me.ryanmiles.aqn.fragments.ViewPagerFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_activity_storage_text_view)
    TextView mStorageTextView;
    @BindView(R.id.main_activity_log_text_view)
    TextView mLogTextView;
    private ViewPagerFragment mViewPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPagerFragment = new ViewPagerFragment();
        EventBus.getDefault().register(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mViewPagerFragment, "mViewPager")
                .commit();

        setLogText(Paper.book().read("LOG"));
        updateStorage();
    }


    @Subscribe
    public void onEvent(DataUpdateEvent event) {
        if(event.UpdateStorage()){
            updateStorage();
        }
        if(!event.getLogText().equals("")){
            updateLog(event.getLogText());
        }
    }

    @Subscribe
    public void onEvent(LogUpdateEvent event) {
        updateLog(event.getLogString());
    }

    @Subscribe
    public void onEvent(ChangeFragmentEvent event) {
        FragmentManager trans = getSupportFragmentManager();
        trans.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout, event.getFragment(), event.getTag())
                .addToBackStack(null)
                .commit();
    }

    public void updateStorage() {
        mStorageTextView.setText(" Storage:");
        for (Item item : Data.ALL_ITEMS) {
            if (!item.isDiscovered() && item.getAmount() > 0) {
                item.setDiscovered(true);
            }
            if (item.isDiscovered()){
                appendStorageTextView(" " + item.getName() + ": " + item.getAmount() + " / " + item.getMax());
            }
        }
    }

    private void appendStorageTextView(String string) {
        mStorageTextView.append("\n" + string);
    }

    public void updateLog(String text){
        mLogTextView.setText(text +"\n" + mLogTextView.getText().toString());
    }


    @Override
    protected void onPause() {
        super.onPause();
        App.saveData(mLogTextView.getText().toString());
    }



    public void setLogText(java.lang.Object logText) {
        if (logText != null) {
            mLogTextView.setText(logText.toString());
        }
    }

    @Override
    public void onBackPressed() {
        ViewPagerFragment myFragment = (ViewPagerFragment) getSupportFragmentManager().findFragmentByTag("mViewPager");
        if (myFragment != null && myFragment.isVisible()) {
            mViewPagerFragment.switchTab();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rest_data) {
            Reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void Reset() {
        Paper.book().destroy();
        SharedPreferences prefs = getSharedPreferences("me.ryanmiles.aqn", MODE_PRIVATE);
        prefs.edit().clear().commit();
        System.exit(0);
    }
}