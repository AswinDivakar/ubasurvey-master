package in.nullify.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Abhishekpalodath on 05-08-2018.
 */

public class SurveyListAdapter extends ArrayAdapter<SurveyHome> {
    private List<SurveyHome> surveys;
    private Context mContext;

    public SurveyListAdapter(Context context, List<SurveyHome> surveys) {
        super(context, R.layout.survey_list_item, surveys);
        this.surveys = surveys;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.survey_list_item, null);
        }
        TextView house_name = (TextView) v.findViewById(R.id.house_no);
        TextView owner = (TextView) v.findViewById(R.id.owner_name);
        TextView adress = (TextView) v.findViewById(R.id.address);

        house_name.setText(surveys.get(position).getHouseno());
        owner.setText("Owner's Name : "+surveys.get(position).getOwner());
        adress.setText("Ward Number : "+surveys.get(position).getWardno()+"\n"+surveys.get(position).getVillage()+" ,"+
                        surveys.get(position).getGrampanch()+" Panchayath ,"
                        +surveys.get(position).getDistrict()+" ,"+surveys.get(position).getState());
        return  v;
    }

    @Override
    public int getCount() {
        return surveys.size();
    }
}
