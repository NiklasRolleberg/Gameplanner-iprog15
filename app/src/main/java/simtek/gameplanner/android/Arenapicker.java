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
import java.util.ConcurrentModificationException;

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
    String arenaLocation;
    int visitors;
    TextView capacity;
    TextView rentCost;
    //TextView ticketPrice;
    //TextView turnout;
    TextView revenue;
    TextView locationLbl;
    TextView visitorsLbl;
    TextView ticketMin;
    TextView ticketMax;
    TextView turnoutMin;
    TextView turnoutMax;
    SeekBar ticketBar;
    SeekBar turnoutBar;
    Button okButton;
    Model myModel;
    int arenaMaxTicketPrice;
    int selectedPosition;
    Arena currentArena;
    private ArrayList<Arena> items;
    private ArrayList<String> arenaNames;

    //String[] items = new String[]{"Super mega stadium 1", "Awesome arena 42"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arenapicker_layout);

        setValues();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arenapicker, menu);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //System.out.println(arenaNames.get(position));
        selectedPosition = position;
        updateValues(items.get(selectedPosition).getId());
        updateTicketPriceLabel(items.get(selectedPosition).getId());
        if (!myModel.getGame(gameID).getArena().equals(myModel.getArenas().get(position))){
            ticketBar.setProgress(0);
            turnoutBar.setProgress(0);
        }
        else{
            ticketBar.setProgress(myModel.getGame(gameID).getTicketPrice());
            turnoutBar.setProgress((int)myModel.getGame(gameID).getTurnout());

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int clickedID = v.getId();

        if (clickedID == R.id.arenapicker_okButton){    //OK button
            myModel.getGame(gameID).setArena(items.get(selectedPosition));
            myModel.getGame(gameID).setTurnout(currentTurnout*100);
            myModel.getGame(gameID).setTicketPrice(currentTicketPrice);
            myModel.getGame(gameID).setVisitors(visitors);
            myModel.getGame(gameID).setRentCost(currentRentCost);
            myModel.getGame(gameID).setRentCost(currentRentCost);

            finish();

        }
    }

    /**Called by constructor*/
    private void setValues(){
        //initiate component values
        gameID = getIntent().getIntExtra("ID", 1);
        arenaID = getIntent().getIntExtra("arenaID", 1);
        vSpinner = (Spinner) findViewById(R.id.venueSpinner);
        capacity = (TextView) findViewById(R.id.arenapicker_capacity);

        //ticketPrice = (TextView) findViewById(R.id.arenapicker_ticketPrice);
        //turnout = (TextView) findViewById(R.id.arenapicker_turnout);
        rentCost = (TextView) findViewById(R.id.arenapicker_rentCost);
        revenue = (TextView) findViewById(R.id.arenapicker_revenue);
        locationLbl = (TextView) findViewById(R.id.arenapicker_location);
        visitorsLbl = (TextView) findViewById(R.id.arenapicker_visitors);
        ticketMax = (TextView) findViewById(R.id.arenapicker_ticketBarMaxLbl);
        ticketMin = (TextView) findViewById(R.id.arenapicker_ticketBarMinLbl);

        turnoutMin = (TextView) findViewById(R.id.arenapicker_turnoutBarMinLbl);
        turnoutMax = (TextView) findViewById(R.id.arenapicker_turnoutBarMaxLbl);
        ticketBar = (SeekBar) findViewById(R.id.arenapicker_ticketBar);
        turnoutBar = (SeekBar) findViewById(R.id.arenapicker_turnoutBar);


        okButton = (Button) findViewById(R.id.arenapicker_okButton);
        okButton.setBackgroundResource(R.drawable.buttondesign);
        okButton.setTextColor(Color.WHITE);

        //ticketPrice.setBackgroundResource(R.drawable.buttondesign);
        //turnout.setBackgroundResource(R.drawable.buttondesign);

        //SELECT name FROM arenas, later: SELECT ID FROM arenas WHERE name = "chosen item thingy"
        myModel = ((CustomApplication) this.getApplication()).getModel();

        items = myModel.getArenas();
        currentArena = myModel.getGame(gameID).getArena();
        arenaLocation = currentArena.getLocation();
        locationLbl.setText(" Location: " + arenaLocation);

        arenaNames = new ArrayList<>(items.size());
        for (Arena a : items){
            arenaNames.add(a.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, arenaNames);
        vSpinner.setAdapter(adapter);
        vSpinner.setOnItemSelectedListener(this);
        //make sure current arena is selected in spinner on startup
        String currentArenaName = currentArena.getName();
        int i = 0;
        for (Arena a : items){
            if (a.getName().equals(currentArenaName)){
                vSpinner.setSelection(i);
            }
            i++;
        }
        okButton.setOnClickListener(this);
        currentRentCost = currentArena.getRentCost();
        currentCapacity = currentArena.getCapacity();
        capacity.setText(" Capacity: " + Integer.toString(currentCapacity) + " visitors");
        rentCost.setText(" Rent cost: " + Integer.toString(currentRentCost)  + " €");

        currentTicketPrice = myModel.getGame(gameID).getTicketPrice();
        if (currentTicketPrice!=0){
            //ticketPrice.setText(" Ticket price: " + currentTicketPrice + " €");
            //System.out.println(currentTicketPrice + "-----------");
        }
        currentTurnout = myModel.getGame(gameID).getTurnout();
        updateTicketPriceLabel(myModel.getGame(gameID).getArena().getID());


        if (currentTurnout!=0){
            //turnout.setText(" Turnout: " + currentTurnout + "%");
        }

        ticketBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //todo update labels, set currentprice to bar value
                currentTicketPrice = progress;
                ticketMin.setText(progress + " €");
                currentTicketPrice = progress;
                updateRevenue();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        turnoutBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTurnout = (double)progress/100.0;
                turnoutMin.setText(progress + " %");
                updateRevenue();
                visitors = (int)(currentCapacity * currentTurnout);
                visitorsLbl.setText(" Projected nr of visitors: " + visitors);
                //todo update visitors
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ticketBar.setProgress(currentTicketPrice);
        //System.out.println(currentTurnout + "------------------------------------------");
        turnoutBar.setProgress((int)currentTurnout);

        updateRevenue();

    }
    private void updateRevenue(){

        currentRevenue = (int)((double)currentTicketPrice * (int)((double)currentCapacity * currentTurnout) - currentRentCost)
                        - myModel.getGame(gameID).getRefCost();
        revenue.setText(" Projected revenue: " + Integer.toString(currentRevenue) + " €");

    }
    private void updateValues(int tempArenaID){
        System.out.println(tempArenaID);
        currentArena = myModel.getArena(tempArenaID);
        currentRentCost = currentArena.getRentCost();
        currentCapacity = currentArena.getCapacity();
        capacity.setText(" Capacity: " + Integer.toString(currentCapacity) + "  visitors");
        rentCost.setText(" Rent cost: " + Integer.toString(currentRentCost) + " €");
        //todo what else happens here?

        //reset ticket price and turnout?
        currentTicketPrice = 0;
        currentTurnout = 0;
        //System.out.println("------------------UPDATE CALLED-----------");
        //ticketPrice.setText(" Ticket price: ");
        //turnout.setText(" Turnout: ");
        //visitors =
        //revenue.setText("");

    }

    private void updateTicketPriceLabel(int arenaID){
        arenaMaxTicketPrice= myModel.getArena(arenaID).getmaxTicketPrice();
        ticketBar.setMax(arenaMaxTicketPrice);
        ticketMax.setText(arenaMaxTicketPrice + " €");
    }



}
