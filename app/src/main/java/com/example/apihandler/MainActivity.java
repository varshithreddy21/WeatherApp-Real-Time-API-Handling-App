package com.example.apihandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
Button button;
TextView textView,textView2;
EditText editText;
TextView temp;
String baseUrl="https://api.openweathermap.org/data/2.5/weather?q=";
String API="&appid=494eceb99c0c6cf4d2251b34a2225268";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RequestQueue requestQueue;
        requestQueue = VolleySingleton.getInstance(this).getmRequestQueue();
        button=findViewById(R.id.button);
        editText=findViewById(R.id.city);
        textView=findViewById(R.id.data);
        textView2=findViewById(R.id.textView2);
        temp=findViewById(R.id.temp);
    textView.setVisibility(View.GONE);
    temp.setVisibility(View.GONE);
    textView2.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                String myUrl=baseUrl+editText.getText().toString()+API;
                requestQueue.add(sendAPIReequest(myUrl));}
            }
        });

    }

    private JsonObjectRequest sendAPIReequest( String url) {
       // String url="https://api.myjson.com/bins/172jsb";



        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray myWeather=response.getJSONArray("weather");
                    for(int i=0;i<myWeather.length();i++){

                        JSONObject obj=myWeather.getJSONObject(i);
                        String weather=obj.getString("description");


                        textView.setText(weather);
                    }
                    JSONObject obj=response.getJSONObject("main");
                    double fhit=obj.getDouble("temp");

                    temp.setText(String.format("%.2f",fhit-273.15)+(char) 0x00B0+"C");
                    textView.setVisibility(View.VISIBLE);
                    temp.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        return jsonObjectRequest;
    }
}
