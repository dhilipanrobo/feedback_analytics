package com.example.sathya.bistro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class Star_rating extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {
    ListView lView;
    Button skip;
    private String[] Question,Mandatory,Q_id,Q_type;
    TextView qst;
    ImageView imageView,imageView_Top;
    DBhelper db=new DBhelper(this);
    String mandatory_status,x="",y="",x1="",x2="",x3="";
    private int arrayno;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_star_rating);
        lView =findViewById(R.id.listview);
        imageView=findViewById(R.id.imageView3);
        imageView_Top=findViewById(R.id.imageView4);


        skip=findViewById( R.id.button2);
        lView.setOnItemClickListener(this);
        SharedPreferences prefs = getSharedPreferences("userlog", Context.MODE_PRIVATE);

       // Toast.makeText(this, "img url:"+Url.img_url, Toast.LENGTH_SHORT).show();
        show_image();
        show_image_Top ();
        vehicle();
        db.deletertoken();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "vvv", Toast.LENGTH_SHORT).show();
        finish();
    }





    void vehicle () {
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.Star, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText (Star_rating.this, "Vehicles: "+response, Toast.LENGTH_LONG ).show ( );
                try{
                    loadIntoListView_star(  response );
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

    private void loadIntoListView_star(String json) throws JSONException
    {

        try{

        JSONArray jsonArray = new JSONArray(json);

        Question = new String[jsonArray.length()];
        Mandatory=new String[jsonArray.length()];
            Q_id=new String[jsonArray.length()];
            Q_type=new String[jsonArray.length()];





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
        lView.setAdapter(adapter);
        mandatory_status=Mandatory[0];
        if (mandatory_status.equals("1")){
            skip.setText("All Fields Are Mandatory");

        }

        else {skip.setOnClickListener(this);}//Toast.makeText(this, ""+mandatory_status, Toast.LENGTH_SHORT).show();
        }catch (Exception e){ Intent v=new Intent(this,Choose.class);
            startActivity(v);
        //finish ();
        }

    }







    @Override
    public void onClick(View view) {
        Intent v=new Intent(this,Choose.class);
        startActivity(v);
        finish();

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
                final RatingBar   mrating =vg.findViewById(R.id.ratingBar);
                mrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                        constant mmrating=new constant();

                        String mposition=String.valueOf(position);

                        Rating(mposition,rating);
                        start_num();

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
                        Toast.makeText(Star_rating.this, "Rat"+mrating.getRating(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return convertView;
        }





    }

    void Rating(String mposition,float mmrat){
        String mq_type=Q_type[0];
        int int_pos=Integer.parseInt(mposition);
        String ans=String.valueOf(mmrat);
        if("0".equals(mposition)){
            constant.rat1=mmrat;

            start_num();
            x="0";
            x="1";
            String qid=Q_id[int_pos];
            db.insert(qid,ans,mq_type);

            //Toast.makeText(Star_rating.this, "cc"+mmrating.rat1+":"+mposition, Toast.LENGTH_SHORT).show();
        }
        if("1".equals(mposition)){

            constant.rat2=mmrat;
            start_num();

            x1="2";
            String qid=Q_id[int_pos];
            db.insert(qid,ans,mq_type);
            //Toast.makeText(Star_rating.this, "cc"+mmrating.rat1+":"+mposition, Toast.LENGTH_SHORT).show();
        }
        if("2".equals(mposition)){

            constant.rat3=mmrat;
            start_num();

            x2="3";
            String qid=Q_id[int_pos];
            db.insert(qid,ans,mq_type);

            //Toast.makeText(Star_rating.this, "cc"+mmrating.rat1+":"+mposition, Toast.LENGTH_SHORT).show();
        }
        if("3".equals(mposition)){

            constant.rat4=mmrat;
            start_num();
            String qid=Q_id[int_pos];
            db.insert(qid,ans,mq_type);
            x3="4";
            //Toast.makeText(Star_rating.this, "cc"+mmrating.rat1+":"+mposition, Toast.LENGTH_SHORT).show();
        }
    }

    void start_num(){



        if (2==arrayno){
            if(x.equals("1") && x1.equals("2")){

                next_act();
            }}

        if (3==arrayno){
            if(x.equals("1") && x1.equals("2") &&x2.equals("3")){

                next_act();
            }}
        if (4==arrayno){
            if(x.equals("1") && x1.equals("2") &&x2.equals("3")&&x3.equals("4")){

                next_act();
            }}
        if (1==arrayno){
            if(x.equals("1")){
                next_act();

            }}
    }

    void  next_act(){Intent v=new Intent(this,Choose.class);
        startActivity(v);
    finish();}

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

        Picasso.with(this).load("https://"+Url.top_img_url).fit().centerInside().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(imageView_Top, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });}
}
