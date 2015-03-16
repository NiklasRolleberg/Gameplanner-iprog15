package simtek.gameplanner.android;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import simtek.gameplanner.R;
import simtek.gameplanner.model.Arena;
import simtek.gameplanner.model.Game;
import simtek.gameplanner.model.Model;
import simtek.gameplanner.model.Official;

public class Gameinfo_Activity extends ActionBarActivity implements View.OnClickListener{

    TextView homeTeam;
    TextView awayTeam;
    TextView arena;
    TextView kickoffTime;
    TextView referee;
    TextView umpire;
    TextView headLinesman;
    TextView linesman;
    TextView backJudge;
    TextView turnout;
    TextView ticketPrice;
    TextView visitors;
    TextView offPay;
    TextView finalRevenue;
    TextView income;
    int gameID;
    Game myGame;
    Arena myArena;
    Model myModel;
    String[] refTitles;
    TextView[] refTextViewList;
    Official[] officials; //good idea, but needs a way to handle nulls
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        setValues(gameID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameinfo_layout);
        homeTeam = (TextView) findViewById(R.id.gameinfo_homeTeam);
        awayTeam = (TextView) findViewById(R.id.gameinfo_awayTeam);
        arena = (TextView) findViewById(R.id.gameinfo_Arena);
        kickoffTime = (TextView) findViewById(R.id.gameinfo_kickoffTime);
        referee = (TextView) findViewById(R.id.gameinfo_referee);
        umpire = (TextView) findViewById(R.id.gameinfo_umpire);
        headLinesman = (TextView) findViewById(R.id.gameinfo_headLinesman);
        linesman = (TextView) findViewById(R.id.gameinfo_linesman);
        backJudge = (TextView) findViewById(R.id.gameinfo_backJudge);
        turnout = (TextView) findViewById(R.id.gameinfo_turnout);
        ticketPrice = (TextView) findViewById(R.id.gameinfo_ticketPrice);
        offPay = (TextView) findViewById(R.id.gameinfo_officialsCost);
        finalRevenue = (TextView) findViewById(R.id.gameinfo_finalRevenue);
        income = (TextView) findViewById(R.id.gameinfo_income);
        refTextViewList = new TextView[5];
        refTextViewList[0]= referee;
        refTextViewList[1]= umpire;
        refTextViewList[2]= headLinesman;
        refTextViewList[3]= linesman;
        refTextViewList[4]= backJudge;
        refTitles = new String[5];
        refTitles[0] = " R: ";
        refTitles[1] = " U: ";
        refTitles[2] = " HL: ";
        refTitles[3] = " L: ";
        refTitles[4] = " BJ: ";




        visitors = (TextView) findViewById(R.id.gameinfo_visitors);


        arena.setOnClickListener(this);
        referee.setOnClickListener(this);
        umpire.setOnClickListener(this);
        headLinesman.setOnClickListener(this);
        linesman.setOnClickListener(this);
        backJudge.setOnClickListener(this);

        myModel = ((CustomApplication) this.getApplication()).getModel();

        //get ID
        gameID = getIntent().getIntExtra("ID", 1);
        officials = new Official[5];

        setValues(gameID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //todo pass gameID
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_02, menu);
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
        int clickedID = v.getId();
        ArrayList<Integer> refArray = new ArrayList<>(5);
        refArray.add(R.id.gameinfo_referee);
        refArray.add(R.id.gameinfo_umpire);
        refArray.add(R.id.gameinfo_headLinesman);
        refArray.add(R.id.gameinfo_linesman);
        refArray.add(R.id.gameinfo_backJudge);
        if (clickedID == R.id.gameinfo_Arena) {
            Intent intent = new Intent(this, Arenapicker.class);
            intent.putExtra("ID", gameID);
            intent.putExtra("arenaID", myGame.getArena().getID());
            startActivity(intent);
        }
        if (refArray.contains(clickedID)){
            Intent intent = new Intent(this, Officialspicker_activity.class);
            //int arenaID = 1337; //TODO fix!
            intent.putExtra("ID", gameID);
            startActivity(intent);
        }

    }
    private void setValues(int gameID){ //TODO get from db
        myGame = myModel.getGame(gameID);
        if (myGame != null){
            myArena = myGame.getArena();
        }

        arena.setText(myArena.getName());
        homeTeam.setText("Home team: " + myGame.getHomeTeam().getName());
        awayTeam.setText("Away team: " + myGame.getAwayTeam().getName());
        ticketPrice.setText("Ticket price: " + myGame.getTicketPrice());
        turnout.setText("Turnout [%]: " + myGame.getTurnout());
        visitors.setText("Visitors: " + myGame.getVisitors());
        referee.setBackgroundResource(R.drawable.red_field);
        umpire.setBackgroundResource(R.drawable.red_field);
        headLinesman.setBackgroundResource(R.drawable.red_field);
        linesman.setBackgroundResource(R.drawable.red_field);
        backJudge.setBackgroundResource(R.drawable.red_field);
        for (int i = 0; i < 5; i++){
            Official temp = myGame.getOfficial(i);
            if (temp != null){
                officials[i] = temp;
            }
        }


        String hourString = "" + myGame.getHour();
        String minString = "" + myGame.getMinute();
        if(hourString.length() == 1)
            hourString = "0" + hourString;
        if(minString.length() == 1)
            minString = "0" + minString;
        String timeString = hourString + ":" + minString;
        String year = "" + myGame.getYear() % 100;
        String month = "" +(myGame.getMonth()+1);
        String day = "" + myGame.getDay();
        if(year.length() == 1)
            year = "0" + year;
        if(myGame.getMonth() < 9)
            month = "0" + month;
        if(myGame.getDay() < 10)
            day = "0"+day;
        String date = (year + "-" + month + "-" + day);

        kickoffTime.setText(date + ", " + timeString);
        //todo this shit is horrible, make a collection and loop through it!
        for (int i = 0; i < 5; i++){
            //doStuff
            if (officials[i] != null){
                refTextViewList[i].setText(refTitles[i] + officials[i].getName());
                refTextViewList[i].setBackgroundResource(R.drawable.tiledesign);
            }
            refTextViewList[i].setPadding(3,0,3,0);

        }
        /*
        Official tempOff = myGame.getOfficial(0);
        if (tempOff != null){
            referee.setText("R: " + tempOff.getName());
            referee.setBackgroundResource(R.drawable.tiledesign);
            referee.setPadding(0,0,0,0);
        }
        tempOff = myGame.getOfficial(1);
        if (tempOff != null){
            umpire.setText("U: " + tempOff.getName());
            umpire.setBackgroundResource(R.drawable.tiledesign);
        }
        tempOff = myGame.getOfficial(2);
        if (tempOff != null){
            headLinesman.setText("HL: " + tempOff.getName());
            headLinesman.setBackgroundResource(R.drawable.tiledesign);
        }
        tempOff = myGame.getOfficial(3);
        if (tempOff != null){
            linesman.setText("L: " + tempOff.getName());
            linesman.setBackgroundResource(R.drawable.tiledesign);
        }
        tempOff = myGame.getOfficial(4);
        if (tempOff != null){
            backJudge.setText("BJ: " + tempOff.getName());
            backJudge.setBackgroundResource(R.drawable.tiledesign);
        }
        */


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
