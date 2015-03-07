package simtek.gameplanner.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import simtek.gameplanner.R;


public class Intro_Activity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        Button createNewGame = (Button) findViewById(R.id.newGameButton);
        createNewGame.setOnClickListener(this);

        GridLayout grid = (GridLayout) findViewById(R.id.intro_gridLayout01);
        grid.removeAllViews();
        for(int i = 0;i< 18; i++) {
            GameTile tile = new GameTile(Intro_Activity.this,1);
            grid.addView(tile);
            tile.setOnClickListener(this);
        }
        /*
        grid.setRowCount(9);
        System.out.println("col" + grid.getColumnCount());
        System.out.println("row" + grid.getRowCount());
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_01, menu);
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
        if(v.getId() == R.id.newGameButton) {
            System.out.println("Create new game");
            Intent intent = new Intent(this, CreateGame_Activity.class);
            startActivity(intent);
        }
    }


    class GameTile extends LinearLayout{

        private LinearLayout list1;
        private TextView team1;
        private TextView team2;
        private TextView vs;

        private TextView date;
        private TextView arena;
        private TextView refs;


        public GameTile(Context context, int a) {
            super(context);

            list1 = new LinearLayout(context);

            team1 = new TextView(context);
            team1.setText("JAG1");

            team2 = new TextView(context);
            team2.setText("JAG2");

            vs = new TextView(context);
            vs.setText(" vs ");

            date = new TextView(context);
            date.setText("idag");

            arena = new TextView(context);
            arena.setText("långt borta");

            refs = new TextView(context);
            refs.setText("3/5 ref");


            
        }
    }
}
