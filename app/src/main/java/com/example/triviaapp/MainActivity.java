package com.example.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    ImageView imageView;

    LinearLayout preGameLayout;

    public static final String TAG = "MainActivity";



    String categoryNumber;

    String typeString;

    Spinner numberOfQuestions;

    Spinner dificulty;

    Spinner type;

    Spinner category;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpAlarm();

        frameLayout = findViewById(R.id.frameLayout);

        imageView = findViewById(R.id.triviaImage);

        preGameLayout = findViewById(R.id.preGameLayout);

        setUpAlarm();


        numberOfQuestions = findViewById(R.id.question_category);


        dificulty = findViewById(R.id.dificulty);


        type = findViewById(R.id.type);


        category = findViewById(R.id.category);


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cat = category.getSelectedItem().toString();
                if (cat.equalsIgnoreCase("any")){
                    categoryNumber = "0";
                } else if (cat.equalsIgnoreCase("sports")){
                    categoryNumber = "21";
                } else if (cat.equalsIgnoreCase("mitology")){
                    categoryNumber = "20";
                } else if (cat.equalsIgnoreCase("history")){
                    categoryNumber = "23";
                } else if (cat.equalsIgnoreCase("politics")){
                    categoryNumber = "24";
                }else if (cat.equalsIgnoreCase("General Knowledge")){
                    categoryNumber = "9";
                }else if (cat.equalsIgnoreCase("Books")){
                    categoryNumber = "10";
                }else if (cat.equalsIgnoreCase("Film")){
                    categoryNumber = "11";
                }else if (cat.equalsIgnoreCase("Music")){
                    categoryNumber = "12";
                }else if (cat.equalsIgnoreCase("Television")){
                    categoryNumber = "14";
                }else if (cat.equalsIgnoreCase("Video Games")){
                    categoryNumber = "15";
                }else if (cat.equalsIgnoreCase("Board Games")){
                    categoryNumber = "16";
                }else if (cat.equalsIgnoreCase("Science and Nature")){
                    categoryNumber = "17";
                }else if (cat.equalsIgnoreCase("Computers")){
                    categoryNumber = "18";
                }else if (cat.equalsIgnoreCase("Geography")){
                    categoryNumber = "22";
                }else if (cat.equalsIgnoreCase("Art")){
                    categoryNumber = "25";
                }else if (cat.equalsIgnoreCase("Animals")){
                    categoryNumber = "27";
                }else if (cat.equalsIgnoreCase("Celebrities")){
                    categoryNumber = "26";
                }else if (cat.equalsIgnoreCase("Mathematics")){
                    categoryNumber = "19";
                }else if (cat.equalsIgnoreCase("Comics")){
                    categoryNumber = "29";
                }else if (cat.equalsIgnoreCase("Gadgets")){
                    categoryNumber = "30";
                }else if (cat.equalsIgnoreCase("Cartoon and Animations")){
                    categoryNumber = "32";
                }else if (cat.equalsIgnoreCase("Vehicles")){
                    categoryNumber = "28";
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String typ = type.getSelectedItem().toString();
                if (typ.equalsIgnoreCase("any")){
                    typeString = "any";
                }else if (typ.equalsIgnoreCase("multiple")){
                    typeString = "multiple";
                }else if (typ.equalsIgnoreCase("true/false")){
                    typeString = "boolean";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Button startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = numberOfQuestions.getSelectedItem().toString();

                String dif = dificulty.getSelectedItem().toString();




                Intent intent = new Intent(MainActivity.this, Game.class);
                intent.putExtra("BROJ", number);
                intent.putExtra("DIF", dif);
                intent.putExtra("TYPE", typeString);
                Log.d(TAG, "onClick: " + categoryNumber);
                intent.putExtra("CATEGORY", categoryNumber);
                startActivity(intent);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settings:

                SettingsFragment settingsFragment = new SettingsFragment();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frameLayout, settingsFragment)
                        .addToBackStack("favorites")
                        .commit();
                frameLayout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                preGameLayout.setVisibility(View.GONE);

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        frameLayout.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        preGameLayout.setVisibility(View.VISIBLE);
    }

    private void setUpAlarm() {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, TriviaNotification.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        if (alarmManager != null) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR, 20);
//            calendar.set(Calendar.MINUTE,16);
            alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 1000,
                    60000*60*24,
                    pendingIntent);
        }
    }


}
