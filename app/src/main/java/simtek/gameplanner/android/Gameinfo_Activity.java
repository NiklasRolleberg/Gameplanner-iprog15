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
        setValues(1);
        arena.setOnClickListener(this);
        referee.setOnClickListener(this);
        umpire.setOnClickListener(this);
        headLinesman.setOnClickListener(this);
        linesman.setOnClickListener(this);
        backJudge.setOnClickListener(this);
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
            int arenaID = 1337; //todo get from db
            intent.putExtra("ID",arenaID);

            //Bundle bundle = new Bundle();
            //bundle.putInt("arenaID",arenaID);
            //intent.putExtras(bundle);
            startActivity(intent);
        }
        if (refArray.contains(clickedID)){
            Intent intent = new Intent(this, Officialspicker_activity.class);
            int arenaID = 1337; //TODO fix!
            intent.putExtra("ID", arenaID);
            startActivity(intent);
        }

    }
    private void setValues(int gameID){ //TODO get from db
        homeTeam.setText("Home team: " + "STU");
        referee.setText("R: John Doe");
        umpire.setText("U: Lubo!!!");

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
