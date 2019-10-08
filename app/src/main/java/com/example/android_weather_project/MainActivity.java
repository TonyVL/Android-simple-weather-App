package com.example.android_weather_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    private Integer images[]={R.drawable.icon32,R.drawable.icon31,R.drawable.icon35,R.drawable.icon16,R.drawable.icon20,R.drawable.icon27,R.drawable.icon18,R.drawable.icon0};
     int currImage = 0;

    TextView tvSelectCity, tvCityName, tvDetails, tvCurrent_temperature, tvHumidity, pressure_field, tvWeather_icon, tvUpdate;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Montreal, CA";
    ImageView ivImageView;

    String OPEN_WEATHER_MAP_API = "fb988a426f34e808b809e9ad3b0ae008";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                          // USES ABSTRACT CLASS ACTIONBAR , HIDES OR SHOWS MY TITLE
        setContentView(R.layout.activity_main);

        loader = (ProgressBar) findViewById(R.id.loader);                                    // PROGRESS BAR
        tvSelectCity = (TextView) findViewById(R.id.tvSelectCity);
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvUpdate = (TextView) findViewById(R.id.tvUpdate);
        tvDetails = (TextView) findViewById(R.id.tvDetails);
        tvCurrent_temperature = (TextView) findViewById(R.id.tvCurrent_temperature);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        ivImageView = findViewById(R.id.ivImageView);

        taskLoadUp(city);


        
        

        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Change City");
                final EditText input = new EditText(MainActivity.this);
                input.setText(city);

//-------------------------------------------------------------------------------------------------------
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,                                         // DISPLAYS THE POPUP ALERT EDIT TEXT TO CHANGE CITY
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.logo);
//--------------------------------------------------------------------------------------------------------
                alertDialog.setPositiveButton("Change",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                city = input.getText().toString();                                      // CANCEL AND CHANGE OPTIONS OF THE ALERT BOX

                                taskLoadUp(city);

                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });

    }


    //--------------------------------------------------------------------------------------------------------
    public void taskLoadUp(String query) {                                                              //CHECKS CONNECTIVITY TO NETWORK
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();  // IF NO NETWORK GET ALERT
        }
    }

//---------------------------------------------------------------------------------------------------------

    class DownloadWeather extends AsyncTask < String, Void, String > {
        @Override
        protected void onPreExecute() {                                                                     // SETS THE VISUALS OF THE UI
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);

        }
  //-------------------------------------------------------------------------------------------------------
        protected String doInBackground(String...args) {
            String xml = Utils.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                    "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();

                    tvCityName.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    tvDetails.setText(details.getString("description").toUpperCase(Locale.US));
                    tvCurrent_temperature.setText(String.format("%.2f", main.getDouble("temp")) + "°C");
                    tvHumidity.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    tvUpdate.setText(df.format(new Date(json.getLong("dt") * 1000)));

//====================================================================================================================================


                  ivImageView.setImageResource(Utils.setImage(details.getInt("id"),
                          json.getJSONObject("sys").getLong("sunrise") * 1000,
                          json.getJSONObject("sys").getLong("sunset") * 1000));


//---------------------------------------------------------------------------------------------------------------------

//=====================================================================================================================================
                    loader.setVisibility(View.GONE);

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Please check if city name is valid", Toast.LENGTH_SHORT).show();
            }


        }



    }


}