package simtek.gameplanner.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import simtek.gameplanner.R;
import simtek.gameplanner.model.Game;
import simtek.gameplanner.model.Model;


public class Intro_Activity extends ActionBarActivity implements View.OnClickListener {

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        //load model
        model = ((CustomApplication) this.getApplication()).getModel();

        Button createNewGame = (Button) findViewById(R.id.newGameButton);
        createNewGame.setBackgroundResource(R.drawable.buttondesign);
        createNewGame.setTextColor(Color.WHITE);
        createNewGame.setOnClickListener(this);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        GridView G = (GridView) findViewById(R.id.intro_gridView);
        G.setColumnWidth(width / 2);
        G.setAdapter(new tileAdapter(this));
        G.setPadding(30, 20, 30, 0);
        G.setVerticalSpacing(20);
        G.setHorizontalSpacing(20);
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
        if (v.getId() == R.id.newGameButton) {
            System.out.println("Create new game");
            Intent intent = new Intent(this, CreateGame_Activity.class);
            startActivity(intent);
        }
    }

    private String getDateString(Game game) {

        int mYear = game.getYear();
        int mMonth = game.getMonth();
        int mDay = game.getDay();
        String year = "" + mYear % 100;
        String month = "" + (mMonth + 1);
        String day = "" + mDay;
        if (mMonth < 9)
            month = "0" + month;
        if (mDay < 10)
            day = "0" + day;

        String dateString = year + "-" + month + "-" + day;
        return dateString;
    }

    private String getTimeString(Game game) {
        int mHour = game.getHour();
        int mMinute = game.getMinute();
        String hourString = "" + mHour;
        String minString = "" + mMinute;
        if (hourString.length() == 1)
            hourString = "0" + hourString;
        if (minString.length() == 1)
            minString = "0" + minString;
        String timeString = hourString + ":" + minString;
        return timeString;
    }

    class tileAdapter extends BaseAdapter {

        Context context;

        tileAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return model.getGames().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GameTile tile;

            if (convertView == null) {
                tile = new GameTile(context);
            } else {
                tile = (GameTile) convertView;
            }

            //TODO fixa om det är dåligt, kanske lägga till en "get(index)" i model
            Game game = model.getGames().get(position);
            if (game == null)
                return tile;

            //TODO kan nog göras bättre
            String info = "" + game.getHomeTeam().getName() + " vs " + game.getAwayTeam().getName() +
                    "\n" + game.getArena().getName() + "\n" + getDateString(game) + "  " + getTimeString(game) +
                    "\n" + game.getNrOfOfficials() + "/5 ref";


            //tile.setBackgroundColor(Color.parseColor("#58FA58"));
            tile.setBackgroundResource(R.drawable.tiledesign);
            tile.setGame(game);
            tile.setText(info);
            tile.setOnClickListener(new tileListener());
            return tile;
        }
    }

    class GameTile extends Button {

        private Game game;

        public GameTile(Context context) {
            super(context);
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public int getGameID() {
            if (game != null)
                return game.getId();
            return 0;
        }

        @Override
        protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
            final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            setMeasuredDimension(width, width);
            this.invalidate();
        }

        @Override
        protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
            super.onSizeChanged(w, w, oldw, oldh);
        }
    }

    class tileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v instanceof GameTile) {
                GameTile tile = (GameTile) v;
                Intent intent = new Intent(Intro_Activity.this, Gameinfo_Activity.class);
                int gameID = tile.getGameID();
                intent.putExtra("ID", gameID);
                startActivity(intent);
            }
        }
    }
}