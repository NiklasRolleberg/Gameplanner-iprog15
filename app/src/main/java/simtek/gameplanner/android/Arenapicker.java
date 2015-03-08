package simtek.gameplanner.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import simtek.gameplanner.R;

public class Arenapicker extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    int defaultArenaID;
    Spinner vSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arenapicker_layout);
        TextView test = (TextView) findViewById(R.id.arenapicker_lbl_pick);
        defaultArenaID = getIntent().getIntExtra("ID", 1);
        vSpinner = (Spinner) findViewById(R.id.venueSpinner);
        TextView capacity = (TextView) findViewById(R.id.arenapicker_capacity);
        TextView ticketPrice = (TextView) findViewById(R.id.arenapicker_ticketPrice);
        TextView turnout = (TextView) findViewById(R.id.arenapicker_turnout);
        TextView rentCost = (TextView) findViewById(R.id.arenapicker_rentCost);
        TextView revenue = (TextView) findViewById(R.id.arenapicker_revenue);
        //SELECT name FROM arenas, later: SELECT ID FROM arenas WHERE name = "chosen item thingy"
        String[] items = new String[]{"Super mega stadium 1", "Awesome arena 42"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        vSpinner.setAdapter(adapter);
        vSpinner.setOnItemSelectedListener(this);
        ticketPrice.setOnClickListener(this);
        turnout.setOnClickListener(this);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arenapicker, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int clickedID = v.getId();
        if (clickedID == R.id.arenapicker_ticketPrice) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Ticket pricing");
            //alert.setMessage("Edit Text");

            LinearLayout linear = new LinearLayout(this);

            linear.setOrientation(LinearLayout.VERTICAL);
            TextView text = new TextView(this);
            //text.setText("Set ticket price!");
            text.setPadding(10, 10, 10, 10);

            SeekBar seek = new SeekBar(this);


            linear.addView(seek);
            linear.addView(text);

            alert.setView(linear);


            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getApplicationContext(), "OK Pressed", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getApplicationContext(), "Cancel Pressed", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            alert.show();
        }
    }
}
