
/** 
 * Security.java
 * @author Tyler Stacey, 201033446, Mar 30, 2013
 */

package controllers;
import models.*;

public class Security extends Secure.Security {
	
	static boolean authenticate(String username, String password) {
		renderArgs.put("user", username);
		return User.connect(username, password) != null;
    }
	static void onDisconnected() {
	    Application.index();
	}
	static void onAuthenticated() {
	    Admin.index();
	}
	static boolean check(String profile) {
	    if("admin".equals(profile)) {
	        return User.find("byEmail", connected()).<User>first().isAdmin;
	    }
	    return false;
	}
}
