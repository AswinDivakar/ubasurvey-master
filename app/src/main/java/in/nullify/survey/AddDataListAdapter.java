package in.nullify.survey;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Abhishekpalodath on 28-09-2018.
 */

public class AddDataListAdapter extends RecyclerView.Adapter<AddDataListAdapter.ViewHolder> {
    private String[] column;
    private Activity context;

    public AddDataListAdapter(Activity context, String[] column) {
        this.context = context;
        this.column = column;
    }

    @Override
    public AddDataListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schemes_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddDataListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.coulmn.setText(column[i]);
        if (i==0)
            viewHolder.value.setText("കേരളം");
        viewHolder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((AddActivity)context).schemeslist.set(i,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return column.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView coulmn;
        private EditText value;

        public ViewHolder(View view) {
            super(view);

            coulmn = (TextView) view.findViewById(R.id.column_name);
            value = (EditText) view.findViewById(R.id.data);
        }
    }

}