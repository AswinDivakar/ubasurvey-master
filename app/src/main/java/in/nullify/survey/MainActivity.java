package in.nullify.survey;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView survey_list, search_list;
    private Boolean loading = false;
    private List<SurveyHome> surveys = new ArrayList<>();
    private List<SurveyHome> search = new ArrayList<>();
    private SurveyListAdapter surveyListAdapter;
    private SurveyListAdapter surveyListAdapter2;
    private Toolbar toolbar;
    private static final int MAX_ITEMS_PER_REQUEST = 20;
    private static final int NUMBER_OF_ITEMS = 100;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1500;
    private View footerView;
    private ArrayList<String> names = new ArrayList<>();
    private AutoCompleteTextView search_event;
    private ArrayAdapter<String> adaptera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button btn_adduser = (Button) findViewById(R.id.adduser);
        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview, null, false);
        survey_list = (ListView) findViewById(R.id.survey_list);
        surveyListAdapter = new SurveyListAdapter(this, surveys);
        survey_list.setAdapter(surveyListAdapter);

        search_list = (ListView) findViewById(R.id.search_list);
        surveyListAdapter2 = new SurveyListAdapter(this, search);
        search_list.setAdapter(surveyListAdapter2);


        search_event = (AutoCompleteTextView) findViewById(R.id.search_event);
        adaptera = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
        search_event.setAdapter(adaptera);
        search_event.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                    findViewById(R.id.search_cont).setVisibility(View.GONE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        search_event.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    if (!(v.getText().toString().equals(""))) {
                        findViewById(R.id.search_cont).setVisibility(View.VISIBLE);
                        getSearch(v.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

        getHouses(0);

        survey_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (!loading && surveys.size()>=15)
                        getHouses(surveys.size() - 1);
                }
            }
        });


        survey_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("id", surveys.get(position).getId());
                startActivity(intent);
            }
        });

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("id", search.get(position).getId());
                startActivity(intent);
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_data);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
    }

    private void getSearch(final String text) {
        search.clear();
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "http://nullify.in/survey/api/getSearch?query="+text;

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        if (!response.equals("0")) {
                            findViewById(R.id.noresult).setVisibility(View.GONE);
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject c = array.getJSONObject(i);
                                    SurveyHome s = new SurveyHome();
                                    s.setId(c.getString("id"));
                                    s.setHouseno(c.getString("household_id"));
                                    s.setOwner(c.getString("name"));
                                    s.setWardno(c.getString("ward_no"));
                                    s.setVillage(c.getString("village"));
                                    s.setGrampanch(c.getString("gram_panch"));
                                    s.setDistrict(c.getString("district"));
                                    s.setState(c.getString("state"));
                                    search.add(s);
                                }
                                surveyListAdapter2.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            findViewById(R.id.noresult).setVisibility(View.VISIBLE);
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

    private void getHouses(final int id) {
        surveys.clear();
        findViewById(R.id.reconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHouses(id);
                return;
            }
        });

        if (!isNetworkAvailable()) {
            findViewById(R.id.connect).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.connect).setVisibility(View.GONE);
            survey_list.addFooterView(footerView);
            loading = true;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String URL = "https://nullify.in/survey/api/getHouses?id="+id;

            StringRequest postRequest = new StringRequest(Request.Method.GET, URL,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            survey_list.removeFooterView(footerView);
                            loading = false;
                            if (!response.equals("0")) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject c = array.getJSONObject(i);
                                        SurveyHome s = new SurveyHome();
                                        s.setId(c.getString("id"));
                                        s.setHouseno(c.getString("household_id"));
                                        names.add(c.getString("household_id"));
                                        s.setOwner(c.getString("name"));
                                        s.setWardno(c.getString("ward_no"));
                                        s.setVillage(c.getString("village"));
                                        s.setGrampanch(c.getString("gram_panch"));
                                        s.setDistrict(c.getString("district"));
                                        s.setState(c.getString("state"));
                                        surveys.add(s);
                                    }
                                    surveyListAdapter.notifyDataSetChanged();
                                    adaptera.notifyDataSetChanged();
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
                    });

            queue.add(postRequest);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
