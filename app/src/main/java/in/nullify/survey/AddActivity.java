package in.nullify.survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private RecyclerView schemes_list, family_list, app_list, agg_list, prob_list;
    public ArrayList<String> values = new ArrayList<>();
    public ArrayList<Family> familymebers = new ArrayList<>();
    private String resid = new String(), schemeid = new String();
    public ArrayList<Appl> applience = new ArrayList<>();
    public ArrayList<Crop> crops = new ArrayList<>();
    public ArrayList<Prob> probs = new ArrayList<>();

    public ArrayList<String> schemeslist = new ArrayList<>();
    public ArrayList<String> skeyss = new ArrayList<>();
    private AppCompatEditText rname, rage, rrelatn, rnumber, ridt, ridn,
            ghouseid, gheadname, gincome,
            village, gp, ward, block, dis, state,
            mmigratec, mmigrated, mmigratey,
            pwater, pcomm, phand, powell, pmode, pother,
            epday, elight, ecook, echullah,
            ttotal, tirr, tbarr, tcult, tunirr, tuncult,
            chemfer, chemin, chemwee, chemorg,
            cows, bff, sheep, calve, bull, duck, other, milk, animwaste,
            filledby, date;
    private Spinner rgender,
            ggender, gcat, gpov, gown, gtype, gtoi, gdrai, gwaste, gcompost, gbio, mmigrate,
            esource,
            irr, irrs,
            livestk;
    String str = "";
    private DataListAdapterNew dataListAdapter;
    private String id;
    private ProgressDialog progressDialog;

    String[] schemes = new String[]{"PM jan Dhan Yojana", "PM Ujjwala Yojana", "PM Awas Yojana", "Sukanya Samridhi Yojana", "Mudra Yojana", "PM Jivan Jyoti Bima Yojana", "PM Suraksha Bima Yojana", "Atal Pension Yojana", "Fasal Bima Yojana", "Kaushal Vokas Yojana", "Krishi Sinchai Yojana", "Jan Aushadi Yojana", "Swachh Bharat Mission Toilet", "Soil Health Card", "Ladli Lakshmi Yojana", "Janani Suraksha Yojana", "Kisan Credit Card"};
    public String[] skeys = new String[]{"PMJDY", "PMUY", "PMAY", "SSY", "MY", "PMJJBY", "PMSBY", "APY", "FBY", "KVY", "KSY", "JAY", "SBMT", "SHC", "LLY", "JSY", "KCC"};

    private ArrayList<String> gender = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<String> poverty = new ArrayList<>();
    private ArrayList<String> yesno = new ArrayList<>();
    private ArrayList<String> housetype = new ArrayList<>();
    private ArrayList<String> toilet = new ArrayList<>();
    private ArrayList<String> drainage = new ArrayList<>();
    private ArrayList<String> wastecoll = new ArrayList<>();
    private ArrayList<String> compost = new ArrayList<>();
    private ArrayList<String> biogass = new ArrayList<>();
    private ArrayList<String> irrigation = new ArrayList<>();
    private ArrayList<String> irrigations = new ArrayList<>();
    private ArrayList<String> livestock = new ArrayList<>();

    private FamilyListAdapterNew familyListAdapter;
    private AppliancesAdapterNew appliancesAdapter;
    private CropListAdapterNew cropListAdapter;
    private ProblemsAdapterNew problemsAdapter;

    private int familycount;

    public HashMap<String, String> house = new HashMap<String, String>();
    public HashMap<String, String> scheme = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gender.add("Male");
        gender.add("Female");
        gender.add("Other");

        category.add("Gen");
        category.add("SC");
        category.add("ST");
        category.add("OBC");

        poverty.add("APL");
        poverty.add("BPL");

        yesno.add("Yes");
        yesno.add("No");

        housetype.add("Kutcha");
        housetype.add("Semi Pucca");
        housetype.add("Pucca");
        housetype.add("Homeless");

        toilet.add("Private");
        toilet.add("Community");
        toilet.add("Open Defecation");

        drainage.add("Covered");
        drainage.add("Open");
        drainage.add("None");

        wastecoll.add("Door Step");
        wastecoll.add("Common point");
        wastecoll.add("No Collection System");

        compost.add("Individual");
        compost.add("Group");
        compost.add("None");

        biogass.add("Individual");
        biogass.add("Group");
        biogass.add("Community");
        biogass.add("None");

        irrigation.add("Canal");
        irrigation.add("Tank");
        irrigation.add("Borewell");
        irrigation.add("River");
        irrigation.add("Other");
        irrigation.add("None");

        irrigations.add("Drip");
        irrigations.add("Sprinkler");
        irrigations.add("Flooding");
        irrigations.add("None");

        livestock.add("Pucca");
        livestock.add("Kutcha");
        livestock.add("Open");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setHouseHoldInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        //id = getIntent().getExtras().getString("id");


        family_list = (RecyclerView) findViewById(R.id.familty_list);
        family_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        family_list.setLayoutManager(mLayoutManager);
        familyListAdapter = new FamilyListAdapterNew(AddActivity.this);
        family_list.setAdapter(familyListAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperFamilyNew(0, ItemTouchHelper.LEFT,
                new RecyclerItemTouchHelperFamilyNew.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Family deletedItem = familymebers.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        familyListAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                        builder.setMessage(
                                "Do you want to delete this family member?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                            }
                                        })
                                .setNegativeButton("cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                familyListAdapter.restoreItem(deletedItem, deletedIndex);
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(family_list);

        Button add_member = (Button) findViewById(R.id.add_fam_member);
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insertmember();
            }
        });

        Button add_app = (Button) findViewById(R.id.add_app_member);
        add_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertApp();
            }
        });

        Button add_crop = (Button) findViewById(R.id.add_agg_crop);
        add_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertCrop();
            }
        });

        Button add_prob = (Button) findViewById(R.id.add_prob);
        add_prob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertProb();
            }
        });


        schemes_list = (RecyclerView) findViewById(R.id.schemes_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        schemes_list.setLayoutManager(mLayoutManager);
        dataListAdapter = new DataListAdapterNew(AddActivity.this, schemes);
        schemes_list.setAdapter(dataListAdapter);

        app_list = (RecyclerView) findViewById(R.id.app_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        app_list.setLayoutManager(mLayoutManager);
        appliancesAdapter = new AppliancesAdapterNew(AddActivity.this);
        app_list.setAdapter(appliancesAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2 = new RecyclerviewItemTouchHelperAppNew(0, ItemTouchHelper.LEFT,
                new RecyclerviewItemTouchHelperAppNew.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Appl deletedItem = applience.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        appliancesAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                        builder.setMessage(
                                "Do you want to delete this Appliance?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                //DeleteApp(deletedItem.getId());
                                            }
                                        })
                                .setNegativeButton("cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                appliancesAdapter.restoreItem(deletedItem, deletedIndex);
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


        new ItemTouchHelper(itemTouchHelperCallback2).attachToRecyclerView(app_list);

        agg_list = (RecyclerView) findViewById(R.id.agg_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        agg_list.setLayoutManager(mLayoutManager);
        cropListAdapter = new CropListAdapterNew(AddActivity.this);
        agg_list.setAdapter(cropListAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback3 = new RecyclerviewItemTouchHelperCropNew(0, ItemTouchHelper.LEFT,
                new RecyclerviewItemTouchHelperCropNew.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Crop deletedItem = crops.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        cropListAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                        builder.setMessage(
                                "Do you want to delete this Crop?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                //DeleteCrop(deletedItem.getId());
                                            }
                                        })
                                .setNegativeButton("cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                cropListAdapter.restoreItem(deletedItem, deletedIndex);
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


        new ItemTouchHelper(itemTouchHelperCallback3).attachToRecyclerView(agg_list);

        prob_list = (RecyclerView) findViewById(R.id.problem_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        prob_list.setLayoutManager(mLayoutManager);
        problemsAdapter = new ProblemsAdapterNew(AddActivity.this);
        prob_list.setAdapter(problemsAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback4 = new RecyclerItemTouchHelperProbNew(0, ItemTouchHelper.LEFT,
                new RecyclerItemTouchHelperProbNew.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Prob deletedItem = probs.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        problemsAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                        builder.setMessage(
                                "Do you want to delete this Problem?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                            }
                                        })
                                .setNegativeButton("cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                problemsAdapter.restoreItem(deletedItem, deletedIndex);
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


        new ItemTouchHelper(itemTouchHelperCallback4).attachToRecyclerView(prob_list);

        //
    }



    private void InsertApp() {
        Appl a=new Appl();
        applience.add(a);
        appliancesAdapter.notifyDataSetChanged();
    }

    private void InsertCrop() {
        Crop c=new Crop();
        crops.add(c);
        cropListAdapter.notifyDataSetChanged();
    }

    private void InsertProb() {
        Prob p=new Prob();
        probs.add(p);
        problemsAdapter.notifyDataSetChanged();
    }


    private void saveHome() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        findViewById(R.id.cont).setVisibility(View.GONE);
        final String[] keys = new String[]{"id",
                "village",
                "gram_panch",
                "ward_no",
                "block",
                "district",
                "state",
                "household_id",
                "name_of_head",
                "gender",
                "category",
                "poverty_status",
                "own_house",
                "type_of_house",
                "toilet",
                "drainage_link_house",
                "waste_coll_sys",
                "compost_pit",
                "biogas",
                "income",
                "does_migrate",
                "migrate_no",
                "fam_migrate_days",
                "years_migration",
                "piped_water",
                "comm_water_tape",
                "hand_pump",
                "open_well",
                "mode_wtr_strg",
                "any_other_source",
                "elec_cnctn",
                "elec_availability",
                "lighting",
                "cooking",
                "cook_chullah",
                "total",
                "cultivable_area",
                "irrigated_area",
                "unirrigated_area",
                "waste_area",
                "uncultivable_area",
                "chem_fert",
                "chem_insect",
                "chem_weedicide",
                "organic_manures",
                "irrigation",
                "irrigation_sm",
                "cows",
                "buffalo",
                "goat_sheep",
                "calves",
                "bullocks",
                "poultry_ducks",
                "others",
                "shelter_livestock",
                "avg_milk_production",
                "animal_waste",
                "schedule_filled",
                "date_of_survey"};

        final String[] vals = new String[]{"",
                village.getText().toString(),
                gp.getText().toString(),
                ward.getText().toString(),
                block.getText().toString(),
                dis.getText().toString(),
                state.getText().toString(),
                ghouseid.getText().toString(),
                gheadname.getText().toString(),
                ggender.getSelectedItem().toString(),
                gcat.getSelectedItem().toString(),
                gpov.getSelectedItem().toString(),
                gown.getSelectedItem().toString(),
                gtype.getSelectedItem().toString(),
                gtoi.getSelectedItem().toString(),
                gdrai.getSelectedItem().toString(),
                gwaste.getSelectedItem().toString(),
                gcompost.getSelectedItem().toString(),
                gbio.getSelectedItem().toString(),
                gincome.getText().toString(),
                mmigrate.getSelectedItem().toString(),
                mmigratec.getText().toString(),
                mmigrated.getText().toString(),
                mmigratey.getText().toString(),
                pwater.getText().toString(),
                pcomm.getText().toString(),
                phand.getText().toString(),
                powell.getText().toString(),
                pmode.getText().toString(),
                pother.getText().toString(),
                esource.getSelectedItem().toString(),
                epday.getText().toString(),
                elight.getText().toString(),
                ecook.getText().toString(),
                echullah.getText().toString(),
                ttotal.getText().toString(),
                tcult.getText().toString(),
                tirr.getText().toString(),
                tunirr.getText().toString(),
                tbarr.getText().toString(),
                tuncult.getText().toString(),
                chemfer.getText().toString(),
                chemin.getText().toString(),
                chemwee.getText().toString(),
                chemorg.getText().toString(),
                irr.getSelectedItem().toString(),
                irrs.getSelectedItem().toString(),
                cows.getText().toString(),
                bff.getText().toString(),
                sheep.getText().toString(),
                calve.getText().toString(),
                bull.getText().toString(),
                duck.getText().toString(),
                other.getText().toString(),
                livestk.getSelectedItem().toString(),
                milk.getText().toString(),
                animwaste.getText().toString(),
                filledby.getText().toString(),
                date.getText().toString()};

        String url = "https://nullify.in/survey/api/newhouse?";

        for (int i = 0; i < keys.length; i++) {
            if (i == 0)
                url = url + keys[i] + "=" + vals[i];
            else
                url = url + "&" + keys[i] + "=" + vals[i];
        }
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {

                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
        queue.add(postRequest);
    }

    private void setHouseHoldInfo() {

        filledby = (AppCompatEditText) findViewById(R.id.filledby);
        date = (AppCompatEditText) findViewById(R.id.date);

        cows = (AppCompatEditText) findViewById(R.id.cow);
        bff = (AppCompatEditText) findViewById(R.id.bff);
        sheep = (AppCompatEditText) findViewById(R.id.sheep);
        calve = (AppCompatEditText) findViewById(R.id.calves);
        bull = (AppCompatEditText) findViewById(R.id.bull);
        duck = (AppCompatEditText) findViewById(R.id.duck);
        other = (AppCompatEditText) findViewById(R.id.other);
        milk = (AppCompatEditText) findViewById(R.id.milk);
        animwaste = (AppCompatEditText) findViewById(R.id.animwaste);

        livestk = (Spinner) findViewById(R.id.shelter);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, livestock);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        livestk.setAdapter(aa);

        chemfer = (AppCompatEditText) findViewById(R.id.chemf);
        chemin = (AppCompatEditText) findViewById(R.id.chemin);
        chemwee = (AppCompatEditText) findViewById(R.id.chemwee);
        chemorg = (AppCompatEditText) findViewById(R.id.org);

        irr = (Spinner) findViewById(R.id.irr);
        irrs = (Spinner) findViewById(R.id.irrs);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, irrigation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        irr.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, irrigations);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        irrs.setAdapter(aa);


        ttotal = (AppCompatEditText) findViewById(R.id.tland);
        tirr = (AppCompatEditText) findViewById(R.id.tirr);
        tbarr = (AppCompatEditText) findViewById(R.id.tbarren);
        tcult = (AppCompatEditText) findViewById(R.id.tcult);
        tunirr = (AppCompatEditText) findViewById(R.id.tunirr);
        tuncult = (AppCompatEditText) findViewById(R.id.tucult);

        epday = (AppCompatEditText) findViewById(R.id.epday);
        elight = (AppCompatEditText) findViewById(R.id.elight);
        ecook = (AppCompatEditText) findViewById(R.id.ecook);
        echullah = (AppCompatEditText) findViewById(R.id.echullah);
        esource = (Spinner) findViewById(R.id.eectricity);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yesno);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        esource.setAdapter(aa);


        pwater = (AppCompatEditText) findViewById(R.id.pwater);
        pcomm = (AppCompatEditText) findViewById(R.id.pcomm);
        phand = (AppCompatEditText) findViewById(R.id.phand);
        powell = (AppCompatEditText) findViewById(R.id.powell);
        pmode = (AppCompatEditText) findViewById(R.id.pmode);
        pother = (AppCompatEditText) findViewById(R.id.pother);


        ghouseid = (AppCompatEditText) findViewById(R.id.ghouseid);
        gheadname = (AppCompatEditText) findViewById(R.id.ghouseheadname);
        gincome = (AppCompatEditText) findViewById(R.id.gincome);

        village = (AppCompatEditText) findViewById(R.id.village);
        gp = (AppCompatEditText) findViewById(R.id.gp);
        ward = (AppCompatEditText) findViewById(R.id.ward);
        block = (AppCompatEditText) findViewById(R.id.block);
        dis = (AppCompatEditText) findViewById(R.id.dis);
        state = (AppCompatEditText) findViewById(R.id.state);

        mmigrate = (Spinner) findViewById(R.id.mmigrate);
        mmigratec = (AppCompatEditText) findViewById(R.id.mmigratec);
        mmigrated = (AppCompatEditText) findViewById(R.id.mmigrated);
        mmigratey = (AppCompatEditText) findViewById(R.id.mmigratey);


        ggender = (Spinner) findViewById(R.id.ggender);
        gcat = (Spinner) findViewById(R.id.gcat);
        gpov = (Spinner) findViewById(R.id.gpov);
        gown = (Spinner) findViewById(R.id.gown);
        gtype = (Spinner) findViewById(R.id.gtype);
        gtoi = (Spinner) findViewById(R.id.gtoi);
        gdrai = (Spinner) findViewById(R.id.gdrainage);
        gwaste = (Spinner) findViewById(R.id.gwaste);
        gcompost = (Spinner) findViewById(R.id.gcomspost);
        gbio = (Spinner) findViewById(R.id.gbio);


        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ggender.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gcat.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, poverty);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gpov.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yesno);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gown.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, housetype);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gtype.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, toilet);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gtoi.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, drainage);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gdrai.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, wastecoll);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gwaste.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, compost);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gcompost.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, biogass);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gbio.setAdapter(aa);

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yesno);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mmigrate.setAdapter(aa);

    }

    private void Insertmember() {
        Family f = new Family();
        familymebers.add(f);
        familyListAdapter.notifyDataSetChanged();
    }



    private void saveFamily() {
        for (int i = 0; i < familymebers.size(); i++) {
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            findViewById(R.id.cont).setVisibility(View.GONE);
            final String[] keys = new String[]{
                    "f_id",
                    "h_id",
                    "name",
                    "age",
                    "sex",
                    "martial_status",
                    "level_of_edu",
                    "gng_awc_school_college",
                    "adhar_card",
                    "bank_ac",
                    "computer_literature",
                    "social_security_pension",
                    "major_health_probs",
                    "mnrega_job_card",
                    "self_help_grps",
                    "occupations_code"};

            final String[] vals = new String[]{familymebers.get(i).getId(),
                    id,
                    familymebers.get(i).getName(),
                    familymebers.get(i).getAge(),
                    familymebers.get(i).getGender(),
                    familymebers.get(i).getMarital_status(),
                    familymebers.get(i).getEducation_level(),
                    familymebers.get(i).getGo_school(),
                    familymebers.get(i).getAdhaar(),
                    familymebers.get(i).getBankac(),
                    familymebers.get(i).getComplit(),
                    familymebers.get(i).getSsp(),
                    familymebers.get(i).getHealthpb(),
                    familymebers.get(i).getMnrega(),
                    familymebers.get(i).getShg(),
                    familymebers.get(i).getOccupation(),
            };

            String url = "https://nullify.in/survey/api/newFamily?";

            for (int j = 0; j < keys.length; j++) {
                if (j == 0)
                    url = url + keys[j] + "=" + vals[j];
                else
                    url = url + "&" + keys[j] + "=" + vals[j];
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Boolean.valueOf(response)) {

                            } else {

                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    });
            queue.add(postRequest);
        }
    }

    private void saveRes() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        findViewById(R.id.cont).setVisibility(View.GONE);
        final String[] keys = new String[]{
                "r_id",
                "h_id",
                "name",
                "gender",
                "age",
                "relation_head",
                "contact_no",
                "id_card_type",
                "id_card_no"};

        final String[] vals = new String[]{resid,
                id,
                rname.getText().toString(),
                rgender.getSelectedItem().toString(),
                rage.getText().toString(),
                rrelatn.getText().toString(),
                rnumber.getText().toString(),
                ridt.getText().toString(),
                ridn.getText().toString()
        };

        String url = "https://nullify.in/survey/api/newRespondant?";

        for (int j = 0; j < keys.length; j++) {
            if (j == 0)
                url = url + keys[j] + "=" + vals[j];
            else
                url = url + "&" + keys[j] + "=" + vals[j];
        }
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
        queue.add(postRequest);
    }

    private void saveAgri() {
        for (int i = 0; i < crops.size(); i++) {
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            findViewById(R.id.cont).setVisibility(View.GONE);
            final String[] keys = new String[]{
                    "a_id",
                    "h_id",
                    "crop",
                    "area_previous_year",
                    "productivity"};

            final String[] vals = new String[]{crops.get(i).getId(),
                    id,
                    crops.get(i).getCrop(),
                    crops.get(i).getArea(),
                    crops.get(i).getPro()
            };

            String url = "https://nullify.in/survey/api/newAgriculture?";

            for (int j = 0; j < keys.length; j++) {
                if (j == 0)
                    url = url + keys[j] + "=" + vals[j];
                else
                    url = url + "&" + keys[j] + "=" + vals[j];
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Boolean.valueOf(response)) {
                            } else {

                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    });
            queue.add(postRequest);
        }
    }

    private void saveApp() {
        for (int i = 0; i < applience.size(); i++) {
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            findViewById(R.id.cont).setVisibility(View.GONE);
            final String[] keys = new String[]{
                    "id",
                    "h_id",
                    "name",
                    "number",
                    "awatt",
                    "duration"};

            final String[] vals = new String[]{applience.get(i).getId(),
                    id,
                    applience.get(i).getName(),
                    applience.get(i).getNum(),
                    applience.get(i).getWatt(),
                    applience.get(i).getDur()
            };

            String url = "https://nullify.in/survey/api/newAppliences?";

            for (int j = 0; j < keys.length; j++) {
                if (j == 0)
                    url = url + keys[j] + "=" + vals[j];
                else
                    url = url + "&" + keys[j] + "=" + vals[j];
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Boolean.valueOf(response)) {
                            } else {

                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    });
            queue.add(postRequest);
        }
    }

    private void saveProb() {
        for (int i = 0; i < probs.size(); i++) {
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            findViewById(R.id.cont).setVisibility(View.GONE);
            final String[] keys = new String[]{
                    "p_id",
                    "h_id",
                    "problems",
                    "suggestions"};

            final String[] vals = new String[]{probs.get(i).getId(),
                    id,
                    probs.get(i).getProb(),
                    probs.get(i).getSol()
            };

            String url = "https://nullify.in/survey/api/newProblems?";

            for (int j = 0; j < keys.length; j++) {
                if (j == 0)
                    url = url + keys[j] + "=" + vals[j];
                else
                    url = url + "&" + keys[j] + "=" + vals[j];
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Boolean.valueOf(response)) {
                            } else {

                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    });
            queue.add(postRequest);
        }
    }

    private void scheme() {

        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        findViewById(R.id.cont).setVisibility(View.GONE);
        final String[] keys = new String[]{
                "s_id",
                "h_id",
                "PMJDY",
                "PMUY",
                "PMAY",
                "SSY",
                "MY",
                "PMJJBY",
                "PMSBY",
                "APY",
                "FBY",
                "KVY",
                "KSY",
                "JAY",
                "SBMT",
                "SHC",
                "LLY",
                "JSY",
                "KCC"};

        String url = "https://nullify.in/survey/api/newSchemes?";

        for (int j = 0; j < keys.length; j++) {
            if (j == 0)
                url = url + keys[j] + "=" + scheme.get(keys[j]);
            else
                url = url + "&" + keys[j] + "=" + scheme.get(keys[j]);
        }
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
        queue.add(postRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon();
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        MenuItem save = menu.getItem(0);

        save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saveHome();
                //saveRes();
                saveAgri();
                saveApp();
                saveProb();
                scheme();
                saveFamily();
                return true;
            }
        });
        return true;
    }

}
