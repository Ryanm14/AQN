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

import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.data.model.Place;
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
    Place mPlace;
    int mCurrentCreaturePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        mWorldFragment = new WorldFragment();
        EventBus.getDefault().register(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mWorldFragment)
                .commit();
        mCurrentCreaturePos = 0;
    }

    @Subscribe
    public void onEvent(ChangeWorldFragmentEvent event) {
        mPlace = event.getPlace();
        Data.PLAYER_CURRENT_HEALTH = Data.PLAYER_MAX_HEALTH;
        openFightFragment(mPlace.getCreatureList().get(mCurrentCreaturePos));
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
        if (mCurrentCreaturePos < mPlace.getCreatureList().size()) {
            openFightFragment(mPlace.getCreatureList().get(mCurrentCreaturePos));
        } else {
            FragmentManager trans = getSupportFragmentManager();
            trans.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.frame_layout, new WorldFragment())
                    .commit();
            new AlertDialogWrapper.Builder(this)
                    .setTitle("Completed: " + mPlace.getName())
                    .setCancelable(false)
                    .setMessage(mPlace.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mCurrentCreaturePos = 0;
                            //  Thanks();
                        }
                    }).show();
            // startActivity(new Intent(WorldActivity.this, MainActivity.class));
        }
    }

   /*
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WorldActivity.this, MainActivity.class));
    }
}
