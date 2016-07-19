package me.ryanmiles.aqn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.model.People;
import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by Ryan Miles on 7/17/2016.
 */
public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.CustomViewHolder> {
    private List<People> mPeopleList;
    private Context mContext;

    public VillageAdapter(Context context, List<People> peopleList) {
        this.mPeopleList = peopleList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.village_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final People person = mPeopleList.get(i);

        //Setting text view title
        customViewHolder.name.setText(Html.fromHtml(person.getName()));
        updatePeople(customViewHolder, person);

        customViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person.checkIfSpace()) {
                    person.addOne();
                    updatePeople(customViewHolder, person);
                } else {
                    EventBus.getDefault().post(new DataUpdateEvent(false, "You have reached max population!"));
                }
            }
        });
        customViewHolder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person.getAmount() > 0) {
                    person.removeOne();
                    updatePeople(customViewHolder, person);
                } else {
                    EventBus.getDefault().post(new DataUpdateEvent(false, "You can't remove anymore " + person.getName()));
                }
            }
        });
    }

    private void updatePeople(CustomViewHolder customViewHolder, People person) {
        customViewHolder.amount.setText("Amount: " + person.getAmount() + "");
        customViewHolder.increasePerSec.setText(person.getIncreaseType().getName() + " per 5: " + person.getIncrease() + "");
    }


    @Override
    public int getItemCount() {
        return (null != mPeopleList ? mPeopleList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected Button add, subtract;
        protected TextView name, amount, increasePerSec;

        public CustomViewHolder(View view) {
            super(view);
            this.add = (Button) view.findViewById(R.id.add_person);
            this.subtract = (Button) view.findViewById(R.id.subtract_person);
            this.name = (TextView) view.findViewById(R.id.person_name);
            this.amount = (TextView) view.findViewById(R.id.person_amount);
            this.increasePerSec = (TextView) view.findViewById(R.id.person_increase);

        }

    }
}
