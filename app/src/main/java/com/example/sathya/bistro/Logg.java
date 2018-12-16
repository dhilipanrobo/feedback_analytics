package com.example.sathya.bistro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class Logg extends AppCompatActivity implements View.OnClickListener {
    Button bt3;
    EditText et1, et2;
    String[] error, mzone_status,mzid,mcid,k,msms,mimg,w_id,b_id,res_id,top_img,sms_states;
    String user, pass, ownerid, STname, STpass, cid, iid,url_img;
    constant mcon=new constant();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logg);
        bt3 = findViewById(R.id.log);
        et1 = findViewById(R.id.user);
        et2 = findViewById(R.id.pwd);
        bt3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(isNetworkAvailable ()){ }else{
            Toast.makeText ( this, "Check Internet Connection", Toast.LENGTH_SHORT ).show ( );
        }
        if ((et1.getText().length() != 0) && (et2.getText().length() != 0)) {
            user = et1.getText().toString();
            pass = et2.getText().toString();
            log();
        } else {

            if ((et1.getText().length() != 0)) {
                Toast.makeText(this, "Password Field is Empty", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Username Field is Empty", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void log () {
        STpass = et2.getText().toString();
        STname = et1.getText().toString();
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, mcon.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText (MainActivity.this, "res: "+response, Toast.LENGTH_LONG ).show ( );
                try {
                    loadIntoListViewlog("[" + response + "]");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", STname);
                params.put("password", STpass);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    void sms () {
        STpass = et2.getText().toString();
        STname = et1.getText().toString();
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST,Url.sms, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText (MainActivity.this, "res: "+response, Toast.LENGTH_LONG ).show ( );
                try {
                    loadIntoListView_sms(  response );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization",constant.idapi);
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }
    void img () {
        STpass = et2.getText().toString();
        STname = et1.getText().toString();
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.img, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText (MainActivity.this, "res: "+response, Toast.LENGTH_LONG ).show ( );
                try {
                    loadIntoListView_img( response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", constant.idapi);
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }


    private void loadIntoListViewlog(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);

        k = new String[jsonArray.length()];
        error=new String[jsonArray.length ()];

        mzid=new String[jsonArray.length ()];
        mcid=new String[jsonArray.length ()];
        w_id=new String[jsonArray.length ()];
        b_id=new String[jsonArray.length ()];
        res_id=new String[jsonArray.length ()];





        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            k[i] = obj.getString("api_key");
            error[i]=obj.getString ( "error" );
            w_id[i]=obj.getString ( "waiter_id" );
            b_id[i]=obj.getString ( "branch_id" );
            res_id[i]=obj.getString ( "res_id" );

constant.mres_id=res_id[i];
            // mcid[i]=obj.getString ( "c_id" );

        }
        for (int i = 0; i < jsonArray.length(); i++) {

            check ( error[i],k[i]);



        }


constant.waiter_id=w_id[0];constant.branch_id=b_id[0];




    }
    private void loadIntoListView_sms(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);

        msms = new String[jsonArray.length()];
        sms_states = new String[jsonArray.length()];





        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            msms[i] = obj.getString("message");
            sms_states[i] = obj.getString("sms_status");




        }

        constant.sms_msg=msms[0];
        constant.sms_states=sms_states[0];
        Toast.makeText ( this, "vv"+constant.sms_states, Toast.LENGTH_SHORT ).show ( );




    }
    private void loadIntoListView_img(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);

        mimg = new String[jsonArray.length()];
        top_img = new String[jsonArray.length()];





        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            mimg[i] = obj.getString("bottom_img_url");
            top_img[i] = obj.getString("top_img_url");

           // Toast.makeText(this, "m:"+mimg[i], Toast.LENGTH_SHORT).show();
            Url.img_url=mimg[i];
            Url.top_img_url=top_img[i];

        }


        Intent v=new Intent(this,Star_rating.class);
        startActivity(v);
        finish();






    }

    private void check(String con,String key){

        // Toast.makeText(this, "con"+con, Toast.LENGTH_SHORT).show();
        STpass=et2.getText ().toString ();
        STname=et1.getText ().toString ();
        if(con.equals ( "false" )) {


            SharedPreferences sharedPreferences = getSharedPreferences("userlog", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            SharedPreferences.Editor logid=sharedPreferences.edit();
            editor.putString("api_key",key);

           constant.idapi=key;


            editor.putString("username",STname);
            editor.putString("url_img",constant.img_url);
            //editor.putString("zoenid",Z_id);
            //editor.putString("zcid",cid.toString ());
            logid.putString ( "pass",STpass );
            logid.putString("username",STname);
            editor.apply();
            logid.apply ();
            logid.commit ();

            editor.commit();

          //  Toast.makeText(this, "img::"+Url.img_url, Toast.LENGTH_SHORT).show();
            String img=constant.img_url;



sms();
img();
          //  Url.img_url=mimg[0];;
          //  editor.putString("img_url",Url.img_url);
           // Toast.makeText(this, ""+Url.img_url, Toast.LENGTH_SHORT).show();



            //startActivity(v);
        }
        else if(con.equals ( "true" )){
            Toast.makeText ( this, "UnAuthorized User", Toast.LENGTH_SHORT ).show ( );
        }
        else{

            Toast.makeText ( this, "Activated Zone", Toast.LENGTH_SHORT ).show ( );
        }

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

}


