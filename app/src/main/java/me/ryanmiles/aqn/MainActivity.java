package me.ryanmiles.aqn;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.gameanalytics.sdk.GameAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Loot;
import me.ryanmiles.aqn.events.ChangeFragmentEvent;
import me.ryanmiles.aqn.events.ChangeWorldEvent;
import me.ryanmiles.aqn.events.DataUpdateEvent;
import me.ryanmiles.aqn.events.LogUpdateEvent;
import me.ryanmiles.aqn.events.updates.MainActivityDialog;
import me.ryanmiles.aqn.fragments.ViewPagerFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_activity_storage_text_view)
    TextView mStorageTextView;
    @BindView(R.id.main_activity_log_text_view)
    TextView mLogTextView;
    boolean temp = false;
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
        if (Data.PLAYER_CURRENT_HEALTH <= 0) {
            displayDeathDialog();
            Data.PLAYER_CURRENT_HEALTH = 1;
        }
        if (!BuildConfig.DEBUG) {
            //setupGameAnalytics();
        }

        mStorageTextView.setMovementMethod(new ScrollingMovementMethod());
        if (!temp && Data.MAP_SHARD.isDiscovered()) {
            survey();
        }

    }

    private void survey() {
        temp = true;
        final String url = "http://bit.ly/1U3aKad";
        new AlertDialogWrapper.Builder(this)
                .setTitle("Thank you for Testing!")
                .setCancelable(false)
                .setMessage("Thank you for helping test the app! Your playthrough will help me see any issues or design flaws! \n\nIf you have time I would appreciate your feedback through the survey listed below. Thank you for everythng!")
                .setPositiveButton("Take me to the quick survey!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Answers.getInstance().logContentView(new ContentViewEvent()
                                .putContentName("Opened Survey"));
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    private void setupGameAnalytics() {
        GameAnalytics.setEnabledInfoLog(true);
        GameAnalytics.setEnabledVerboseLog(true);

        GameAnalytics.configureBuild("0.1.0");
        GameAnalytics.initializeWithGameKey(this, getString(R.string.com_gameanalytics_apiKey), getString(R.string.com_gameanalytics_apiSecret));

    }

    private void displayDeathDialog() {
        Data.WOOD.remove(25);
        Data.STONE.remove(50);
        new AlertDialogWrapper.Builder(this)
                .setTitle("You Died!")
                .setCancelable(false)
                .setMessage("You took too many hits in there. \n\nYou lost: \nWood:25 \nStone:50")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    @Subscribe
    public void onEvent(DataUpdateEvent event) {
        if (event.UpdateStorage()) {
            updateStorage();
        }
        if (!event.getLogText().equals("")) {
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

    @Subscribe
    public void onEvent(ChangeWorldEvent event) {
        startActivity(new Intent(this, WorldActivity.class));
    }

    @Subscribe
    public void onEvent(MainActivityDialog event) {
        event.getDialog(this);
    }


    public void updateStorage() {
        mStorageTextView.setText(" Storage:");
        for (Item item : Data.ALL_ITEMS) {
            if (!item.isDiscovered() && item.getAmount() > 0) {
                item.setDiscovered(true);
            }
            if (item.isDiscovered()) {
                appendStorageTextView(" " + item.getName() + ": " + item.getAmount() + " / " + item.getMax());
            }
        }

        for (Loot loot : Data.ALL_LOOT) {
            if (!loot.isDiscovered() && loot.getAmount() > 0) {
                loot.setDiscovered(true);
            }
            if (loot.isDiscovered() && loot.isStorageDisplay()) {
                appendStorageTextView(" " + loot.getName() + ": " + loot.getAmount() + " / " + loot.getMax());
            }
        }
    }

    private void appendStorageTextView(String string) {
        mStorageTextView.append("\n" + string);
    }

    public void updateLog(String text) {
        mLogTextView.setText(text + "\n" + mLogTextView.getText().toString());
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.rest_data:
                Reset();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
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