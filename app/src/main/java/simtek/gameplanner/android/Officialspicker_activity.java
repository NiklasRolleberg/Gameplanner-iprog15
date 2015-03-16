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
import android.widget.Toast;

import java.util.ArrayList;

import simtek.gameplanner.R;
import simtek.gameplanner.model.Game;
import simtek.gameplanner.model.Model;
import simtek.gameplanner.model.Official;

public class Officialspicker_activity extends ActionBarActivity{

    View currentDrag;
    String[] pos = {"R", "U", "HL", "L", "BJ"};
    LinearLayout officialsPositions[] = new LinearLayout[5];
    textViewOfficial officialsPositionsText[] = new textViewOfficial[5];
    Model model;
    ArrayList<Official> allOfficials;
    ArrayList<textViewOfficial> textViews = new ArrayList<>();
    Game game;
    Official[] officialsToGame = new Official[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officials_layoutr);

        model = ((CustomApplication) this.getApplication()).getModel();

        allOfficials = model.getOfficials();

        int ID = getIntent().getIntExtra("ID",0);
        game = model.getGame(ID);

        //set title (teams)
        setTitle(game.getHomeTeam().getName() + " vs " + game.getAwayTeam().getName());

        Toast toast= Toast.makeText(getApplicationContext(), "Choose officials by dragging the names into the desired fields", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();

        officialsPositions[0] = (LinearLayout) findViewById(R.id.R_officials);
        officialsPositions[1] = (LinearLayout) findViewById(R.id.U_officials);
        officialsPositions[2] = (LinearLayout) findViewById(R.id.HL_officials);
        officialsPositions[3] = (LinearLayout) findViewById(R.id.L_officials);
        officialsPositions[4] = (LinearLayout) findViewById(R.id.BJ_officials);

        for(LinearLayout l : officialsPositions)
        {
            l.setBackgroundResource(R.drawable.red_field);
        }

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
            official.setBackgroundResource(R.drawable.not_chosen_field);
            official.getBackground().setAlpha(130);
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

            //Drag listeners (enable drop)
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
                            currentDrag.setBackgroundResource(R.drawable.chosen_field);
                            currentDrag.getBackground().setAlpha(75);
                            currentDrag.setLongClickable(false);

                            v.setBackgroundResource(R.drawable.tiledesign);

                            int INDEX = getIndex((LinearLayout) v);
                            officialsToGame[INDEX] = d.getOfficial();
                            textViewOfficial t = officialsPositionsText[INDEX];
                            if(game.getOfficial(INDEX) != null)//???
                            {
                                String st = t.getText().toString();
                                for(textViewOfficial to: textViews)
                                {
                                    if(st.substring(2+pos[INDEX].length(),st.length()).equals(to.getText()))
                                    {
                                        to.setBackgroundResource(R.drawable.not_chosen_field);
                                        to.getBackground().setAlpha(130);
                                        to.setLongClickable(true);
                                    }
                                }
                            }
                            t.setText(pos[INDEX] + ": " + d.getText());
                            t.setOfficial(((textViewOfficial) currentDrag).getOfficial());

                            //set onClickListener (short click) on layout
                            officialsPositions[INDEX].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), Refinfo_Activity.class);
                                    int index = getIndex((LinearLayout) v);
                                    Official o = officialsPositionsText[index].getOfficial();
                                    intent.putExtra("officialID", o.getId());
                                    startActivity(intent);
                                }
                            });
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

        //Add text and onClickListeners (short click) on layouts
        for(int i=0; i<5; i++)
        {
            textViewOfficial tv = new textViewOfficial(this);
            officialsPositionsText[i] = tv;

            //check if there is an existing official on this position
            if(game.getOfficial(i) != null)
            {
               // officialsPositions[i].setBackgroundColor(Color.parseColor(green));
                officialsPositions[i].setBackgroundResource(R.drawable.tiledesign);
                officialsPositionsText[i].setText(pos[i] + ": " + game.getOfficial(i).getName());
                officialsPositionsText[i].setOfficial(game.getOfficial(i));
                tv.setText(pos[i] + ": " + game.getOfficial(i).getName());
                tv.setOfficial(game.getOfficial(i));

                //Remove (set gray color and not "drag-able") the official from the list
                for(textViewOfficial to : textViews)
                {
                    if(to.getOfficial().getName().equals(game.getOfficial(i).getName()))
                    {
                        //to.setBackgroundColor(Color.parseColor(gray));
                        to.setBackgroundResource(R.drawable.chosen_field);
                        to.getBackground().setAlpha(75);
                        to.setLongClickable(false);
                    }
                }

                //set onClickListener (short click) on layout
                officialsPositions[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Refinfo_Activity.class);
                        int index = getIndex((LinearLayout) v);
                        intent.putExtra("officialID", officialsPositionsText[index].getOfficial().getId());
                        startActivity(intent);
                    }
                });
            }
            else
            {
                tv.setText(pos[i] + ": [missing]");
            }

            tv.setTextSize(18);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
            params.setMargins(0, 10, 0, 10);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); // :(
            officialsPositions[i].addView(tv);
        }

        //ok button to exit activity
        Button okButton = (Button) findViewById(R.id.ok_button_officials);
        okButton.setBackgroundResource(R.drawable.buttondesign);
        okButton.setTextColor(Color.WHITE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i<5; i++)
                {
                    if(officialsToGame[i] != null)
                    {
                        game.addOfficial(officialsToGame[i],i);
                    }
                }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast toast= Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();

                this.finish();
                return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** getIndex finds the index of one of the 5 LinearLayouts */
    int getIndex(LinearLayout l)
    {
        int index = 0;
        for(int j=0; j<5; j++)
        {
            if(l == officialsPositions[j])
            {
                index = j;
            }
        }
        return index;
    }
}


/* extends TextView with an Official */
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