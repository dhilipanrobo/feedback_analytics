package com.example.sathya.bistro;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cos_detial extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    EditText cell, cos_name, date_birth, adate;
    Button submit;
    ImageView imageView,top;

    AlertDialog.Builder builder;
    DBhelper db = new DBhelper(this);
    String Mob;
    String[] error, cname, cdob, canv, wid, bid;
    Spinner mon,dat,yr,mon1,dat1,yr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cos_detial);
        cell = findViewById(R.id.editText);
        cos_name = findViewById(R.id.editText2);
        //date_birth = findViewById(R.id.editText4);
        //adate = findViewById(R.id.editText5);
        submit = findViewById(R.id.button2);
        submit.setOnClickListener(this);
        submit.setText("Submit");
        imageView = findViewById(R.id.imageView3);
        top=findViewById ( R.id.imageView4 );
        cos_name.setOnClickListener(this);
        show_image();
        show_image_Top ();
        builder = new AlertDialog.Builder(this);
        mon=findViewById ( R.id.month);
        mon.setOnItemSelectedListener ( this );
        dat=findViewById ( R.id.date );
        dat.setOnItemSelectedListener ( this );
        yr=findViewById ( R.id.year );
        yr.setOnItemSelectedListener ( this );
        mon1=findViewById ( R.id.month1 );
        mon1.setOnItemSelectedListener ( this );
        dat1=findViewById ( R.id.date1 );
        dat1.setOnItemSelectedListener ( this );
        yr1=findViewById ( R.id.year1 );
        yr1.setOnItemSelectedListener ( this );

cell.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Toast.makeText(cos_detial.this, "before", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Toast.makeText(cos_detial.this, "ontext", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(cell.length()==10){
        //    Toast.makeText ( cos_detial.this, "sgdjgsd", Toast.LENGTH_SHORT ).show ( );

            log();

    }

    }
});

        //Toast.makeText(this, "sms:"+constant.branch_id+"vv:"+constant.waiter_id, Toast.LENGTH_SHORT).show()
        List<String> month=new ArrayList<String> (  );
        month.add ( "Month" );
        month.add ( "01" );
        month.add ( "02" );
        month.add ( "03" );
        month.add ( "04" );
        month.add ( "05" );
        month.add ( "06" );
        month.add ( "07" );
        month.add ( "08" );
        month.add ( "09" );
        month.add ( "10" );
        month.add ( "11" );
        month.add ( "12" );

        List<String>date=new ArrayList<String> (  );
        date.add ( "Date" );
        date.add ( "01" );date.add ( "02" );date.add ( "03" );date.add ( "04" );date.add ( "05" );date.add ( "06" );
        date.add ( "07" );date.add ( "08" );date.add ( "09" );date.add ( "10" );date.add ( "11" );date.add ( "12" );
        date.add ( "13" );date.add ( "14" );date.add ( "15" );date.add ( "16" );date.add ( "17" );date.add ( "18" );
        date.add ( "19" );date.add ( "20" );date.add ( "21" );date.add ( "22" );date.add ( "23" );date.add ( "24" );
        date.add ( "25" );date.add ( "26" );date.add ( "27" );date.add ( "28" );date.add ( "29" );date.add ( "30" );
        date.add ( "31" );

        List<String>year=new ArrayList<String> (  );
        year.add ( "2018" );

        ArrayAdapter<String>madapter=new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, month);
        ArrayAdapter<String>dadapter=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,date  );
        ArrayAdapter<String>yadapter=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,year  );

        madapter.setDropDownViewResource (android.R.layout.simple_spinner_item);
        dadapter.setDropDownViewResource (android.R.layout.simple_spinner_item);
        yadapter.setDropDownViewResource (android.R.layout.simple_spinner_item);
        mon.setAdapter ( madapter );

        dat.setAdapter ( dadapter );
        yr.setAdapter ( yadapter );
        mon1.setAdapter ( madapter );
        dat1.setAdapter ( dadapter );
        yr1.setAdapter ( yadapter );
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String item=parent.getItemAtPosition (position).toString ();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.button2:
                constant.b_mon=mon.getSelectedItem ().toString ();
                constant.b_day=dat.getSelectedItem ().toString ();
                constant.A_mon=mon1.getSelectedItem ().toString ();
                constant.A_day=dat1.getSelectedItem ().toString ();
               // Toast.makeText ( this, "DOB"+constant.b_mon+""+constant.b_day, Toast.LENGTH_SHORT ).show ( );
             //   Toast.makeText ( this, "ANV"+constant.A_mon+""+constant.A_day, Toast.LENGTH_SHORT ).show ( );

                break;
        }


        if (cell.length() == 10) {
            constant.c_name = cos_name.getText().toString();
            //constant.b_date = date_birth.getText().toString();
            //constant.A_date = adate.getText().toString();
            //constant.b_date=mon.getSelectedItem ().toString ();
            constant.ph_no = cell.getText().toString();
          //  sms();

          dialog();



        } else {
            // Toast.makeText(this, "Only "+cell.length()+" Numbers", Toast.LENGTH_SHORT).show();
        }
    }

    void sms() {

        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.sms_url + cell.getText().toString() + Url.sms_url1 + constant.sms_msg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Toast.makeText(cos_detial.this, "res: " + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


        };
        Volley.newRequestQueue(this).add(request);

    }

    void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(cos_detial.this);


        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.ald, null);
        final EditText userAnswer = (EditText) dialoglayout.findViewById(R.id.editText);
        final EditText userAnswer1 = (EditText) dialoglayout.findViewById(R.id.editText1);
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            send_sms ();
                //  Toast.makeText(cos_detial.this, "Tee"+userAnswer.getText().toString(), Toast.LENGTH_SHORT).show();
if(constant.old_ch.equals ( "0" )){
    //Toast.makeText ( cos_detial.this, "new", Toast.LENGTH_SHORT ).show ( );


send_con ( userAnswer.getText ( ).toString ( ), userAnswer1.getText ( ).toString ( ) );
    send_cos_lsedger ( userAnswer.getText ( ).toString ( ), userAnswer1.getText ( ).toString ( ) );

    }else {

   // Toast.makeText ( cos_detial.this, "old", Toast.LENGTH_SHORT ).show ( );
    oldsend_con(userAnswer.getText().toString(), userAnswer1.getText().toString());
    send_cos_lsedger ( userAnswer.getText ( ).toString ( ), userAnswer1.getText ( ).toString ( ) );
}






                lisr();

                next_act();


            }
        });


        builder.setView(dialoglayout);

        builder.show();
    }

    public void show_image() {

        Picasso.with(this).load("https://" + Url.img_url).fit().centerInside().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }


    void send_con(final String order_number, final String order_value) {



        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.cos_detial_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 // Toast.makeText (cos_detial.this, "res: "+response, Toast.LENGTH_LONG ).show ( );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String mdob="2018-"+constant.b_mon+"-"+constant.b_day;
                String mav="2018-"+constant.A_mon+"-"+constant.A_day;
                params.put("cust_name", constant.c_name);
                params.put("c_ph_no", cell.getText ().toString ());
                params.put("cust_DOB", mdob);
                params.put("cust_aniv", mav);
                params.put("cust_order_no", order_number);
                params.put("cus_order_value", order_value);
                params.put("w_id", constant.waiter_id.toString());
                params.put("b_id", constant.branch_id.toString());
                params.put("res_id", constant.mres_id.toString());
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", constant.idapi);
                return headers;
            }


        };


        Volley.newRequestQueue(this).add(request);

    }

    void send_cos_lsedger(final String order_number, final String order_value) {



      //  Toast.makeText ( this, "name"+constant.c_name, Toast.LENGTH_SHORT ).show ( );
       StringRequest request = new StringRequest(StringRequest.Method.POST, Url.cos_ledger, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //    Toast.makeText (cos_detial.this, "res: "+response, Toast.LENGTH_LONG ).show ( );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("cust_name", constant.c_name);
                params.put("cust_phone",cell.getText ().toString ());
                params.put("cust_order_no", order_number);
                params.put("cust_order_value", order_value);
                params.put("w_id", constant.waiter_id.toString());
                params.put("b_id", constant.branch_id.toString());
                params.put("res_id", constant.mres_id.toString());
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", constant.idapi);
                return headers;
            }


        };


        Volley.newRequestQueue(this).add(request);

    }

    void oldsend_con(final String order_number, final String order_value) {



      //  Toast.makeText ( this, ""+constant.old_cos_Adate+"  "+constant.old_cos_bdate+"  "+constant.c_name, Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.cos_detial_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Toast.makeText (cos_detial.this, "res: "+response, Toast.LENGTH_LONG ).show ( );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String mdob="2018-"+constant.b_mon+"-"+constant.b_day;
                String mav="2018-"+constant.A_mon+"-"+constant.A_day;
                params.put("cust_name", constant.c_name);
                params.put("c_ph_no", cell.getText ().toString ());
                params.put("cust_DOB", constant.old_cos_bdate);
                params.put("cust_aniv", constant.old_cos_Adate);
                params.put("cust_order_no", order_number);
                params.put("cus_order_value", order_value);
                params.put("w_id", constant.waiter_id.toString());
                params.put("b_id", constant.branch_id.toString());
                params.put("res_id", constant.mres_id.toString());
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", constant.idapi);
                return headers;
            }


        };


        Volley.newRequestQueue(this).add(request);

    }


    void send_ans(final String mq_type, final String manswer, final String q_id) {


        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.ans_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText (cos_detial.this, "ans: "+response, Toast.LENGTH_LONG ).show ( );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("q_id", q_id);
                params.put("cust_ph_no", cell.getText ().toString ());
                params.put("q_type", mq_type);
                params.put("answer", manswer);
                params.put("waiter_id", constant.waiter_id.toString());
                params.put("b_id", constant.branch_id.toString());
                params.put("res_id", constant.mres_id.toString());
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", constant.idapi);
                return headers;
            }


        };


        Volley.newRequestQueue(this).add(request);

    }


    void lisr() {

        DBhelper rd = new DBhelper(this);
        SQLiteDatabase arg0 = rd.getReadableDatabase();


        Cursor c = null;
        c = arg0.rawQuery(" SELECT *   FROM question ", null);


        if (c.moveToFirst()) {
            do {

                String mqty=c.getString ( 1 );
                String mans=c.getString ( 2 );
                String mid=c.getString ( 3 );
                send_ans ( mqty,mans,mid );
            } while (c.moveToNext());


            }

        db.deletertoken();
        }




    void next_act() {
        Intent v = new Intent(this, Star_rating.class);
        startActivity(v);
        finish();
    }


    void log() {
        Mob = cell.getText().toString();
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.con_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText (cos_detial.this, "res: "+response, Toast.LENGTH_LONG ).show ( );
                try {
                    loadIntoListC_detail("[" + response + "]");
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

                params.put("cus_Phno", Mob);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization",constant.idapi);
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(request);


    }

    private void loadIntoListC_detail(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        error = new String[jsonArray.length()];

        cname = new String[jsonArray.length()];
        cdob = new String[jsonArray.length()];
        canv = new String[jsonArray.length()];
        wid = new String[jsonArray.length()];
        bid = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                error[i] = obj.getString("error");
                cname[i] = obj.getString("cust_name");
                cdob[i] = obj.getString("cust_DOB");
                canv[i] = obj.getString("cust_aniv");
                wid[i] = obj.getString("w_id");
                bid[i] = obj.getString("b_id");


                //Toast.makeText(this, "cname"+cname[i], Toast.LENGTH_SHORT).show();
                cos_name.setText("" + cname[i]);
                constant.old_cos_bdate=cdob[i];
                constant.old_cos_Adate=canv[i];
                constant.c_name=cname[i];


               // date_birth.setText("" + cdob[i]);
               // adate.setText("" + canv[i]);
           if(cname[i].equals("null")){cos_name.setText("");
           constant.old_ch="0";

           }

else{   constant.old_ch="1";  dialog();
                   ;
                }
            }

        }

    public void show_image_Top(){

        Picasso.with(this).load("https://"+Url.img_url).fit().centerInside().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(top, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });}

public  void send_sms(){
    //Toast.makeText ( cos_detial.this, "dd"+constant.sms_states.toString (), Toast.LENGTH_SHORT ).show ( );
    if (constant.sms_states.equals ( "1" )){

        sms();

    }else {
        //Toast.makeText ( cos_detial.this, "ff", Toast.LENGTH_SHORT ).show ( );
    }
}
}
