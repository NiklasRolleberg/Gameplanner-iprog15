package simtek.gameplanner.android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import simtek.gameplanner.R;
import simtek.gameplanner.model.Model;
import simtek.gameplanner.model.Official;

public class Refinfo_Activity extends ActionBarActivity {

    Model model;
    Official currentOfficial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refinfo_layout);

        int ID = getIntent().getIntExtra("officialID",0);
        model = ((CustomApplication) this.getApplication()).getModel();

        for(Official o: model.getOfficials())
        {
            if(o.getId() == ID)
            {
                currentOfficial = o;
            }
        }

        TextView name = (TextView) findViewById(R.id.name_official);
        name.setText(currentOfficial.getName());

        TextView license = (TextView) findViewById(R.id.license);
        license.setText(license.getText() + currentOfficial.getLicenseType());

        TextView resArea = (TextView) findViewById(R.id.residence);
        resArea.setText(resArea.getText() + currentOfficial.getResArea());

        ImageView image = (ImageView) findViewById(R.id.official_image);
        int resID = getResources().getIdentifier("official", "drawable", getPackageName());
        image.setBackgroundResource(resID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_refinfo_, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
