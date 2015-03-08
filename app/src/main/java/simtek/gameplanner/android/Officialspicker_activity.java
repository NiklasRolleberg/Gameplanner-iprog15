package simtek.gameplanner.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    Model model;
    ArrayList<Official> allOfficials;
    Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officials_layoutr);

        model = ((CustomApplication) this.getApplication()).getModel();
        allOfficials = model.getOfficials();

        //Move following to View?
        R_layout = (LinearLayout) findViewById(R.id.R_officials);
        U_layout = (LinearLayout) findViewById(R.id.U_officials);
        HL_layout = (LinearLayout) findViewById(R.id.HL_officials);
        L_layout = (LinearLayout) findViewById(R.id.L_officials);
        BJ_layout = (LinearLayout) findViewById(R.id.BJ_officials);

        //add text views
        LinearLayout scroll = (LinearLayout) findViewById(R.id.scroll_linear);

        for(Official o : allOfficials)
        {
            //Set "view" for text view
            textViewOfficial official = new textViewOfficial(this);
            official.setOfficial(o);
            official.setText(o.getName());
            official.setTag(official.getText());
            official.setTextSize(18);
            int resID = getResources().getIdentifier("abc_list_longpressed_holo", "drawable", getPackageName());
            official.setBackgroundResource(resID);
            official.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)official.getLayoutParams();
            params.setMargins(0, 5, 0, 5);
            official.setLayoutParams(params);
            scroll.addView(official);

            //Set listener for the drag and drop events.
            listeners(R_layout, official);
            listeners(U_layout, official);
            listeners(HL_layout, official);
            listeners(L_layout, official);
            listeners(BJ_layout, official);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_officials_picker, menu);
        return true;
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

    public void listeners(View drop, View drag)
    {
        currentDrag = drag;

        //currentDrag.setTag("CURRENT_DRAG");
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
                v.startDrag(dragData, myShadow, v, 0);

                return false;
            }
        });

        //Open new activity on "short" click
        currentDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Refinfo_Activity.class);
                startActivity(intent);
            }
        });

        //Drag listener
        drop.setOnDragListener(new View.OnDragListener() {

            textViewOfficial d;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                d = (textViewOfficial) currentDrag;
                switch (event.getAction()) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        break;
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        break;
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        break;
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        break;
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        break;
                    case DragEvent.ACTION_DROP:
                        //Official o = allOfficials.get(v.ge);

                        //handle the dragged view being dropped over a drop view
                        currentDrag.setVisibility(View.INVISIBLE); //stop displaying the text when it has been dropped a correct place

                        //listan borde "åka ihop" också när man tar bort en...
                        //om man lägger den på ett ställe där det redan finns en så ska den gamla komma tillbaks!

                        int resID = getResources().getIdentifier("abc_list_longpressed_holo", "drawable", getPackageName());
                        v.setBackgroundResource(resID);

                        TextView t = new TextView(getBaseContext());

                        String s = v.getResources().getResourceName(v.getId());
                        String S = "";
                        if(s.substring(22, 23).equals("H") || s.substring(22, 23).equals("B"))
                        {
                            S = s.substring(22, 24);
                        }
                        else
                        {
                            S = s.substring(22, 23);
                        }

                        int index = 0;
                        if(S.equals("R"))
                        {
                            index = 0;
                        }
                        else if(S.equals("U"))
                        {
                            index = 1;
                        }
                        else if(S.equals("HL"))
                        {
                            index = 2;
                        }
                        else if(S.equals("L"))
                        {
                            index = 3;
                        }
                        else if(S.equals("BJ"))
                        {
                            index = 4;
                        }
                        else
                        {
                            System.out.println("Error: no index available");
                        }

                        Official of = d.getOfficial();
                        game.addOfficial(of, index);
                        System.out.println("hej");

                        int ID = getResources().getIdentifier(S+"_text", "id", getPackageName());
                        t = (TextView) findViewById(ID);

                        t.setText(S + ": " + d.getText());
                        //t.setText(game.getOfficial(index).getName());

                        //Fixa: Open new activity on "short" click

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
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