
/** 
 * TempRecs.java
 * @author Tyler Stacey, 201033446, Mar 29, 2013
 */
package controllers;
 
import play.*;
import play.mvc.*;

@Check("admin")
@With(Secure.class)
public class TempRecs extends CRUD {

}
