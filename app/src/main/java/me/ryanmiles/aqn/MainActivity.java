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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.easyandroidanimations.library.FadeInAnimation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.Places;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Place;
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
    @BindView(R.id.trans_con_main_activity)
    ViewGroup mTransitionsContainer;
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

        mStorageTextView.setMovementMethod(new ScrollingMovementMethod());
        mLogTextView.setMovementMethod(new ScrollingMovementMethod());

        if(Data.FIRSTRUN){
            new FadeInAnimation(mTransitionsContainer).setDuration(5000).animate();
        }
        Log.v(TAG, "onCreate");

        boolean temp = true;
        for (Place allPlace : Places.ALL_PLACES) {
            if (!allPlace.isCompleted()) {
                temp = false;
            }
        }

        if (temp) {
            survey();
        }
    }

    private void survey() {
        final String url = "https://goo.gl/forms/Z2zeRCWM2t8PXz3f2";
        new AlertDialogWrapper.Builder(this)
                .setTitle("Thank you for Testing!")
                .setCancelable(false)
                .setMessage("Thank you for helping test the app! Your playthrough will help me see any issues or design flaws! \n\nIf you have time I would appreciate your feedback through the survey listed below. Thank you for everythng!")
                .setPositiveButton("Take me to the quick survey!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    private void displayDeathDialog() {
        //TODO Customise death
        new AlertDialogWrapper.Builder(this)
                .setTitle("You Died!")
                .setCancelable(false)
                .setMessage("You took too many hits in there.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    @Subscribe
    public void onEvent(DataUpdateEvent event) {
        Log.v(TAG, "onEvent() called with: " + "event = [" + event + "]");
        if (event.UpdateStorage()) {
            updateStorage();
        }
        if (!event.getLogText().equals("")) {
            updateLog(event.getLogText());
        }
    }

    @Subscribe
    public void onEvent(LogUpdateEvent event) {
        Log.v(TAG, "onEvent() called with: " + "event = [" + event + "]");
        updateLog(event.getLogString());
    }

    @Subscribe
    public void onEvent(ChangeFragmentEvent event) {
        Log.v(TAG, "onEvent() called with: " + "event = [" + event + "]");
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
        Log.v(TAG, "updateStorage() called");
        mStorageTextView.setText(" Storage:");
        for (Item item : Data.ALL_ITEMS) {
            if (!item.isDiscovered() && item.getAmount() > 0) {
                item.setDiscovered(true);
            }
            if (item.isDiscovered()) {
                appendStorageTextView(" " + item.getName() + ": " + item.getAmount() + " / " + item.getMax());
            }
        }

    }

    private void appendStorageTextView(String string) {
        Log.v(TAG, "appendStorageTextView() called with: " + "string = [" + string + "]");
        mStorageTextView.append("\n" + string);
    }

    public void updateLog(String text) {
        Log.v(TAG, "updateLog() called with: " + "text = [" + text + "]");
        mLogTextView.setText(text + "\n" + mLogTextView.getText().toString());
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause() called");
        App.saveData(mLogTextView.getText().toString());
    }


    public void setLogText(java.lang.Object logText) {
        Log.v(TAG, "setLogText() called with: " + "logText = [" + logText + "]");
        if (logText != null) {
            mLogTextView.setText(logText.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "onBackPressed() called");
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
        Log.d(TAG, "Reset() called");
        Paper.book().destroy();
        SharedPreferences prefs = getSharedPreferences("me.ryanmiles.aqn", MODE_PRIVATE);
        prefs.edit().clear().commit();
        System.exit(0);
    }
}