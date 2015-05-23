package kyawthanmong.cystic.controller;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import kyawthanmong.cystic.Alarm.AlarmBroadcastReceiver;
import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.R;
import kyawthanmong.cystic.adapter.Settings;


public class MainActivity extends ActionBarActivity {

    private Button                  surveyButton, logOut;
    private Settings                settings;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.settings = new Settings(this);
        if (!settings.isUserLogin())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);

        }
        setTitle("");
        shouldSetUpView();
        surveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isMondayYet(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), SurveyActivity.class));
                }

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setUserLoginStatus(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        shouldSetAlarm();
    }

    private void shouldSetAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);


        Intent myIntent = new Intent(MainActivity.this, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        

    }


    private void shouldSetUpView(){
        surveyButton        = (Button) findViewById(R.id.buttonSurveyXml);
        logOut              = (Button) findViewById(R.id.logOutButton);
        surveyButton.setVisibility(View.INVISIBLE);

        if (AppUtils.isMondayYet(this)) {
            surveyButton.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onBackPressed() {
        return;
    }





    private void shouldSetupAlermOnMonday(){
    }

}