package com.android.weather2;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.weather2.OpenWeather_API.WeatherResult;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgDrawerMenu = findViewById(R.id.imgDrawerMenu);
        imgDrawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
                if (!navDrawer.isDrawerOpen(GravityCompat.START))
                    navDrawer.openDrawer(GravityCompat.START);
                else navDrawer.closeDrawer(GravityCompat.END);
            }
        });

        final TextView txtTemprature = findViewById(R.id.txtTemprature);
        final TextView txtSummary = findViewById(R.id.txtSummary);
        final TextView txtCity = findViewById(R.id.txtCity);
        final TextView txtDate = findViewById(R.id.txtDate);
        final TextView txtWind = findViewById(R.id.txtWind);
        final TextView txtHumidity = findViewById(R.id.txtHumidity);
        final TextView txtMaximum = findViewById(R.id.txtMaximum);
        final ImageView imgCondition = findViewById(R.id.imgCondition);
        final EditText edtCity = findViewById(R.id.edtCity);
        Button btnGo = findViewById(R.id.btnGo);



        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).
                        edit().putString("city", edtCity.getText().toString()).apply();



                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


        String city = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("city", "London");
        String api = "74222d36187cec02c8b3c571ed7addf7";

        final String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=Metric&appid=" + api;
        AsyncHttpClient weatherClient = new AsyncHttpClient();
        weatherClient.get(weatherUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();
                WeatherResult weatherResult = gson.fromJson(response.toString(), WeatherResult.class);
                System.out.println("HERE");
                txtTemprature.setText(Long.toString(Math.round(weatherResult.getMain().getTemp())));
                txtHumidity.setText(weatherResult.getMain().getHumidity() + " %");
                txtCity.setText(weatherResult.getName());
                txtDate.setText(new SimpleDateFormat("EEEE dd MMMM yyyy").format(Calendar.getInstance().getTime()));
                txtWind.setText(Long.toString(Math.round(weatherResult.getWind().getSpeed() * 100)) + " m/s");
                txtMaximum.setText(Long.toString(Math.round(weatherResult.getMain().getTempMax()))+" °C");
                txtSummary.setText(weatherResult.getWeather().get(0).getMain());


                switch (weatherResult.getWeather().get(0).getIcon()) {
                    case "01d":
                        imgCondition.setImageResource(R.drawable.wd01d);
                        break;
                    case "01n":
                        imgCondition.setImageResource(R.drawable.wd01n);
                        break;
                    case "02d":
                        imgCondition.setImageResource(R.drawable.wd02d);
                        break;
                    case "02n":
                        imgCondition.setImageResource(R.drawable.wd02n);
                        break;
                    case "03d":
                        imgCondition.setImageResource(R.drawable.wd03d);
                        break;
                    case "03n":
                        imgCondition.setImageResource(R.drawable.wd03n);
                        break;
                    case "04d":
                        imgCondition.setImageResource(R.drawable.wd04d);
                        break;
                    case "04n":
                        imgCondition.setImageResource(R.drawable.wd04n);
                        break;
                    case "09d":
                        imgCondition.setImageResource(R.drawable.wd09d);
                        break;
                    case "09n":
                        imgCondition.setImageResource(R.drawable.wd09n);
                        break;
                    case "10d":
                        imgCondition.setImageResource(R.drawable.wd10d);
                        break;
                    case "10n":
                        imgCondition.setImageResource(R.drawable.wd10n);
                        break;
                    case "11d":
                        imgCondition.setImageResource(R.drawable.wd11d);
                        break;
                    case "11n":
                        imgCondition.setImageResource(R.drawable.wd11n);
                        break;
                    case "13d":
                        imgCondition.setImageResource(R.drawable.wd13d);
                        break;
                    case "13n":
                        imgCondition.setImageResource(R.drawable.wd13n);
                        break;
                    case "50d":
                        imgCondition.setImageResource(R.drawable.wd50d);
                        break;
                    case "50n":
                        imgCondition.setImageResource(R.drawable.wd50n);
                        break;
                    default:
                        imgCondition.setImageResource(R.drawable.wd_default);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(throwable.getMessage());
            }
        });


    }
}
