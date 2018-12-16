package com.example.sathya.bistro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
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

public class num_Rating extends AppCompatActivity implements AdapterView.OnItemClickListener ,View.OnClickListener{
    ListView lView;
    private String[] Question,Mandatory,Q_id;
    private int[] M_rating;
    TextView qst,rating;
    SeekBar seekBar;
    ImageView imageView,top;
    Button skip;
    String x="",y="",x1="",x2="",x3="",mandatory_status;;
    private int arrayno,rat;
    constant mmrating=new constant();
DBhelper db=new DBhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getSupportActionBar().hide();
        setContentView ( R.layout.activity_num__rating );
        lView =findViewById(R.id.listview);
        lView.setOnItemClickListener(this);
        skip=findViewById(R.id.button2);
        skip.setText("All Fields Are Mandatory");
        imageView=findViewById(R.id.imageView3);
        top=findViewById ( R.id.imageView4 );
        show_image();
        show_image_Top ();
       // bt.setVisibility(View.GONE);



        vehicle();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      //  Toast.makeText(this, "vvv", Toast.LENGTH_SHORT).show();
        finish();
    }





    void vehicle () {
        try{

        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.Rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText (Star_rating.this, "Vehicles: "+response, Toast.LENGTH_LONG ).show ( );
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

    }catch (Exception e){ next_act();}}






    private void loadIntoListView_star(String json) throws JSONException {
        try{

        JSONArray jsonArray = new JSONArray(json);

        Question = new String[jsonArray.length()];
        Q_id=new String[jsonArray.length()];
        M_rating=new int[jsonArray.length()];
        Mandatory= new String[jsonArray.length()];
mmrating.Ratarr=new String[4];
        mmrating.Ratarr[0]="0";
        mmrating.Ratarr[1]="0";
        mmrating.Ratarr[2]="0";
        mmrating.Ratarr[3]="0";


        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Question[i] = obj.getString("question");
            Mandatory[i]= obj.getString("mandatory");
            Q_id[i] = obj.getString("q_id");


            // mcid[i]=obj.getString ( "c_id" );

        }

        mandatory_status=Mandatory[0];
        if (mandatory_status.equals("1")){
            skip.setText("All Fields Are Mandatory");

        }

        else {skip.setOnClickListener(this);}//Toast.makeText(this, ""+mandatory_status, Toast.LENGTH_SHORT).show();


        arrayno=Question.length;
        AppointmentAdapter adapter = new AppointmentAdapter();
        lView.setAdapter(adapter);

     }catch (Exception e){ Intent v=new Intent(this,Yes_no.class);
        startActivity(v);}}

    @Override
    public void onClick(View view) {
        next_act();

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
                convertView = getLayoutInflater().inflate(R.layout.num, null);
                qst = convertView.findViewById(R.id.tn);
                rating=convertView.findViewById(R.id.textView2);
                seekBar=convertView.findViewById(R.id.seekBar);








                qst.setText("Token NO:"+Question[position]);
rating.setText(""+mmrating.Ratarr[position]);
try {
    seekBar.setProgress(Integer.parseInt(mmrating.Ratarr[position]));

}catch (Exception e){}


                ViewGroup vg=(ViewGroup) convertView;

                        final SeekBar mrating =vg.findViewById(R.id.seekBar);
                mrating.setMax(10);

                mrating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        int id=seekBar.getId();
                        mmrating.Ratarr[position]="0";

rat=i;

                        AppointmentAdapter adapter = new AppointmentAdapter();
                        lView.setAdapter(adapter);


                        xx();

                       // Toast.makeText(num_Rating.this, ""+mmrating.Ratarr[0], Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mmrating.Ratarr[position]="0"+rat;
                        start_num( position);

                    }
                });

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                                // RatingBar   mrating =vg.findViewById(R.id.ratingBar);
                              //  Toast.makeText(num_Rating.this, "Rdd" + mrating, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return convertView;
        }





    }


    void start_num(int mposition){
        int int_pos=Integer.parseInt(String.valueOf(mposition));
        try{
        int int_a,int_b,int_c,int_d;
        int_a=Integer.parseInt(mmrating.Ratarr[0]);
        int_b=Integer.parseInt(mmrating.Ratarr[1]);
        int_c=Integer.parseInt(mmrating.Ratarr[2]);
        int_d=Integer.parseInt(mmrating.Ratarr[3]);
          //  Toast.makeText(this, "c"+int_a, Toast.LENGTH_SHORT).show();
        if (int_a>0){
            x=""+1;
            String qid=Q_id[int_pos];
            String ans=mmrating.Ratarr[int_pos];
            db.insert(qid,ans,"2");
            xx();


        }
            if (int_b>0){
                x1=""+2;
                String qid=Q_id[int_pos];
                String ans=mmrating.Ratarr[int_pos];
                db.insert(qid,ans,"2");
xx();

            }

            if (int_c>0){
                x2=""+3;
                String qid=Q_id[int_pos];
                String ans=mmrating.Ratarr[int_pos];
                db.insert(qid,ans,"2");


            }
            if (int_d>0){
                x3=""+4;
                String qid=Q_id[int_pos];
                String ans=mmrating.Ratarr[int_pos];
                db.insert(qid,ans,"2");


            }



        }catch (Exception e){}


    }
    void xx(){

        //Toast.makeText(this, "x:"+x+"xx:"+x1, Toast.LENGTH_SHORT).show();
        if (2==arrayno){

        if(x.equals("1") && x1.equals("2")){

            next_act();
        }}

        if (3==arrayno){
            if(x.equals("1") && x1.equals("2") &&x2.equals("3")){

                next_act();
            }}
        if (3==arrayno){
            if(x.equals("1") && x1.equals("2") &&x2.equals("3")&&x3.equals("4")){

                next_act();
            }}
        if (1==arrayno){
            if(x.equals("1")){
                next_act();

            }}}
    void  next_act(){Intent v=new Intent(this,Yes_no.class);
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




