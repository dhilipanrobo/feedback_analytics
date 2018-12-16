package com.example.sathya.bistro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class text_feeback extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    Button skip;
    TextView textView,textView_mat;
    EditText editText;
    ImageView imageView,top;
    private String[] Question,Mandatory,Q_id,Q_type;
    TextView qst;
    String mandatory_status,x="",y="";
    private int arrayno;
    constant mmrating=new constant();
    DBhelper db=new DBhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_text_feeback);

        editText=findViewById(R.id.editText3);
        textView=findViewById(R.id.textView3);
        textView_mat=findViewById(R.id.textView8);
        //editText.setOnClickListener(this);
        skip=findViewById( R.id.button2);
        skip.setOnClickListener(this);
        imageView=findViewById(R.id.imageView3);
        top=findViewById ( R.id.imageView4 );
        show_image();
        show_image_Top ();

//getActionBar().setTitle(Html.fromHtml("<font color='#000000'>ActionBartitle</font>"));

        vehicle();
        editText.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText.length ()>0) {
                    skip.setText ( "Submit" );
                }else{
                    skip.setText ( "Skip" );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "vvv", Toast.LENGTH_SHORT).show();
        finish();
    }





    void vehicle () {

        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               //  Toast.makeText (text_feeback.this, "Vehicles: "+response, Toast.LENGTH_LONG ).show ( );
                try{
                    loadIntoListView_star(response );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })




        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", constant.idapi);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }




    private void loadIntoListView_star(String json) throws JSONException {
        try{

        JSONArray jsonArray = new JSONArray(json);

        Question = new String[jsonArray.length()];
        Mandatory=new String[jsonArray.length()];

            Q_type=new String[jsonArray.length()];

            Q_id=new String[jsonArray.length()];


        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Question[i] = obj.getString("question");
            Mandatory[i] = obj.getString("mandatory");
            Q_id[i] = obj.getString("q_id");
            Q_type[i] = obj.getString("q_type");


            // mcid[i]=obj.getString ( "c_id" );

        }

        arrayno=Question.length;
        y=""+arrayno;
        AppointmentAdapter adapter = new AppointmentAdapter();
        textView.setText(Question[0]);

        mandatory_status=Mandatory[0];
        if (mandatory_status.equals("1")){
            skip.setText("Next");
            textView_mat.setText("All Fields Are Mandatory");

        }

        else {skip.setOnClickListener(this);}//Toast.makeText(this, ""+mandatory_status, Toast.LENGTH_SHORT).show();
    }catch (Exception e){ Intent v=new Intent(this,cos_detial.class);
            startActivity(v);finish();}}



    @Override
    public void onClick(View view) {
        String mq_type=Q_type[0];
        if (mandatory_status.equals("0")){
            String qid=Q_id[0];
            db.insert(qid,editText.getText().toString(),mq_type);
            Intent v=new Intent(this,cos_detial.class);

            startActivity(v);
            finish();
        }
        else {
        int id = view.getId();


        if (id== R.id.button2){
            if(editText.length()!=0){
                String qid=Q_id[0];
                db.insert(qid,editText.getText().toString(),mq_type);
                Intent v=new Intent(this,cos_detial.class);
                startActivity(v);
                finish();
            }
            else {

                Toast.makeText(this, "All Fields Are Mandatory!!!", Toast.LENGTH_SHORT).show();
            }}




        }






    }

    class AppointmentAdapter extends BaseAdapter {



        @Override
        public int getCount() {
            return arrayno;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }



        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            convertView = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.star_rating, null);
                qst = convertView.findViewById(R.id.tn);






                qst.setText(""+Question[position]);

                ViewGroup vg=(ViewGroup) convertView;
                final RatingBar mrating =vg.findViewById(R.id.ratingBar);
                mrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                        constant mmrating=new constant();

                        String mposition=String.valueOf(position);



                    }
                });

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewGroup vg=(ViewGroup) view;
                        RatingBar   mrating =vg.findViewById(R.id.ratingBar);
                        mrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            public void onRatingChanged(RatingBar ratingBar, float rating,
                                                        boolean fromUser) {


                            }
                        });





                        // RatingBar   mrating =vg.findViewById(R.id.ratingBar);
                        Toast.makeText(text_feeback.this, "Rat"+mrating.getRating(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return convertView;
        }





    }
    public void show_image(){

        Picasso.with(this).load("https://"+Url.img_url).fit().centerInside().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });}

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

}

