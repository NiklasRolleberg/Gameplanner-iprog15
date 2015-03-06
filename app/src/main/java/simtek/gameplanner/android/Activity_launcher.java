package simtek.gameplanner.android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import simtek.gameplanner.R;
import simtek.gameplanner.model.Model;

public class Activity_launcher extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //Find buttons
        Button startAcrivity01 = (Button)findViewById(R.id.button);
        Button startAcrivity02 = (Button)findViewById(R.id.button2);
        Button startAcrivity03 = (Button)findViewById(R.id.button3);

        //add OnClicklistener
        startAcrivity01.setOnClickListener(this);
        startAcrivity02.setOnClickListener(this);
        startAcrivity03.setOnClickListener(this);

        //Load model
        Model model = ((CustomApplication) this.getApplication()).getModel();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_launcher, menu);
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
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Intent intent = new Intent(this, Activity_01.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, Activity_02.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.button3) {
            Intent intent = new Intent(this, Activity_03.class);
            startActivity(intent);
        }
    }
}
