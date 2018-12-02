package in.nullify.survey;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;

/**
 * Created by Abhishekpalodath on 08-11-2018.
 */

public class CropListAdapter extends RecyclerView.Adapter<CropListAdapter.ViewHolder> {
    private Activity context;

    public CropListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public CropListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agg_list_style, viewGroup, false);
        return new CropListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CropListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.agg_item.setTitle("Crop "+(i+1));

        viewHolder.name.setText(((ProfileActivity)context).crops.get(i).getCrop());
        viewHolder.area.setText(((ProfileActivity)context).crops.get(i).getArea());
        viewHolder.pro.setText(((ProfileActivity)context).crops.get(i).getPro());




        viewHolder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).crops.get(i).setCrop(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).crops.get(i).setArea(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.pro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).crops.get(i).setPro(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return ((ProfileActivity)context).crops.size();
    }

    public void removeItem(int position) {
        ((ProfileActivity)context).crops.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Crop item, int position) {
        ((ProfileActivity)context).crops.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatEditText name;
        private AppCompatEditText area;
        private AppCompatEditText pro;

        private ExpandableCardView agg_item;

        public ViewHolder(View view) {
            super(view);

            agg_item = (ExpandableCardView) view.findViewById(R.id.agg_item);

            name = (AppCompatEditText) view.findViewById(R.id.cname);
            area = (AppCompatEditText) view.findViewById(R.id.carea);
            pro = (AppCompatEditText) view.findViewById(R.id.cpro);
        }
    }

}