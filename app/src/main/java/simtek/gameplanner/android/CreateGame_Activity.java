package simtek.gameplanner.android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import simtek.gameplanner.model.Arena;
import simtek.gameplanner.model.Game;
import simtek.gameplanner.model.Model;
import simtek.gameplanner.model.Team;

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


    private Model model;

    private Button createGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategame_layout);

        //load model
        model = ((CustomApplication) this.getApplication()).getModel();

        //spinners
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        for(Team t:model.getTeams()) {
            adapter1.add(t.getName());
            adapter2.add(t.getName());
        }

        hometeam = (Spinner)findViewById(R.id.creategame_spinner01);
        awayteam = (Spinner)findViewById(R.id.creategame_spinner02);
        arena = (Spinner) findViewById(R.id.creategame_arenaspinner);

        hometeam.setAdapter(adapter1);
        awayteam.setAdapter(adapter2);
        //hometeam.setOnItemSelectedListener(this);
        //awayteam.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        for(Arena a: model.getArenas()) {
            adapter3.add(a.getName());
        }
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

            Arena temparena = null;
            Team home = null;
            Team away = null;

            //TODO gör om
            String selectedArena= (String) arena.getSelectedItem();
            for(Arena a:model.getArenas()) {
                if(a.getName().equals(selectedArena)) {
                    temparena = a;
                    System.out.println("arena is :" + temparena.getName());
                    break;
                }
            }

            String selectedHome= (String) hometeam.getSelectedItem();
            for(Team a:model.getTeams()) {
                if(a.getName().equals(selectedHome)) {
                    home = a;
                    break;
                }
            }

            String selectedAway= (String) awayteam.getSelectedItem();
            for(Team a:model.getTeams()) {
                if(a.getName().equals(selectedAway)) {
                    away = a;
                    break;
                }
            }



            Game g = new Game(0,temparena,home,away ,mYear ,mMonth, mDay,mHour,mMinute);
            model.addGame(g);


            System.out.println("Create game");
            Intent intent = new Intent(this, Gameinfo_Activity.class);
            int arenaID = g.getId(); //todo get from db
            intent.putExtra("ID", arenaID);
            startActivity(intent);
        }

    }
}
