package com.example.android_weather_project;

import android.content.Context;
import android.net.ConnectivityManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import android.app.Activity;
import android.widget.ImageView;

import org.json.JSONArray;

import static android.app.PendingIntent.getActivity;


public class Utils {


    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


    public static String excuteGet(String targetURL) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("content-type", "application/json;  charset=utf-8");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(false);

            InputStream is;
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)
                is = connection.getErrorStream();
            else
                is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static int setImage(int actualId, long sunrise, long sunset) {
        int id = actualId;
        int image = 0;

            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {   //DAYTIME IMAGES
                switch (id) {
                    case 804:
                        image = R.drawable.icon26;
                        break;
                    case 803:
                        image = R.drawable.icon26;
                        break;
                    case 802:
                        image = R.drawable.icon28;
                        break;
                    case 801:
                        image = R.drawable.icon29;
                        break;
                    case 800:
                        image = R.drawable.icon32;
                        break;

                    case 200:
                        image = R.drawable.icon35;
                        break;
                    case 300:
                        image = R.drawable.icon42;
                        break;
                    case 700:
                        image = R.drawable.icon19;
                        break;
                    case 600:
                        image = R.drawable.icon18;
                        break;
                    case 500:
                        image = R.drawable.icon5;
                        break;
                    case 501:
                        image = R.drawable.icon5;
                        break;
                    case 502:
                        image = R.drawable.icon5;
                        break;
                    case 503:
                        image = R.drawable.icon5;
                        break;
                    case 511:
                        image = R.drawable.icon5;
                        break;
                    case 520:
                        image = R.drawable.icon5;
                        break;
                    case 521:
                        image = R.drawable.icon9;
                        break;

                        default:
                            image = R.drawable.icon44;
                            break;
                }
            }
            else {

                switch (id) {                                                   // NIGHT TIME IMAGES
                    case 804:
                        image = R.drawable.icon26;
                        break;
                    case 803:
                        image = R.drawable.icon26;
                        break;
                    case 802:
                        image = R.drawable.icon27;
                        break;
                    case 801:
                        image = R.drawable.icon30;
                        break;
                    case 800:
                        image = R.drawable.icon31;
                        break;
                    case 200:
                        image = R.drawable.icon35;
                        break;
                    case 300:
                        image = R.drawable.icon42;
                        break;
                    case 700:
                        image = R.drawable.icon19;
                        break;

                    case 600:
                        image = R.drawable.icon18;
                        break;
                    case 500:
                        image = R.drawable.icon5;
                        break;
                    case 501:
                        image = R.drawable.icon5;
                        break;
                    case 502:
                        image = R.drawable.icon5;
                        break;
                    case 503:
                        image = R.drawable.icon5;
                        break;
                    case 511:
                        image = R.drawable.icon5;
                        break;
                    case 520:
                        image = R.drawable.icon5;
                        break;
                    case 521:
                        image = R.drawable.icon9;
                        break;
                    default:
                        image = R.drawable.icon44;
                        break;
            }
        }
        return image;
    }

}



