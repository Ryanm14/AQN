package me.ryanmiles.aqn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.events.ChangeWorldFragmentEvent;
import me.ryanmiles.aqn.events.CreatureDeadEvent;
import me.ryanmiles.aqn.events.updates.PlayerDead;
import me.ryanmiles.aqn.fragments.FightFragment;
import me.ryanmiles.aqn.fragments.WorldFragment;

/**
 * Created by ryanm on 5/23/2016.
 */
public class WorldActivity extends AppCompatActivity {
    WorldFragment mWorldFragment;
    ArrayList<Creature> mCreatureList;
    int mCurrentCreaturePos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        mWorldFragment = new WorldFragment();
        EventBus.getDefault().register(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mWorldFragment)
                .commit();
    }

    @Subscribe
    public void onEvent(ChangeWorldFragmentEvent event) {
        mCreatureList = event.getCreatureArrayList();
        Data.PLAYER_CURRENT_HEALTH = Data.PLAYER_MAX_HEALTH;
        openFightFragment(mCreatureList.get(mCurrentCreaturePos));
    }

    @Subscribe
    public void onEvent(PlayerDead event) {
        startActivity(new Intent(WorldActivity.this, MainActivity.class));
    }

    private void openFightFragment(Creature creature) {
        FragmentManager trans = getSupportFragmentManager();
        trans.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout, FightFragment.newInstance(creature.getName(), creature.getHealth(), creature.getDamage()))
                .commit();
        trans.popBackStack();
    }

    @Subscribe
    public void onEvent(CreatureDeadEvent event) {
        mCurrentCreaturePos++;
        if (mCurrentCreaturePos < mCreatureList.size()) {
            openFightFragment(mCreatureList.get(mCurrentCreaturePos));
        } else {
            FragmentManager trans = getSupportFragmentManager();
            trans.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.frame_layout, new WorldFragment())
                    .commit();
            new AlertDialogWrapper.Builder(this)
                    .setTitle("Abandoned Mine")
                    .setCancelable(false)
                    .setMessage("You finished going through the Cave. \n\nYou found: \n\nCopper: 9\nWood: 45\nStone: 27")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Data.COPPER.addAmount(9);
                            Data.WOOD.addAmount(45);
                            Data.STONE.addAmount(27);
                            //  Thanks();
                        }
                    }).show();
            // startActivity(new Intent(WorldActivity.this, MainActivity.class));
        }
    }

   /* private void Thanks() {
        final String url = "http://goo.gl/forms/LWLNdnC0WLUG599E2";
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
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(WorldActivity.this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
