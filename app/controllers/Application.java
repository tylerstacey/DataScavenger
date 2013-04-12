package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;
@With(Secure.class)
public class Application extends Controller {
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.email);
        }
    }
	public static void index() {
        Project frontProject = Project.find("order by postedAt desc").first();
        List<Project> olderProjects = Project.find(
            "order by postedAt desc"
        ).from(1).fetch(10);
        render(frontProject, olderProjects);
    }
	public static void show(Long id) {
	    Project project = Project.findById(id);
	    render(project);
	}
	
	public static void postTempRec(Long postId, @Required String author, @Required double content) {
	    Project project = Project.findById(postId);
	    if (validation.hasErrors()) {
	        render("Application/show.html", project);
	    }
	    project.addTempRec(author, content);
	    flash.success("Thanks for the record %s !", author);
	    show(postId);
	}

}