package simtek.gameplanner.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import simtek.gameplanner.R;
import simtek.gameplanner.model.Game;
import simtek.gameplanner.model.Model;
import simtek.gameplanner.model.Official;

public class Officialspicker_activity extends ActionBarActivity{

    View currentDrag;
    LinearLayout R_layout, U_layout, HL_layout, L_layout, BJ_layout;
    String[] pos = {"R", "U", "HL", "L", "BJ"};
    LinearLayout officialsPositions[] = new LinearLayout[5];
    TextView officialsPositionsText[] = new TextView[5];
    Model model;
    ArrayList<Official> allOfficials;
    ArrayList<textViewOfficial> textViews = new ArrayList<>();
    Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officials_layoutr);

        model = ((CustomApplication) this.getApplication()).getModel();

        allOfficials = model.getOfficials();

        int ID = getIntent().getIntExtra("ID",0); //TODO this is not working when you come from the Refinfo Activity :(
        game = model.getGame(ID);

        //set title (teams)
        TextView teams = (TextView) findViewById(R.id.Teams);
        teams.setText(game.getHomeTeam().getName() + " vs " + game.getAwayTeam().getName());

        //Move following to View?
        R_layout = (LinearLayout) findViewById(R.id.R_officials);
        U_layout = (LinearLayout) findViewById(R.id.U_officials);
        HL_layout = (LinearLayout) findViewById(R.id.HL_officials);
        L_layout = (LinearLayout) findViewById(R.id.L_officials);
        BJ_layout = (LinearLayout) findViewById(R.id.BJ_officials);

        officialsPositions[0] = R_layout;
        officialsPositions[1] = U_layout;
        officialsPositions[2] = HL_layout;
        officialsPositions[3] = L_layout;
        officialsPositions[4] = BJ_layout;

        //TODO add onClickListeners on the layouts

        officialsPositionsText[0] = (TextView) findViewById(R.id.R_text);
        officialsPositionsText[1] = (TextView) findViewById(R.id.U_text);
        officialsPositionsText[2] = (TextView) findViewById(R.id.HL_text);
        officialsPositionsText[3] = (TextView) findViewById(R.id.L_text);
        officialsPositionsText[4] = (TextView) findViewById(R.id.BJ_text);


        //add text views
        LinearLayout scroll = (LinearLayout) findViewById(R.id.scroll_linear);

        for(Official o : allOfficials)
        {
            //Set "view" for text view
            textViewOfficial official = new textViewOfficial(this);
            textViews.add(official);
            official.setOfficial(o);
            official.setText(o.getName());
            official.setTag(official.getText());
            official.setTextSize(18);
            official.setMinHeight(60);
            official.setMinWidth(200);
            official.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            int resID = getResources().getIdentifier("abc_list_longpressed_holo", "drawable", getPackageName());
            official.setBackgroundResource(resID);
            official.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)official.getLayoutParams();
            params.setMargins(0, 6, 0, 6);
            official.setLayoutParams(params);
            scroll.addView(official);

            // Set listeners for the drag and drop events.
            currentDrag = official;
            currentDrag.setTag(currentDrag.getTag());
            currentDrag.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    currentDrag = v;
                    ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                    // Instantiates the drag shadow builder.
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

                    // Starts the drag
                    currentDrag.startDrag(dragData, myShadow, v, 0);

                    return false;
                }
            });

            //Drag listeners
            currentDrag = official;
            for(int j=0; j<5; j++)
            {
                officialsPositions[j].setOnDragListener(new View.OnDragListener() {

                    textViewOfficial d;

                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        d = (textViewOfficial) currentDrag;
                        if(event.getAction() == DragEvent.ACTION_DROP) //handle the dragged view being dropped over a drop view
                        {
                            //currentDrag.setVisibility(View.INVISIBLE); //stop displaying the text when it has been dropped a correct place
                            currentDrag.setBackgroundColor(Color.GRAY);
                            currentDrag.getBackground().setAlpha(75);
                            currentDrag.setLongClickable(false);

                            int resID = getResources().getIdentifier("abc_list_longpressed_holo", "drawable", getPackageName());
                            v.setBackgroundResource(resID);

                            TextView t;

                            int INDEX = -1;
                            for(int i=0; i<officialsPositions.length; i++)
                            {
                                if(v.getId()==officialsPositions[i].getId())
                                {
                                    INDEX = i;
                                }
                            }

                            game.addOfficial(d.getOfficial(), INDEX);

                            t = officialsPositionsText[INDEX];
                            //String name = t.getText().toString();
                            if(game.getOfficial(INDEX) != null)
                            {
                                String st = t.getText().toString();
                                for(textViewOfficial to: textViews)
                                {
                                    if(st.substring(2+pos[INDEX].length(),st.length()).equals(to.getText()))
                                    {
                                        //to.setVisibility(View.VISIBLE);
                                        int rID = getResources().getIdentifier("abc_list_longpressed_holo", "drawable", getPackageName());
                                        to.setBackgroundResource(rID);
                                        to.setLongClickable(true);
                                    }
                                }
                            }
                            t.setText(pos[INDEX] + ": " + d.getText());

                        }
                        return true;
                    }
                });
            }

            //Open new activity on "short" click
            official.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Refinfo_Activity.class);
                    textViewOfficial tv = (textViewOfficial) v;
                    intent.putExtra("officialID", tv.getOfficial().getId());
                    startActivity(intent);
                }
            });
        }

        //add already existing officials!
        for(int i=0; i<5; i++)
        {
            if(game.getOfficial(i) != null)
            {
                int resID = getResources().getIdentifier("abc_list_longpressed_holo", "drawable", getPackageName());
                officialsPositions[i].setBackgroundResource(resID);
                officialsPositionsText[i].setText(pos[i] + ": " + game.getOfficial(i).getName());

                //Remove the official from the list
                for(textViewOfficial to : textViews)
                {
                    if(to.getOfficial().getName().equals(game.getOfficial(i).getName()))
                    {
                        //to.setVisibility(View.INVISIBLE);
                        to.setBackgroundColor(Color.GRAY);
                        to.getBackground().setAlpha(75);
                        to.setLongClickable(false);
                    }
                }
            }
        }

        Button okButton = (Button) findViewById(R.id.ok_button_officials);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_officials_picker, menu);
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
}


class textViewOfficial extends TextView
{
    Official off;

    public textViewOfficial(Context context) {
        super(context);
    }

    public void setOfficial(Official o)
    {
        off = o;
    }

    public Official getOfficial()
    {
        return off;
    }
}