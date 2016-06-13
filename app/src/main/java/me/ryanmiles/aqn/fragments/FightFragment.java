package me.ryanmiles.aqn.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.events.CreatureDeadEvent;
import me.ryanmiles.aqn.events.updates.PlayerDead;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FightFragment extends Fragment {
    private static final String CREATURE_NAME = "creature_name";
    private static final String CREATURE_DAMAGE = "creature_damage";
    private static final String CREATURE_HEALTH = "creature_health";

    @BindView(R.id.stab)
    Button mStabButton;
    @BindView(R.id.quest_log)
    TextView mQuestLog;
    @BindView(R.id.swing)
    Button mSwingButton;
    @BindView(R.id.quest_storage)
    TextView mQuestStorage;
    @BindView(R.id.strong)
    Button mStrongButton;
    @BindView(R.id.eat)
    Button mEatButton;
    @BindView(R.id.player)
    TextView mPlayer;
    @BindView(R.id.enemy)
    TextView mEnemy;
    @BindView(R.id.enemy_health_bar)
    ProgressBar mEnemyHealthBar;
    @BindView(R.id.player_health_bar)
    ProgressBar mPlayerHealthBar;
    @BindView(R.id.stab_bar)
    ProgressBar mStabBar;
    @BindView(R.id.strong_bar)
    ProgressBar mStrongBar;
    @BindView(R.id.swing_bar)
    ProgressBar mSwingBar;
    @BindView(R.id.eat_bar)
    ProgressBar mEatBar;
    @BindView(R.id.enemyname)
    TextView mEnemyName;
    @BindView(R.id.enemyhealthtext)
    TextView mEnemyHealthText;
    @BindView(R.id.playerhealthtext)
    TextView mPlayerHealthText;


    private String mCreatureName;
    private int mCreatureDamage;
    private int mCreatureHealth;
    private int mCreatureCurrentHealth;
    private CountDownTimer mCreatureCountDownTimer;


    public FightFragment() {
        // Required empty public constructor
    }


    public static FightFragment newInstance(String name, int health, int damage) {
        FightFragment fragment = new FightFragment();
        Bundle args = new Bundle();
        args.putString(CREATURE_NAME, name);
        args.putInt(CREATURE_HEALTH, health);
        args.putInt(CREATURE_DAMAGE, damage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCreatureName = getArguments().getString(CREATURE_NAME);
            mCreatureHealth = getArguments().getInt(CREATURE_HEALTH);
            mCreatureDamage = getArguments().getInt(CREATURE_DAMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_fight, container, false);
        ButterKnife.bind(this, rootview);
        setupViews();
        return rootview;
    }

    private void setupViews() {
        //Player
        mPlayerHealthText.setText(Data.PLAYER_CURRENT_HEALTH + "/" + Data.PLAYER_MAX_HEALTH);
        mPlayerHealthBar.setMax(Data.PLAYER_MAX_HEALTH);
        mPlayerHealthBar.setProgress(Data.PLAYER_CURRENT_HEALTH);
        mStrongButton.setEnabled(false);

        //Enemy
        mCreatureCurrentHealth = mCreatureHealth;
        mEnemyName.setText(mCreatureName);
        mEnemy.setText(mCreatureName.substring(0, 1));
        mEnemyHealthBar.setMax(mCreatureHealth);
        mEnemyHealthBar.setProgress(mCreatureCurrentHealth);
        mEnemyHealthText.setText(mCreatureCurrentHealth + "/" + mCreatureHealth);


        //Bars
        mEatBar.setVisibility(View.INVISIBLE);
        mStrongBar.setVisibility(View.INVISIBLE);
        mStabBar.setVisibility(View.INVISIBLE);
        mSwingBar.setVisibility(View.INVISIBLE);

        //Temp
        mStrongButton.setEnabled(false);

        updateQuestStorage();
        enemyAttackNormal(2);
        setLogText("You found a " + mCreatureName);
    }

    private void updateQuestStorage() {
        mQuestStorage.setText("Meat: " + Data.MEAT.getAmount() + "/" + Data.MEAT.getMax());
    }

    @OnClick(R.id.stab)
    public void onClickStab() {
        mCreatureCurrentHealth -= Data.PLAYER_STAB_DAMAGE;
        if (mCreatureCurrentHealth > 0) {
            mEnemyHealthBar.setProgress(mCreatureCurrentHealth);
            mEnemyHealthText.setText(mCreatureCurrentHealth + "/" + mCreatureHealth);
            mStabButton.setEnabled(false);
            mStabBar.setVisibility(View.VISIBLE);
            mStabBar.setMax(95);
            // new ShakeAnimation(mEnemy).animate();
            CountDownTimer stab_timer = new CountDownTimer(4000, 40) {
                int s = 0;

                @Override
                public void onTick(long millisUntilFinishedl) {
                    s++;
                    mStabBar.setProgress(s);
                }

                @Override
                public void onFinish() {
                    mStabButton.setEnabled(true);
                    s = 0;
                    mStabBar.setProgress(s);
                    mStabBar.setVisibility(View.INVISIBLE);
                }
            };
            stab_timer.start();
            setLogText("You did 2 dmg!");
        } else {
            creatureDead();
        }

    }

    @OnClick(R.id.swing)
    public void onClickSwing() {
        mCreatureCurrentHealth -= Data.PLAYER_SWING_DAMAGE;
        if (mCreatureCurrentHealth > 0) {
            mEnemyHealthBar.setProgress(mCreatureCurrentHealth);
            mEnemyHealthText.setText(mCreatureCurrentHealth + "/" + mCreatureHealth);
            mSwingButton.setEnabled(false);
            mSwingBar.setVisibility(View.VISIBLE);
            mSwingBar.setMax(95);
            // new ShakeAnimation(mEnemy).animate();
            CountDownTimer swing_timer = new CountDownTimer(2500, 25) {
                int s = 0;

                @Override
                public void onTick(long millisUntilFinishedl) {
                    s++;
                    mSwingBar.setProgress(s);
                }

                @Override
                public void onFinish() {
                    mSwingButton.setEnabled(true);
                    s = 0;
                    mSwingBar.setProgress(s);
                    mSwingBar.setVisibility(View.INVISIBLE);
                }
            };
            swing_timer.start();
            setLogText("You did 1 dmg!");
        } else {
            creatureDead();
        }
    }

    @OnClick(R.id.eat)
    public void onClickEat() {
        if (Data.MEAT.getAmount() > 0) {
            Data.MEAT.remove(1);
            if (Data.PLAYER_CURRENT_HEALTH + 3 < Data.PLAYER_MAX_HEALTH) {
                Data.PLAYER_CURRENT_HEALTH += 3;
            } else {
                Data.PLAYER_CURRENT_HEALTH = Data.PLAYER_MAX_HEALTH;
            }
            mEatButton.setEnabled(false);
            mEatBar.setVisibility(View.VISIBLE);
            mEatBar.setMax(95);
            mPlayerHealthText.setText(Data.PLAYER_CURRENT_HEALTH + "/" + Data.PLAYER_MAX_HEALTH);
            mPlayerHealthBar.setProgress(Data.PLAYER_CURRENT_HEALTH);
            CountDownTimer swing_timer = new CountDownTimer(3500, 35) {
                int s = 0;

                @Override
                public void onTick(long millisUntilFinishedl) {
                    s++;
                    mEatBar.setProgress(s);
                }

                @Override
                public void onFinish() {
                    mEatButton.setEnabled(true);
                    s = 0;
                    mEatBar.setProgress(s);
                    mEatBar.setVisibility(View.INVISIBLE);
                }
            };
            swing_timer.start();
            setLogText("You Healed for 3");
        } else {
            setLogText("You are out of Meat!");
        }
    }

    private void creatureDead() {
        mCreatureCountDownTimer.cancel();
        EventBus.getDefault().post(new CreatureDeadEvent());
    }


    private void setLogText(String text) {
        mQuestLog.setText(text + "\n" + mQuestLog.getText());
    }

    public void enemyAttackNormal(int initDelay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
            }
        }, initDelay * 1000);
        Random rng = new Random();

        if (rng.nextInt(100) + 1 >= 70) {//Chance to attack every 1.5 seconds
            Data.PLAYER_CURRENT_HEALTH -= mCreatureDamage; //Damage done
            //  new ShakeAnimation(mPlayer).animate();
            mPlayerHealthBar.setProgress(Data.PLAYER_CURRENT_HEALTH);
            mPlayerHealthText.setText(Data.PLAYER_CURRENT_HEALTH + "/" + Data.PLAYER_MAX_HEALTH);
            setLogText("You took " + mCreatureDamage + " dmg!");
        }
        mCreatureCountDownTimer = new CountDownTimer(2000, 20) {
            @Override
            public void onTick(long millisUntilFinishedl) {
            }

            @Override
            public void onFinish() {
                enemyAttackNormal(0);
            }
        }.start();
        if (Data.PLAYER_CURRENT_HEALTH <= 0) {
            mCreatureCountDownTimer.cancel();
            EventBus.getDefault().post(new PlayerDead());
            getActivity().finish();
        }
    }


}
