package in.nullify.survey;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

public class SigninActivity extends AppCompatActivity {
    private EditText et_pwd;
    private EditText et_uname;
    private Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        et_uname = (EditText) findViewById(R.id.et_uname);
        et_pwd = (EditText) findViewById(R.id.et_pwd);



        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nullify.in/survey/api/signin?";
                url = url+"uname="+et_uname.getText()+"&upwd="+et_pwd.getText();
                Log.e("URL",url);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
             StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.valueOf(response)) {
                            //Toast.makeText(SigninActivity.this, "Success" + response, Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(SigninActivity.this, "Failed "+response, Toast.LENGTH_SHORT).show();
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
        });

    }
}
