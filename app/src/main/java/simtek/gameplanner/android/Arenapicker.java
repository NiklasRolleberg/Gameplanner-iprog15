package simtek.gameplanner.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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

import java.util.ArrayList;

import simtek.gameplanner.R;
import simtek.gameplanner.model.Arena;
import simtek.gameplanner.model.Model;

public class Arenapicker extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    int gameID;
    int arenaID;
    Spinner vSpinner;
    int currentTicketPrice;
    double currentTurnout;
    int currentRentCost;
    int currentCapacity;
    int currentRevenue;
    TextView capacity;
    TextView rentCost;
    TextView ticketPrice;
    TextView turnout;
    TextView revenue;
    Button okButton;
    Model myModel;
    int selectedPosition;
    Arena currentArena;
    private ArrayList<Arena> items;
    private ArrayList<String> arenaNames;

    //String[] items = new String[]{"Super mega stadium 1", "Awesome arena 42"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arenapicker_layout);
        //TODO set selected arena in the spinner to the current one!
        setValues();
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
        //System.out.println(arenaNames.get(position));
        selectedPosition = position;
        updateValues(items.get(selectedPosition).getId());
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
            setTurnoutFromSlider();
        }
        if (clickedID == R.id.arenapicker_okButton){    //OK button
            //todo add update stuff here later!
            myModel.getGame(gameID).setArena(items.get(selectedPosition));
            myModel.getGame(gameID).setTurnout((int)currentTurnout*100);
            myModel.getGame(gameID).setTicketPrice(currentTicketPrice);
            finish();

        }
    }
    /**Called by constructor, possibly by the dropdown list*/
    private void setValues(){
        //initiate component values
        gameID = getIntent().getIntExtra("ID", 1);
        arenaID = getIntent().getIntExtra("arenaID", 1);
        vSpinner = (Spinner) findViewById(R.id.venueSpinner);
        capacity = (TextView) findViewById(R.id.arenapicker_capacity);
        ticketPrice = (TextView) findViewById(R.id.arenapicker_ticketPrice);
        turnout = (TextView) findViewById(R.id.arenapicker_turnout);
        rentCost = (TextView) findViewById(R.id.arenapicker_rentCost);
        revenue = (TextView) findViewById(R.id.arenapicker_revenue);
        okButton = (Button) findViewById(R.id.arenapicker_okButton);
        okButton.setBackgroundResource(R.drawable.buttondesign);
        okButton.setTextColor(Color.WHITE);
        //ticketPrice.setBackgroundResource(R.drawable.buttondesign);
        //turnout.setBackgroundResource(R.drawable.buttondesign);

        //SELECT name FROM arenas, later: SELECT ID FROM arenas WHERE name = "chosen item thingy"
        myModel = ((CustomApplication) this.getApplication()).getModel();

        items = myModel.getArenas();
        currentArena = myModel.getGame(gameID).getArena();
        arenaNames = new ArrayList<>(items.size());
        for (Arena a : items){
            arenaNames.add(a.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arenaNames);
        vSpinner.setAdapter(adapter);
        vSpinner.setOnItemSelectedListener(this);
        ticketPrice.setOnClickListener(this);
        turnout.setOnClickListener(this);
        okButton.setOnClickListener(this);
        currentRentCost = currentArena.getRentCost();
        currentCapacity = currentArena.getCapacity();
        capacity.setText(" Capacity: " + Integer.toString(currentCapacity));
        rentCost.setText(" Rent cost: " + Integer.toString(currentRentCost));

    }
    private void updateValues(int tempArenaID){
        System.out.println(tempArenaID);
        currentArena = myModel.getArena(tempArenaID);
        currentRentCost = currentArena.getRentCost();
        currentCapacity = currentArena.getCapacity();
        capacity.setText(" Capacity: " + Integer.toString(currentCapacity));
        rentCost.setText(" Rent cost: " + Integer.toString(currentRentCost));
        //todo what else happens here?

        //reset ticket price and turnout?
        currentTicketPrice = 0;
        currentTurnout = 0;
        ticketPrice.setText(" Ticket price: ");
        turnout.setText(" Turnout: ");
        revenue.setText("");

    }
    private void setTurnoutFromSlider(){

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
                currentTurnout = (double)currentTurnout * 0.01;
                System.out.println(currentTurnout);
                Toast.makeText(getApplicationContext(), "Turnout set to: " + currentTurnout * 100 + " %", Toast.LENGTH_LONG).show();
                turnout.setText(" Turnout: " + currentTurnout * 100 + " % ");

                currentRevenue = (int)((double)currentTicketPrice * (double)currentCapacity * currentTurnout);
                revenue.setText(Integer.toString(currentRevenue));

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
                progressLabel.setText((String.valueOf(tempTurnout)) + " %");

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
                ticketPrice.setText(" Ticket price: " + currentTicketPrice + " ");
                currentRevenue = (int)((double)currentTicketPrice * (double)currentCapacity * currentTurnout);
                revenue.setText(Integer.toString(currentRevenue));
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
