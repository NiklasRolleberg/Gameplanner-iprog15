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
    TextView turnout;
    TextView ticketPrice;
    TextView visitors;
    TextView offPay;
    TextView finalRevenue;
    TextView officials;
    TextView crewRating;
    TextView rentCost;
    TextView income;
    TextView expenses;
    int gameID;
    int refCount;
    float meanCrewRating;
    Game myGame;
    Arena myArena;
    Model myModel;
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
        officials = (TextView) findViewById(R.id.gameinfo_officials);
        turnout = (TextView) findViewById(R.id.gameinfo_turnout);
        ticketPrice = (TextView) findViewById(R.id.gameinfo_ticketPrice);
        finalRevenue = (TextView) findViewById(R.id.gameinfo_finalRevenue);
        income = (TextView) findViewById(R.id.gameinfo_income);
        crewRating = (TextView) findViewById(R.id.gameinfo_crewrating);
        rentCost = (TextView) findViewById(R.id.gameinfo_rentCost);
        visitors = (TextView) findViewById(R.id.gameinfo_visitors);
        expenses = (TextView) findViewById(R.id.gameinfo_expenses);
        income = (TextView) findViewById(R.id.gameinfo_income);


        arena.setOnClickListener(this);
        officials.setOnClickListener(this);

        myModel = ((CustomApplication) this.getApplication()).getModel();
        gameID = getIntent().getIntExtra("ID", 1);

        setValues(gameID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if (clickedID == R.id.gameinfo_Arena) {
            Intent intent = new Intent(this, Arenapicker.class);
            intent.putExtra("ID", gameID);
            intent.putExtra("arenaID", myGame.getArena().getID());
            startActivity(intent);
        }
        if (clickedID == R.id.gameinfo_officials){
            Intent intent = new Intent(this, Officialspicker_activity.class);
            intent.putExtra("ID", gameID);
            startActivity(intent);
        }

    }
    private void setValues(int gameID){
        myGame = myModel.getGame(gameID);
        if (myGame != null){
            myArena = myGame.getArena();
        }

        arena.setText("Current venue: " + myArena.getName());

        homeTeam.setText("Home team: " + myGame.getHomeTeam().getName());
        awayTeam.setText("Away team: " + myGame.getAwayTeam().getName());
        ticketPrice.setText("Ticket price: " + myGame.getTicketPrice() + " €");
        turnout.setText("Turnout [%]: " + myGame.getTurnout());
        visitors.setText("Visitors: " + myGame.getVisitors());
        refCount = myGame.getNrOfOfficials();
        officials.setText("Officials assigned: " + refCount + "/5");
        if(refCount == 5){
            officials.setBackgroundResource(R.drawable.tiledesign5);
        }
        else if(refCount == 0){
            officials.setBackgroundResource(R.drawable.tiledesign0);
        }
        else {
            officials.setBackgroundResource(R.drawable.tiledesign);
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
        meanCrewRating = 0;
        Official tempOfficial;
        for (int i = 0; i < 5; i++){
            tempOfficial = myGame.getOfficial(i);
            if (tempOfficial!=null){
                meanCrewRating += myGame.getOfficial(i).getRating();
            }
        }
        meanCrewRating /= myGame.getNrOfOfficials();
        crewRating.setText("Crew rating: " + String.format("%.2f", meanCrewRating) + "/5");

        rentCost.setText("Rent cost: " + myGame.getRentCost());
        double tempExpenses = myGame.getRentCost(); //todo get the cost for the refs!
        expenses.setText("Expenses: " + tempExpenses);
        double tempIncome = myGame.getVisitors()*myGame.getTicketPrice();
        income.setText("Income: " + tempIncome);
        double tempRevenue = tempIncome - tempExpenses;
        finalRevenue.setText(String.valueOf(tempRevenue) + " €");

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
