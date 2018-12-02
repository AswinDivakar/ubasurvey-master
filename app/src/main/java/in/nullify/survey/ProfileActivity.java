package in.nullify.survey;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ProfileActivity extends AppCompatActivity {

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
    private DataListAdapter dataListAdapter;
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

    private FamilyListAdapter familyListAdapter;
    private AppliancesAdapter appliancesAdapter;
    private CropListAdapter cropListAdapter;
    private ProblemsAdapter problemsAdapter;

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


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        id = getIntent().getExtras().getString("id");


        family_list = (RecyclerView) findViewById(R.id.familty_list);
        family_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        family_list.setLayoutManager(mLayoutManager);
        familyListAdapter = new FamilyListAdapter(ProfileActivity.this);
        family_list.setAdapter(familyListAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperFamily(0, ItemTouchHelper.LEFT,
                new RecyclerItemTouchHelperFamily.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Family deletedItem = familymebers.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        familyListAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage(
                                "Do you want to delete this family member?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                Deletemember(deletedItem.getId());
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


        for (int i = 0; i < schemes.length; i++)
            schemeslist.add("");


        schemes_list = (RecyclerView) findViewById(R.id.schemes_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        schemes_list.setLayoutManager(mLayoutManager);
        dataListAdapter = new DataListAdapter(ProfileActivity.this, schemes);
        schemes_list.setAdapter(dataListAdapter);

        app_list = (RecyclerView) findViewById(R.id.app_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        app_list.setLayoutManager(mLayoutManager);
        appliancesAdapter = new AppliancesAdapter(ProfileActivity.this);
        app_list.setAdapter(appliancesAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2 = new RecyclerviewItemTouchHelperApp(0, ItemTouchHelper.LEFT,
                new RecyclerviewItemTouchHelperApp.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Appl deletedItem = applience.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        appliancesAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage(
                                "Do you want to delete this Appliance?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                DeleteApp(deletedItem.getId());
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
        cropListAdapter = new CropListAdapter(ProfileActivity.this);
        agg_list.setAdapter(cropListAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback3 = new RecyclerviewItemTouchHelperCrop(0, ItemTouchHelper.LEFT,
                new RecyclerviewItemTouchHelperCrop.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Crop deletedItem = crops.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        cropListAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage(
                                "Do you want to delete this Crop?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                DeleteCrop(deletedItem.getId());
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
        problemsAdapter = new ProblemsAdapter(ProfileActivity.this);
        prob_list.setAdapter(problemsAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback4 = new RecyclerItemTouchHelperProb(0, ItemTouchHelper.LEFT,
                new RecyclerItemTouchHelperProb.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        final Prob deletedItem = probs.get(viewHolder.getAdapterPosition());
                        final int deletedIndex = viewHolder.getAdapterPosition();
                        problemsAdapter.removeItem(viewHolder.getAdapterPosition());

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage(
                                "Do you want to delete this Problem?")
                                .setCancelable(false)
                                .setPositiveButton("ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                DeleteProb(deletedItem.getId());
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

        getData();
    }

    private void DeleteApp(String id) {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/deleteAppMember?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void DeleteCrop(String id) {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/deleteCropMember?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void DeleteProb(String id) {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/deleteProbMember?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void InsertApp() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/insertAppEdit?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void InsertCrop() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/insertCropEdit?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void InsertProb() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/insertProbEdit?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void setRespondantProfile(JSONArray array) {


        rname = (AppCompatEditText) findViewById(R.id.rname);
        rgender = (Spinner) findViewById(R.id.rgender);
        rage = (AppCompatEditText) findViewById(R.id.rage);
        rrelatn = (AppCompatEditText) findViewById(R.id.rrel);
        rnumber = (AppCompatEditText) findViewById(R.id.rno);
        ridt = (AppCompatEditText) findViewById(R.id.ridtype);
        ridn = (AppCompatEditText) findViewById(R.id.ridno);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rgender.setAdapter(aa);

        JSONObject c = null;
        try {
            c = array.getJSONObject(0);
            resid = c.getString("r_id");
            rname.setText(c.getString("name"));
            rage.setText(c.getString("age"));
            rrelatn.setText(c.getString("relation_head"));
            rnumber.setText(c.getString("contact_no"));
            ridt.setText(c.getString("id_card_type"));
            ridn.setText(c.getString("id_card_no"));
            int i = gender.indexOf(c.getString("gender"));
            rgender.setSelection(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        filledby.setText(house.get("schedule_filled"));
        date.setText(house.get("date_of_survey"));

        cows.setText(house.get("cows"));
        bff.setText(house.get("buffalo"));
        sheep.setText(house.get("goat_sheep"));
        calve.setText(house.get("calves"));
        bull.setText(house.get("bullocks"));
        duck.setText(house.get("poultry_ducks"));
        other.setText(house.get("others"));
        milk.setText(house.get("avg_milk_production"));
        animwaste.setText(house.get("animal_waste"));

        chemfer.setText(house.get("chem_fert"));
        chemin.setText(house.get("chem_insect"));
        chemwee.setText(house.get("chem_weedicide"));
        chemorg.setText(house.get("organic_manures"));

        ttotal.setText(house.get("total"));
        tirr.setText(house.get("irrigated_area"));
        tbarr.setText(house.get("waste_area"));
        tcult.setText(house.get("cultivable_area"));
        tunirr.setText(house.get("unirrigated_area"));
        tuncult.setText(house.get("uncultivable_area"));

        epday.setText(house.get("elec_availability"));
        elight.setText(house.get("lighting"));
        ecook.setText(house.get("cooking"));
        echullah.setText(house.get("cook_chullah"));

        pwater.setText(house.get("piped_water"));
        pcomm.setText(house.get("comm_water_tape"));
        phand.setText(house.get("hand_pump"));
        powell.setText(house.get("open_well"));
        pmode.setText(house.get("mode_wtr_strg"));
        pother.setText(house.get("any_other_source"));

        village.setText(house.get("village"));
        gp.setText(house.get("gram_panch"));
        dis.setText(house.get("district"));
        ward.setText(house.get("ward_no"));
        block.setText(house.get("block"));
        state.setText(house.get("state"));

        ghouseid.setText(house.get("household_id"));
        gheadname.setText(house.get("name_of_head"));
        gincome.setText(house.get("income"));

        mmigratec.setText(house.get("migrate_no"));
        mmigrated.setText(house.get("fam_migrate_days"));
        mmigratey.setText(house.get("years_migration"));

        int i = livestock.indexOf(house.get("shelter_livestock"));
        livestk.setSelection(i);

        i = irrigation.indexOf(house.get("irrigation"));
        irr.setSelection(i);

        i = irrigations.indexOf(house.get("irrigation_sm"));
        irrs.setSelection(i);


        i = yesno.indexOf(house.get("elec_cnctn"));
        esource.setSelection(i);

        i = gender.indexOf(house.get("gender"));
        ggender.setSelection(i);

        i = yesno.indexOf(house.get("does_migrate"));
        mmigrate.setSelection(i);


        i = category.indexOf(house.get("category"));
        gcat.setSelection(i);

        i = poverty.indexOf(house.get("poverty_status"));
        gpov.setSelection(i);

        i = yesno.indexOf(house.get("own_house"));
        gown.setSelection(i);

        i = housetype.indexOf(house.get("type_of_house"));
        gtype.setSelection(i);

        i = toilet.indexOf(house.get("toilet"));
        gtoi.setSelection(i);

        i = drainage.indexOf(house.get("drainage_link_house"));
        gdrai.setSelection(i);

        i = wastecoll.indexOf(house.get("waste_coll_sys"));
        gwaste.setSelection(i);

        i = compost.indexOf(house.get("compost_pit"));
        gcompost.setSelection(i);

        i = biogass.indexOf(house.get("biogas"));
        gbio.setSelection(i);


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
                "date_of_survey",
                "saved"};

        final String[] vals = new String[]{id,
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
                date.getText().toString(), "1"};

        String url = "https://nullify.in/survey/api/savehouse?";

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

    private void Insertmember() {
       Family f = new Family();
       familymebers.add(f);
       familyListAdapter.notifyDataSetChanged();
    }

    private void Deletemember(String fid) {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/deleteFamilyMember?id=" + fid;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            getData();
                        } else {

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }

    private void getData() {

        probs.clear();
        crops.clear();
        applience.clear();
        skeyss.clear();
        schemeslist.clear();
        house.clear();
        familymebers.clear();
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        findViewById(R.id.cont).setVisibility(View.GONE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://nullify.in/survey/api/getHomeDetails?id=" + id;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("0")) {
                            Log.d("Res", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray array = jsonObject.getJSONArray("house");
                                JSONObject c = array.getJSONObject(0);
                                Iterator<String> iter = c.keys();

                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    if (!c.getString(key).equals(null))
                                        house.put(key, c.getString(key));
                                    else
                                        house.put(key, "bla");

                                }
                                setHouseHoldInfo();

                                array = jsonObject.getJSONArray("res");
                                setRespondantProfile(array);

                                array = jsonObject.getJSONArray("schemes");
                                if (array.length() > 0) {
                                    c = array.getJSONObject(0);
                                    Iterator<String> iter1 = c.keys();
                                    while (iter1.hasNext()) {
                                        String key = iter1.next();
                                        scheme.put(key, c.getString(key));
                                    }
                                    for (int i = 0; i < skeys.length; i++)
                                        schemeslist.add(scheme.get(skeys[i]));
                                }
                                dataListAdapter.notifyDataSetChanged();

                                array = jsonObject.getJSONArray("members");
                                familycount = array.length();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject cc = array.getJSONObject(i);
                                    Family f = new Family();
                                    f.setId(cc.getString("f_id"));
                                    f.setName(cc.getString("name"));
                                    f.setAge(cc.getString("age"));
                                    f.setGender(cc.getString("sex"));
                                    f.setMarital_status(cc.getString("martial_status"));
                                    f.setEducation_level(cc.getString("level_of_edu"));
                                    f.setGo_school(cc.getString("gng_awc_school_college"));
                                    f.setAdhaar(cc.getString("adhar_card"));
                                    f.setBankac(cc.getString("bank_ac"));
                                    f.setComplit(cc.getString("computer_literature"));
                                    f.setSsp(cc.getString("social_security_pension"));
                                    f.setHealthpb(cc.getString("major_health_probs"));
                                    f.setMnrega(cc.getString("mnrega_job_card"));
                                    f.setShg(cc.getString("self_help_grps"));
                                    f.setOccupation(cc.getString("occupations_code"));
                                    familymebers.add(f);
                                }

                                familyListAdapter.notifyDataSetChanged();

                                array = jsonObject.getJSONArray("apps");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject cc = array.getJSONObject(i);
                                    Appl a = new Appl();
                                    a.setId(cc.getString("id"));
                                    a.setName(cc.getString("name"));
                                    a.setNum(cc.getString("number"));
                                    a.setWatt(cc.getString("awatt"));
                                    a.setDur(cc.getString("duration"));
                                    applience.add(a);
                                }

                                appliancesAdapter.notifyDataSetChanged();

                                array = jsonObject.getJSONArray("crops");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject cc = array.getJSONObject(i);
                                    Crop a = new Crop();
                                    a.setId(cc.getString("a_id"));
                                    a.setCrop(cc.getString("crop"));
                                    a.setPro(cc.getString("productivity"));
                                    a.setArea(cc.getString("area_previous_year"));
                                    crops.add(a);
                                }

                                cropListAdapter.notifyDataSetChanged();

                                array = jsonObject.getJSONArray("probs");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject cc = array.getJSONObject(i);
                                    Prob a = new Prob();
                                    a.setId(cc.getString("p_id"));
                                    a.setProb(cc.getString("problems"));
                                    a.setSol(cc.getString("suggestions"));
                                    probs.add(a);
                                }

                                problemsAdapter.notifyDataSetChanged();

                                findViewById(R.id.progress).setVisibility(View.GONE);
                                findViewById(R.id.cont).setVisibility(View.VISIBLE);
                                /*int listSize = familymebers.size();
                                for (int i = 0; i<listSize; i++){
                                    Log.e("Member name: ", familymebers.get(1).getAdhaar());
                                }*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
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

            String url = "https://nullify.in/survey/api/saveFamily?";

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
                                getData();
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

        String url = "https://nullify.in/survey/api/saveRespondant?";

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

            String url = "https://nullify.in/survey/api/saveAgriculture?";

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

            String url = "https://nullify.in/survey/api/saveAppliences?";

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

            String url = "https://nullify.in/survey/api/saveProblems?";

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

        String url = "https://nullify.in/survey/api/saveSchemes?";

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
                saveRes();
                saveHome();
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
