package simtek.gameplanner.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import simtek.gameplanner.R;

public class Arenapicker extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    int defaultArenaID;
    Spinner vSpinner;
    int currentTicketPrice;
    int currentTurnout;
    TextView capacity;
    TextView rentCost;
    TextView ticketPrice;
    TextView turnout;
    TextView revenue;
    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.arenapicker_layout);

        //initiate component values
        defaultArenaID = getIntent().getIntExtra("ID", 1);
        vSpinner = (Spinner) findViewById(R.id.venueSpinner);
        capacity = (TextView) findViewById(R.id.arenapicker_capacity);
        ticketPrice = (TextView) findViewById(R.id.arenapicker_ticketPrice);
        turnout = (TextView) findViewById(R.id.arenapicker_turnout);
        rentCost = (TextView) findViewById(R.id.arenapicker_rentCost);
        revenue = (TextView) findViewById(R.id.arenapicker_revenue);
        okButton = (Button) findViewById(R.id.arenapicker_okButton);

        //SELECT name FROM arenas, later: SELECT ID FROM arenas WHERE name = "chosen item thingy"
        String[] items = new String[]{"Super mega stadium 1", "Awesome arena 42"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        vSpinner.setAdapter(adapter);
        vSpinner.setOnItemSelectedListener(this);
        ticketPrice.setOnClickListener(this);
        turnout.setOnClickListener(this);
        okButton.setOnClickListener(this);
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
            setTicketPriceFromSlider();
        }
        if (clickedID == R.id.arenapicker_turnout){
            setTurnoutPriceFromSlider();
        }
        if (clickedID == R.id.arenapicker_okButton){
            //todo add update stuff here later!
            finish();

        }
    }

    private void setTurnoutPriceFromSlider(){

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Set expected turnout [%]");
        //alert.setMessage("Edit Text");

        LinearLayout linear = new LinearLayout(this);

        linear.setOrientation(LinearLayout.VERTICAL);
        final TextView progressLabel = new TextView(this);
        progressLabel.setText("0");
        progressLabel.setPadding(10, 10, 10, 10);

        final SeekBar seek = new SeekBar(this);


        linear.addView(seek);
        linear.addView(progressLabel);

        alert.setView(linear);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                currentTurnout = seek.getProgress();
                Toast.makeText(getApplicationContext(), "Turnout set to: " + currentTurnout + " %", Toast.LENGTH_LONG).show();
                turnout.setText(" Turnout: " + currentTurnout + " % ");

                //finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                //finish();
            }
        });

        seek.setMax(100);
        alert.show();
        //final int[] ticketBarValue = new int[1];
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int tempTurnout = seek.getProgress();
                progressLabel.setText((String.valueOf(tempTurnout)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


    }
    private void setTicketPriceFromSlider(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Ticket pricing");
        //alert.setMessage("Edit Text");

        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);

        final SeekBar seek = new SeekBar(this);
        linear.addView(seek);

        alert.setView(linear);

        final TextView progressLabel = new TextView(this);
        progressLabel.setText("0");
        progressLabel.setPadding(10, 10, 10, 10);
        linear.addView(progressLabel);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                currentTicketPrice = seek.getProgress();
                Toast.makeText(getApplicationContext(), "Ticket price set to: "+ currentTicketPrice, Toast.LENGTH_LONG).show();
                ticketPrice.setText("Ticket price: " + currentTicketPrice);
                //finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                //finish();
            }
        });

        alert.show();
        //final int[] ticketBarValue = new int[1];
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int tempTurnout = seek.getProgress();
                progressLabel.setText((String.valueOf(tempTurnout)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


    }/* //todo failed attempt at breaking out the seekbar in a single method call, in order to avoid code repetition
    private int getValueFromSeekBar(String title, String lbl_min,String progress_lbl_init,
                                    final String ok_toast, final String textViewValue, int defaultValue)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(title);
        //alert.setMessage("Edit Text");

        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);

        final SeekBar seek = new SeekBar(this);
        linear.addView(seek);

        alert.setView(linear);

        final TextView progressLabel = new TextView(this);
        progressLabel.setText(lbl_min);
        progressLabel.setPadding(10, 10, 10, 10);
        linear.addView(progressLabel);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                defaultValue = seek.getProgress();
                Toast.makeText(getApplicationContext(), ok_toast + currentTicketPrice, Toast.LENGTH_LONG).show();
                ticketPrice.setText(textViewValue + currentTicketPrice);
                //finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                //finish();
            }
        });

        alert.show();
        //final int[] ticketBarValue = new int[1];
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int tempValue = seek.getProgress();
                progressLabel.setText((String.valueOf(tempValue)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //if
        return 1;
    }*/

}
