package com.yegen.studentapidemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private List<String> dataList = new ArrayList<>();
    Button btnAdd;
    private ListView listView;
    private final static String myUrl = "myUrl";
    private static final String TAG = "MainActivity";
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        findViews();
        initialize();
    }
    private void initialize(){
        new studentGetData().execute();
    }
    private void findViews() {
        listView = findViewById(R.id.studentsListView);
        btnAdd=findViewById(R.id.btnAdd);
    }
   private class studentGetData extends AsyncTask<Void, Void, String>{
        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... voids) {
                        try {
                            URL url = new URL(myUrl); // API endpoint
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();connection.setRequestMethod("GET");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            JSONArray jsonArray = new JSONArray(response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String data = jsonArray.getString(i);
                                dataList.add(data);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error fetching data: " + e.getMessage());
                        }
                        return dataList.toString();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String jsonData) {
            if (jsonData != null) {
                try {
                    ArrayList<Student> students = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Student student=new Student();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        student.NAME = jsonObject.getString("name");
                        student.SURNAME = jsonObject.getString("surname");
                        students.add(student);
                    }
                    StudentAdapter adapter = new StudentAdapter(MainActivity.this,R.layout.student_adapter,students);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON: " + e.getMessage());
                }
            }
            }
        }
    }



