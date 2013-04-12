
/** 
 * Admin.java
 * @author Tyler Stacey, 201033446, Mar 30, 2013
 */
package controllers;

import play.*;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }
 
    public static void index() {
    	String user = Security.connected();
        List<Project> projects = Project.find("author.email", user).fetch();
        render(projects);
    }
    
    public static void form(Long id) {
        if(id != null) {
            Project project = Project.findById(id);
            render(project);
        }
        render();
    }
    public static void form() {
        render();
    }
     
    public static void save(Long id, String title, String content) {
    	Project project;
        if(id == null) {
            // Create project
            User author = User.find("byEmail", Security.connected()).first();
            project = new Project(author, title, content);
        } else {
            // Retrieve project
            project = Project.findById(id);
            // Edit
            project.title = title;
            project.content = content;
        }
        // Validate
        validation.valid(project);
        if(validation.hasErrors()) {
            render("@form", project);
        }
        // Save
        project.save();
        index();
    }
    
}
