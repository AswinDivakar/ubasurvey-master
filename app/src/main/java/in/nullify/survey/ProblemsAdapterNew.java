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

/**
 * Created by Abhishekpalodath on 08-11-2018.
 */

public class ProblemsAdapterNew extends RecyclerView.Adapter<ProblemsAdapterNew.ViewHolder> {
    private Activity context;

    public ProblemsAdapterNew(Activity context) {
        this.context = context;
    }

    @Override
    public ProblemsAdapterNew.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.problem_list_style, viewGroup, false);
        return new ProblemsAdapterNew.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProblemsAdapterNew.ViewHolder viewHolder, final int i) {

        viewHolder.prob_item.setTitle("Problem "+(i+1));

        viewHolder.prob.setText(((AddActivity)context).probs.get(i).getProb());
        viewHolder.sol.setText(((AddActivity)context).probs.get(i).getSol());




        viewHolder.prob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).probs.get(i).setProb(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.sol.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).probs.get(i).setSol(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return ((AddActivity)context).probs.size();
    }

    public void removeItem(int position) {
        ((AddActivity)context).probs.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Prob item, int position) {
        ((AddActivity)context).probs.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatEditText prob;
        private AppCompatEditText sol;

        private ExpandableCardView prob_item;

        public ViewHolder(View view) {
            super(view);

            prob_item = (ExpandableCardView) view.findViewById(R.id.prob_item);

            prob = (AppCompatEditText) view.findViewById(R.id.prob);
            sol = (AppCompatEditText) view.findViewById(R.id.sug);
        }
    }

}