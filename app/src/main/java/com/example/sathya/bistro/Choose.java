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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Choose extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {
    ListView lView;
    private String[] Question,ans_a,ans_b,ans_c,ans_d,Mandatory,Q_id,Q_type;
    TextView qst;
    private int arrayno;
    ImageView imageView,top;
    Button skip;
    public static ArrayList<String> selectedAnswers;
    constant mmrating=new constant();
    DBhelper db=new DBhelper(this);
    String a="a",b="b",c="c",d="d",x="",y="",x1="",x2="",x3="", mandatory_status;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.choose );
        lView =findViewById(R.id.listview);
        lView.setOnItemClickListener(this);
        skip=findViewById( R.id.button2);
        imageView=findViewById(R.id.imageView3);
        top=findViewById ( R.id.imageView4 );
        show_image();
        show_image_Top ();
        vehicle();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "vvv", Toast.LENGTH_SHORT).show();
        finish();
    }





    void vehicle () {
        //Toast.makeText ( this, "", Toast.LENGTH_SHORT ).show ( );
        StringRequest request = new StringRequest(StringRequest.Method.POST, Url.chosse, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    loadIntoListView_star( response );

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
        ans_a = new String[jsonArray.length()];
        ans_b = new String[jsonArray.length()];
        ans_c = new String[jsonArray.length()];
        ans_d = new String[jsonArray.length()];
        Mandatory=new String[jsonArray.length()];
          Q_id=new String[jsonArray.length()];
          Q_type=new String[jsonArray.length()];




          String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Question[i] = obj.getString("question");
            Q_id[i] = obj.getString("q_id");
            Q_type[i]=obj.getString("q_type");
            ans_a[i] = obj.getString("a");
            ans_b[i] = obj.getString("b");
            ans_c[i] = obj.getString("c");
            ans_d[i] = obj.getString("d");
            Mandatory[i]= obj.getString("mandatory");



            // mcid[i]=obj.getString ( "c_id" );

        }

        arrayno=Question.length;
        y=""+arrayno;
        AppointmentAdapter adapter = new AppointmentAdapter ();
        lView.setAdapter(adapter);
        mandatory_status=Mandatory[0];
        if (mandatory_status.equals("1")){
            skip.setText("All Fields Are Mandatory");

        }

        else {skip.setOnClickListener(this);}//Toast.makeText(this, ""+mandatory_status, Toast.LENGTH_SHORT).show();
    }catch (Exception e){ Intent v=new Intent(this,num_Rating.class);
          startActivity(v);
      finish();}

    }


    @Override
    public void onClick(View view) {
        Intent v=new Intent(this,num_Rating.class);
        startActivity(v);

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



                selectedAnswers = new ArrayList<>();
                for (int i = 0; i < arrayno; i++) {
                    selectedAnswers.add("Not Attempted");
                }

                convertView = getLayoutInflater().inflate(R.layout.choosee, null);
                qst = convertView.findViewById(R.id.tn);

                RadioGroup radioGroup= convertView.findViewById ( R.id.radiogroup );
                RadioButton radioButton2=convertView.findViewById ( R.id.radioButton2 );
                RadioButton radioButton3=convertView.findViewById ( R.id.radioButton3 );
                RadioButton radioButton4=convertView.findViewById ( R.id.radioButton4);
                RadioButton radioButton5=convertView.findViewById ( R.id.radioButton5 );

                qst.setText(""+Question[position]);
                radioButton2.setText(ans_a[position]);
                radioButton3.setText(ans_b[position]);
                radioButton4.setText(ans_c[position]);
                radioButton5.setText(ans_d[position]);

                ViewGroup vg=(ViewGroup) convertView;



                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener ()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {


                        int checkedid = group.getCheckedRadioButtonId();
                        RadioButton radioButton2=findViewById ( checkedid);
                        RadioButton radioButton3=findViewById ( checkedId);
                        RadioButton radioButton4=findViewById ( checkedId);
                        RadioButton radioButton5=findViewById ( checkedId);
                        String mposition=String.valueOf(position);
                        mchoose ( mposition,radioButton2.getText ().toString ());
                        start_numm ();
                       // Toast.makeText ( Choose.this, "rgb:"+ans_a[5], Toast.LENGTH_SHORT ).show ( );
                    }
                });





        }


            return convertView;


    }

    void mchoose(String mposition,String mmchoose){
        String mq_type=Q_type[0];
            int int_pos=Integer.parseInt(mposition);

        if("0".equals(mposition)){
            String qid=Q_id[int_pos];
            db.insert(qid,mmchoose,mq_type);
           x="1";

            mmrating.choose1=mmchoose;
          //  start_num ();
           // Toast.makeText(Choose.this, "cc"+qid, Toast.LENGTH_SHORT).show();
        }
        if("1".equals(mposition)){
            x1="2";
            String qid=Q_id[int_pos];
            db.insert(qid,mmchoose,mq_type);

            mmrating.choose2=mmchoose;
            //Toast.makeText(Choose.this, "cc"+qid, Toast.LENGTH_SHORT).show();
        }
        if("2".equals(mposition)){
            x2="3";
            String qid=Q_id[int_pos];
            db.insert(qid,mmchoose,mq_type);

            mmrating.choose3=mmchoose;
           // start_num ();
            //Toast.makeText(Star_rating.this, "cc"+mmrating.rat1+":"+mposition, Toast.LENGTH_SHORT).show();
        }
        if("1".equals(mposition)){
            x3="4";
            String qid=Q_id[int_pos];
            db.insert(qid,mmchoose,mq_type);

            mmrating.choose4=mmchoose;
          //  start_num ();
            //Toast.makeText(Star_rating.this, "cc"+mmrating.rat1+":"+mposition, Toast.LENGTH_SHORT).show();
        }
    }




}
    void start_numm(){



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

  void  next_act(){
        Intent v=new Intent(this,num_Rating.class);
      startActivity(v);
    //  finish();
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

