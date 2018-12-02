package in.nullify.survey;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alespero.expandablecardview.ExpandableCardView;

import java.util.ArrayList;

/**
 * Created by Abhishekpalodath on 07-11-2018.
 */

public class AppliancesAdapterNew extends RecyclerView.Adapter<AppliancesAdapterNew.ViewHolder> {
    private Activity context;
    private ArrayList<String> yesno = new ArrayList<>();
    private ArrayList<String> involve = new ArrayList<>();
    private ArrayList<String> gender = new ArrayList<>();

    public AppliancesAdapterNew(Activity context) {
        this.context = context;
    }

    @Override
    public AppliancesAdapterNew.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.applience_list_style, viewGroup, false);
        return new AppliancesAdapterNew.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppliancesAdapterNew.ViewHolder viewHolder, final int i) {

        viewHolder.app_item.setTitle("Appliance "+(i+1));

        viewHolder.aname.setText(((AddActivity)context).applience.get(i).getName());
        viewHolder.anum.setText(((AddActivity)context).applience.get(i).getNum());
        viewHolder.adur.setText(((AddActivity)context).applience.get(i).getDur());
        viewHolder.awatt.setText(((AddActivity)context).applience.get(i).getDur());




        viewHolder.aname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).applience.get(i).setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.awatt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).applience.get(i).setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.anum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).applience.get(i).setNum(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.adur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).applience.get(i).setDur(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return ((AddActivity)context).applience.size();
    }

    public void removeItem(int position) {
        ((AddActivity)context).applience.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Appl item, int position) {
        ((AddActivity)context).applience.add(position, item);
        notifyItemInserted(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ExpandableCardView app_item;

        private AppCompatEditText aname;
        private AppCompatEditText anum;
        private AppCompatEditText adur;
        private AppCompatEditText awatt;

        public ViewHolder(View view) {
            super(view);

            app_item = (ExpandableCardView) view.findViewById(R.id.app_item);

            aname = (AppCompatEditText) view.findViewById(R.id.aname);
            anum = (AppCompatEditText) view.findViewById(R.id.anumbers);
            adur = (AppCompatEditText) view.findViewById(R.id.adur);
            awatt = (AppCompatEditText) view.findViewById(R.id.awatt);


        }
    }

}