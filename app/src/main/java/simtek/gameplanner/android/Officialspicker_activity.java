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

        Toast toast= Toast.makeText(getApplicationContext(), "Choose officials by dragging the names into the desired fields", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();

        officialsPositions[0] = (LinearLayout) findViewById(R.id.R_officials);
        officialsPositions[1] = (LinearLayout) findViewById(R.id.U_officials);
        officialsPositions[2] = (LinearLayout) findViewById(R.id.HL_officials);
        officialsPositions[3] = (LinearLayout) findViewById(R.id.L_officials);
        officialsPositions[4] = (LinearLayout) findViewById(R.id.BJ_officials);

        for(int i=0; i<5; i++)
        {
            officialsPositions[i].setId(i);
            if(game.getOfficial(i)==null)
            {
                officialsToGame[i] = null;
            }
            else
            {
                officialsToGame[i] = game.getOfficial(i);
            }
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
            //official.setTag(official.getText());
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
            official.setTag("TextView");
            official.setOnLongClickListener(new View.OnLongClickListener() {
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
                            v.setBackgroundResource(R.drawable.tiledesign);
                            int dropIndex = getIndex((LinearLayout) v); //index of the layout where you dropped something
                            textViewOfficial t = officialsPositionsText[dropIndex];

                            if (officialsToGame[dropIndex] != null)
                            { //if already an official assigned to this position
                                String s = t.getOfficial().getName();
                                System.out.println(s);
                                for (textViewOfficial to : textViews) {
                                    if (s.equals(to.getText())) {
                                        to.setBackgroundResource(R.drawable.not_chosen_field); //add official to list again (clickable etc.)
                                        to.getBackground().setAlpha(130);
                                        to.setLongClickable(true);
                                    }
                                }
                            }

                            v.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), Refinfo_Activity.class);
                                    int index = getIndex((LinearLayout) v);
                                    Official o = officialsPositionsText[index].getOfficial();
                                    intent.putExtra("officialID", o.getId());
                                    startActivity(intent);
                                }
                            });

                            CharSequence cs = event.getClipData().getDescription().getLabel();
                            String type = cs.toString(); //name of the "dragged" official if LinearLayout is dragged or "TextView" if a TextView is dragged

                            if (type.equals("TextView"))
                            {
                                currentDrag.setBackgroundResource(R.drawable.chosen_field);
                                currentDrag.getBackground().setAlpha(130);
                                currentDrag.setLongClickable(false);

                                t.setText(pos[dropIndex] + ": " + d.getText());
                                t.setOfficial(((textViewOfficial) currentDrag).getOfficial());
                                officialsToGame[dropIndex] = ((textViewOfficial) currentDrag).getOfficial();
                            }
                            else
                            { //if a LinearLayout is dragged
                                LinearLayout view = (LinearLayout) event.getLocalState();
                                int dragIndex = view.getId(); //index of dragged (and dropped) layout!
                                t.setText(pos[dropIndex] + ": " + type);
                                officialsToGame[dropIndex] = officialsPositionsText[dragIndex].getOfficial();
                                officialsPositionsText[dropIndex].setOfficial(officialsPositionsText[dragIndex].getOfficial());
                                for(textViewOfficial tv : textViews)
                                {

                                    if(tv.getText().equals(officialsPositionsText[dragIndex].getOfficial().getName()))
                                    {
                                        tv.setLongClickable(false);
                                        tv.setBackgroundResource(R.drawable.chosen_field);
                                        tv.getBackground().setAlpha(75);
                                    }
                                }



                                officialsPositions[dragIndex].setBackgroundResource(R.drawable.red_field);
                                officialsPositionsText[dragIndex].setText(pos[view.getId()] + ": [missing]");
                                officialsPositionsText[dragIndex].setTag(null);
                                officialsPositionsText[dragIndex].setOfficial(null);

                                officialsPositions[dragIndex].setLongClickable(false);
                                officialsPositions[dragIndex].setClickable(false);

                                officialsToGame[dragIndex] = null;
                            }

                            officialsPositions[dropIndex].setTag(((textViewOfficial) currentDrag).getOfficial().getName());
                            layoutOnLongClick(officialsPositions[dropIndex]);
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
            if(officialsToGame[i] != null)
            {
                officialsPositions[i].setBackgroundResource(R.drawable.tiledesign);
                officialsPositionsText[i].setText(pos[i] + ": " + game.getOfficial(i).getName());
                officialsPositionsText[i].setOfficial(game.getOfficial(i));
                officialsPositionsText[i].setTag(game.getOfficial(i).getName());
                officialsToGame[i] = game.getOfficial(i);
                tv.setText(pos[i] + ": " + game.getOfficial(i).getName());
                tv.setOfficial(game.getOfficial(i));

                //Remove (set gray color and not "drag-able") the official from the list
                for(textViewOfficial to : textViews)
                {
                    if(to.getOfficial().getName().equals(game.getOfficial(i).getName()))
                    {
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

                officialsPositions[i].setTag(officialsPositionsText[i].getOfficial().getName());
                layoutOnLongClick(officialsPositions[i]);

            }
            else
            {
                tv.setText(pos[i] + ": [missing]");
                officialsPositions[i].setBackgroundResource(R.drawable.red_field);
            }

            tv.setTextSize(18);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
            params.setMargins(0, 10, 0, 10);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
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
                    else
                    {
                        game.addOfficial(null,i);
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

    public void layoutOnLongClick(LinearLayout l)
    {
       // l.setTag(l.getTag());//??
        l.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

                // Starts the drag
                v.startDrag(dragData, myShadow, v, 0);
                return false;
            }
        });
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