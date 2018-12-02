package in.nullify.survey;

import android.app.Activity;
import android.content.ClipData;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;

import java.util.ArrayList;

/**
 * Created by Abhishekpalodath on 06-11-2018.
 */

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<String> yesno = new ArrayList<>();
    private ArrayList<String> involve = new ArrayList<>();
    private ArrayList<String> gender = new ArrayList<>();

    public FamilyListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public FamilyListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.family_member_list_style, viewGroup, false);
        return new FamilyListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FamilyListAdapter.ViewHolder viewHolder, final int i) {

        gender.clear();
        yesno.clear();
        involve.clear();

        gender.add("Male");
        gender.add("Female");
        gender.add("Other");

        yesno.add("Yes");
        yesno.add("No");

        involve.add("Involved");
        involve.add("Not Involved");

        viewHolder.familty_item.setTitle("Family Member "+(i+1));

        viewHolder.fname.setText(((ProfileActivity)context).familymebers.get(i).getName());
        viewHolder.fage.setText(((ProfileActivity)context).familymebers.get(i).getAge());
        viewHolder.fmar.setText(((ProfileActivity)context).familymebers.get(i).getMarital_status());
        viewHolder.fedu.setText(((ProfileActivity)context).familymebers.get(i).getEducation_level());
        viewHolder.fgo.setText(((ProfileActivity)context).familymebers.get(i).getGo_school());
        viewHolder.fadhaar.setText(((ProfileActivity)context).familymebers.get(i).getAdhaar());
        viewHolder.fbankac.setText(((ProfileActivity)context).familymebers.get(i).getBankac());
        viewHolder.fssp.setText(((ProfileActivity)context).familymebers.get(i).getSsp());
        viewHolder.fhealth.setText(((ProfileActivity)context).familymebers.get(i).getHealthpb());
        viewHolder.focc.setText(((ProfileActivity)context).familymebers.get(i).getOccupation());

        ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.fgender.setAdapter(aa);

        aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, yesno);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.fcomp.setAdapter(aa);

        aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, yesno);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.fmnrega.setAdapter(aa);

        aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, involve);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.fshg.setAdapter(aa);

        int pos = gender.indexOf(((ProfileActivity)context).familymebers.get(i).getGender());
        viewHolder.fgender.setSelection(pos);

        pos = yesno.indexOf(((ProfileActivity)context).familymebers.get(i).getComplit());
        viewHolder.fcomp.setSelection(pos);

        pos = yesno.indexOf(((ProfileActivity)context).familymebers.get(i).getMnrega());
        viewHolder.fmnrega.setSelection(pos);

        pos = involve.indexOf(((ProfileActivity)context).familymebers.get(i).getShg());
        viewHolder.fshg.setSelection(pos);


        viewHolder.fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setAge(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fmar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setMarital_status(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fedu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setEducation_level(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fgo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setGo_school(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setAdhaar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fbankac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setBankac(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fssp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setSsp(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.fhealth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setHealthpb(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.focc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ProfileActivity)context).familymebers.get(i).setOccupation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.fgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ProfileActivity)context).familymebers.get(i).setGender(parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewHolder.fcomp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ProfileActivity)context).familymebers.get(i).setComplit(parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewHolder.fmnrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ProfileActivity)context).familymebers.get(i).setMnrega(parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewHolder.fshg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ProfileActivity)context).familymebers.get(i).setShg(parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ((ProfileActivity)context).familymebers.size();
    }

    public void removeItem(int position) {
        ((ProfileActivity)context).familymebers.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Family item, int position) {
        ((ProfileActivity)context).familymebers.add(position, item);
        notifyItemInserted(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ExpandableCardView familty_item;

        private AppCompatEditText fname;
        private AppCompatEditText fage;
        private AppCompatEditText fmar;
        private AppCompatEditText fedu;
        private AppCompatEditText fgo;
        private AppCompatEditText fadhaar;
        private AppCompatEditText fbankac;
        private AppCompatEditText fssp;
        private AppCompatEditText fhealth;
        private AppCompatEditText focc;

        private Spinner fgender;
        private Spinner fcomp;
        private Spinner fmnrega;
        private Spinner fshg;


        public ViewHolder(View view) {
            super(view);

            familty_item = (ExpandableCardView) view.findViewById(R.id.familty_item);

            fname = (AppCompatEditText) view.findViewById(R.id.fname);
            fage = (AppCompatEditText) view.findViewById(R.id.fage);
            fmar = (AppCompatEditText) view.findViewById(R.id.fmar);
            fedu = (AppCompatEditText) view.findViewById(R.id.fedu);
            fgo = (AppCompatEditText) view.findViewById(R.id.fgo);
            fadhaar = (AppCompatEditText) view.findViewById(R.id.fadhaar);
            fbankac = (AppCompatEditText) view.findViewById(R.id.fbank);
            fssp = (AppCompatEditText) view.findViewById(R.id.fsocial);
            fhealth = (AppCompatEditText) view.findViewById(R.id.fmajor);
            focc = (AppCompatEditText) view.findViewById(R.id.foccu);

            fgender = (Spinner) view.findViewById(R.id.fgender);
            fcomp = (Spinner) view.findViewById(R.id.fcomp);
            fmnrega = (Spinner) view.findViewById(R.id.fmnrega);
            fshg = (Spinner) view.findViewById(R.id.fself);

        }
    }

}