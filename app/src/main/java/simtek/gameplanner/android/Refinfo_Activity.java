package simtek.gameplanner.android;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
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
        license.setText(currentOfficial.getLicenseType());

        TextView resArea = (TextView) findViewById(R.id.residence);
        resArea.setText(currentOfficial.getResArea());

        TextView age = (TextView) findViewById(R.id.age);
        age.setText(currentOfficial.getAge());

        TextView years = (TextView) findViewById(R.id.years);
        years.setText(""+(currentOfficial.getYearsOfXp()));

        TextView prefPos = (TextView) findViewById(R.id.prefPos);
        prefPos.setText(currentOfficial.getPrefPos());


        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setRating(currentOfficial.getRating());
        ratingBar.setIsIndicator(true);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#33B5E5"), PorterDuff.Mode.SRC_ATOP);

        ImageView image = (ImageView) findViewById(R.id.official_image);
        String imageName = currentOfficial.getImage();
        Drawable im = getResources().getDrawable(getResources().getIdentifier(imageName, "drawable", getPackageName()));
        image.setImageDrawable(im);


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
        switch (item.getItemId()) {
            case android.R.id.home:
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
}
