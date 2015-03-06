package simtek.gameplanner.android;

import android.app.Application;
import simtek.gameplanner.model.Model;

public class CustomApplication extends Application {

   private Model model = new Model();

    public Model getModel() {
        return this.model;
    }


}
