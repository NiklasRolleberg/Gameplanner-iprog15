package simtek.gameplanner.android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import simtek.gameplanner.R;

public class CreateGame_Activity extends ActionBarActivity implements View.OnClickListener{

    private TextView arena;
    private Spinner hometeam;
    private Spinner awayteam;

    private TextView date;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategame_layout);

        //arena
        arena = (TextView) findViewById(R.id.creategame_arenatext);
        arena.setOnClickListener(this);

        //spinners
        String[] items = new String[]{"ABC", "123", "NIKLAS", "INTE NIKLAS"};

        hometeam = (Spinner)findViewById(R.id.creategame_spinner01);
        awayteam = (Spinner)findViewById(R.id.creategame_spinner02);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        hometeam.setAdapter(adapter1);
        awayteam.setAdapter(adapter2);
        //hometeam.setOnItemSelectedListener(this);
        //awayteam.setOnItemSelectedListener(this);

        //data and time
        date = (TextView) findViewById(R.id.creategame_date);
        date.setOnClickListener(this);

        time = (TextView) findViewById(R.id.creategame_time);
        time.setOnClickListener(this);


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
        if(v.getId() == R.id.creategame_arenatext) {
            System.out.println("ARENA!");
        }
        else if(v.getId() == R.id.creategame_date) {

            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(this,
                    null, mYear, mMonth, mDay);
            dpd.show();
        }
        else if(v.getId() == R.id.creategame_time) {
            Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this,null, mHour, mMinute, true);
            tpd.show();
        }

    }
}
