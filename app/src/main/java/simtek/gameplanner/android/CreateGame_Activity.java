package simtek.gameplanner.android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

import simtek.gameplanner.R;

public class CreateGame_Activity extends ActionBarActivity implements View.OnClickListener{

    private Spinner arena;
    private Spinner hometeam;
    private Spinner awayteam;

    private TextView date;
    private TextView time;

    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;




    private Button createGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategame_layout);

        //spinners
        String[] teams = new String[]{"ABC", "123", "NIKLAS", "INTE NIKLAS"};

        hometeam = (Spinner)findViewById(R.id.creategame_spinner01);
        awayteam = (Spinner)findViewById(R.id.creategame_spinner02);
        arena = (Spinner) findViewById(R.id.creategame_arenaspinner);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teams);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teams);
        hometeam.setAdapter(adapter1);
        awayteam.setAdapter(adapter2);
        //hometeam.setOnItemSelectedListener(this);
        //awayteam.setOnItemSelectedListener(this);

        String[] arenas = new String[]{"Långt borta", "På parkeringen", "I vardagsrummet"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arenas);
        arena.setAdapter(adapter3);
        //arena.setOnItemSelectedListener(this);
        //data and time

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);



        date = (TextView) findViewById(R.id.creategame_date);
        time = (TextView) findViewById(R.id.creategame_time);

        String year = "" + mYear % 100;
        String month = ""+(mMonth+1);
        String day = ""+mDay;
        if(mMonth < 9)
            month = "0" + month;
        if(mDay < 10)
            day = "0"+day;

        date.setText(year + "-" + month + "-" + day);
        date.setOnClickListener(this);


        String hourString = "" + mHour;
        String minString = "" + mMinute;
        if(hourString.length() == 1)
            hourString = "0" + hourString;
        if(minString.length() == 1)
            minString = "0" + minString;
        String timeString = hourString + ":" + minString;

        time.setText(timeString);
        time.setOnClickListener(this);

        //Button
        createGame = (Button) findViewById(R.id.creategame_createButton);
        createGame.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_03, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.creategame_date) {

            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int yearInt,
                                              int monthOfYearInt, int dayOfMonthInt) {
                            mYear = yearInt;
                            mMonth = monthOfYearInt;
                            mDay = dayOfMonthInt;

                            String year = "" + mYear % 100;
                            String month = ""+(mMonth+1);
                            String day = ""+mDay;
                            if(year.length() == 1)
                                year = "0"+year;
                            if(mMonth < 9)
                                month = "0" + month;
                            if(mDay < 10)
                                day = "0"+day;
                            date.setText(year + "-" + month + "-" + day);
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
        else if(v.getId() == R.id.creategame_time) {

            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mHour = hourOfDay;
                            mMinute = minute;

                            String hourString = "" + mHour;
                            String minString = "" + mMinute;
                            if(hourString.length() == 1)
                                hourString = "0" + hourString;
                            if(minString.length() == 1)
                                minString = "0" + minString;
                            String timeString = hourString + ":" + minString;
                            time.setText(timeString);

                        }
                    }, mHour, mMinute, true);
            tpd.show();
        }
        else if(v.getId() == R.id.creategame_createButton) {
            System.out.println("Create game");
            this.finish();
        }

    }
}
