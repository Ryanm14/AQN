package me.ryanmiles.aqn;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.Item;
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
        EventBus.getDefault().register(this);
        mViewPagerFragment = new ViewPagerFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mViewPagerFragment)
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
                .replace(R.id.frame_layout, event.getFragment())
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setLogText(java.lang.Object logText) {
        if (logText != null) {
            mLogTextView.setText(logText.toString());
        }
    }
}