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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        grid.setPadding(15,0,0,0);
        grid.removeAllViews();

        //set params for tiles
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        LinearLayout.LayoutParams tileSize = new LinearLayout.LayoutParams((width/2)-30,(width/2)-30);
        tileSize.setMargins(15,0,15,0);
        LinearLayout.LayoutParams tileParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0;i< 18; i++) {
            GameTile tile = new GameTile(Intro_Activity.this);
            tile.setLayoutParams(tileParams);
            LinearLayout container = new LinearLayout(this);
            container.setLayoutParams(tileSize);
            container.setPadding(15,15,15,15);
            container.setGravity(Gravity.CENTER);
            container.addView(tile);
            grid.addView(container);
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
        else {
            Intent intent = new Intent(this, Gameinfo_Activity.class);
            int arenaID = 1337; //todo get from db
            intent.putExtra("ID",arenaID);
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


        public GameTile(Context context) {
            super(context);

            list1 = new LinearLayout(context);

            team1 = new TextView(context);
            team1.setText("JAG1");

            team2 = new TextView(context);
            team2.setText("JAG2");

            vs = new TextView(context);
            vs.setText(" vs ");

            list1.addView(team1);
            list1.addView(vs);
            list1.addView(team2);
            list1.setGravity(Gravity.CENTER_HORIZONTAL);

            date = new TextView(context);
            date.setText("idag");
            date.setGravity(Gravity.CENTER_HORIZONTAL);

            arena = new TextView(context);
            arena.setText("långt borta");
            arena.setGravity(Gravity.CENTER_HORIZONTAL);

            refs = new TextView(context);
            refs.setText("3/5 ref");
            refs.setGravity(Gravity.CENTER_HORIZONTAL);

            setOrientation(VERTICAL);
            setBackgroundColor(Color.GREEN);

            addView(list1);
            addView(date);
            addView(arena);
            addView(refs);
        }
    }
}
