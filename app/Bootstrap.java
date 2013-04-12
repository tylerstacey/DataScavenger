
/** 
 * Bootstrap.java
 * @author Tyler Stacey, 201033446, Mar 29, 2013
 */
import org.junit.Before;

import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
    	
        //Fixtures.deleteDatabase();
        // Check if the database is empty
        if(User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }
    }
 
}
