package simtek.gameplanner.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import simtek.gameplanner.R;

public class Officialspicker_activity extends ActionBarActivity {

    TextView testOfficial;
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officials_layoutr);

        //Move following to View?
        testOfficial = (TextView) findViewById(R.id.testOfficial);
        testOfficial.setTag("hej");
        testOfficial.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // System.out.println("Long click!");

                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(testOfficial);
                // Starts the drag
                LinearLayout dropLayout = (LinearLayout) findViewById(R.id.officials);
                //dropLayout.startDrag(dragData, myShadow, dropLayout, 0);
                v.startDrag(dragData, myShadow, v, 0);

                return false;
            }
        });

        testOfficial.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                switch(event.getAction())
                {
                    case DragEvent.ACTION_DRAG_STARTED:
                        System.out.println("1");
                        //layoutParams = RelativeLayout.LayoutParams;
                        System.out.println("2");
                        v.getLayoutParams();
                        System.out.println("Action is DragEvent.ACTION_DRAG_STARTED");
                        // Do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        System.out.println("Action is DragEvent.ACTION_DRAG_ENTERED");
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_EXITED :
                        System.out.println("Action is DragEvent.ACTION_DRAG_EXITED");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        System.out.println(x_cord);
                        System.out.println(y_cord);
                        //layoutParams.leftMargin = x_cord;
                        //layoutParams.topMargin = y_cord;
                        //v.setLayoutParams(layoutParams);
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION  :
                        //System.out.println("Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENDED   :
                        System.out.println("Action is DragEvent.ACTION_DRAG_ENDED");
                        // Do nothing
                        break;
                    case DragEvent.ACTION_DROP:
                        //handle the dragged view being dropped over a drop view
                        System.out.println("ACTION_DROP event");
                        if(v==findViewById(R.id.officials))
                        {
                            System.out.println("drop");
                        }
                        v.setVisibility(View.INVISIBLE); //stop displaying the text when it has been dropped a correct place

                        // Gets the item containing the dragged data - do we need it.........?
                        //ClipData.Item item = event.getClipData().getItemAt(0);

                        // Do nothing
                        break;
                    default: break;
                }
                return true;

                //return false;
            }
        });

        testOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Refinfo_Activity.class);
                startActivity(intent);
            }
        });
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
}
