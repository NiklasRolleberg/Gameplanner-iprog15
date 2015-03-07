package simtek.gameplanner.android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import simtek.gameplanner.R;

public class Arenapicker extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    int defaulArenaID;
    Spinner vSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arenapicker_layout);
        TextView test = (TextView) findViewById(R.id.arenapicker_lbl_pick);
        defaulArenaID = getIntent().getIntExtra("ID", 1);
        vSpinner = (Spinner) findViewById(R.id.venueSpinner);
        //SELECT name FROM arenas, later: SELECT ID FROM arenas WHERE name = "chosen item thingy"
        String[] items = new String[]{"Super mega stadium 1", "Awesome arena 42"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        vSpinner.setAdapter(adapter);
        vSpinner.setOnItemSelectedListener(this);




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
}
