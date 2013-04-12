import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	
	@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	@Test
	public void createAndRetrieveUser() {
	    // Create a new user and save it
	    new User("rod@mun.ca", "rod", "Rod Byrne").save();
	    
	    // Retrieve the user with e-mail address rod@mun.ca
	    User rod = User.find("byEmail", "rod@mun.ca").first();
	    
	    // Test 
	    assertNotNull(rod);
	    assertEquals("Rod Byrne", rod.fullname);
	}
	@Test
	public void tryConnectAsUser() {
	    // Create a new user and save it
	    new User("rod@mun.ca", "rod", "Rod Byrne").save();
	    
	    // Test 
	    assertNotNull(User.connect("rod@mun.ca", "rod"));
	    assertNull(User.connect("rod@mun.ca", "BADrod"));
	    assertNull(User.connect("tom@mun.ca", "rod"));
	}
	@Test
	public void createProject() {
	    // Create a new user and save it
	    User rod = new User("rod@mun.ca", "rod", "Rod Byrne").save();
	    
	    // Create a new project
	    new Project(rod, "My first project", "Hello world").save();
	    
	    // Test that the project has been created
	    assertEquals(1, Project.count());
	    
	    // Retrieve all projects created by Rod Byrne
	    List<Project> rodProjects = Project.find("byAuthor", rod).fetch();
	    
	    // Tests
	    assertEquals(1, rodProjects.size());
	    Project firstProject = rodProjects.get(0);
	    assertNotNull(firstProject);
	    assertEquals(rod, firstProject.author);
	    assertEquals("My first project", firstProject.title);
	    assertEquals("Hello world", firstProject.content);
	    assertNotNull(firstProject.postedAt);
	}
	@SuppressWarnings("deprecation")
	@Test
	public void projectTempRecs() {
	    // Create a new user and save it
	    User rod = new User("rod@mun.ca", "rod", "Rod Byrne").save();
	 
	    // Create a new project
	    Project rodProject = new Project(rod, "My first project", "Hello world").save();
	 
	    // Add a first record
	    new TempRec(rodProject, "Jeff", 23.0).save();
	    new TempRec(rodProject, "Tom", 20.0).save();
	 
	    // Retrieve all records
	    List<TempRec> rodProjectTempRecs = TempRec.find("byProject", rodProject).fetch();
	 
	    // Tests
	    assertEquals(2, rodProjectTempRecs.size());
	 
	    TempRec firstTempRec = rodProjectTempRecs.get(0);
	    assertNotNull(firstTempRec);
	    assertEquals("Jeff", firstTempRec.author);
	    assertEquals(23.0, firstTempRec.content,0.0);
	    assertNotNull(firstTempRec.postedAt);
	 
	    TempRec secondTempRec = rodProjectTempRecs.get(1);
	    assertNotNull(secondTempRec);
	    assertEquals("Tom", secondTempRec.author);
	    assertEquals(20.0, secondTempRec.content,0.0);
	    assertNotNull(secondTempRec.postedAt);
	}
	@Test
	public void useTheCommentsRelation() {
	    // Create a new user and save it
	    User rod = new User("rod@gmail.com", "rod", "Rod Byrne").save();
	 
	    // Create a new project
	    Project rodProject = new Project(rod, "My first post", "Hello world").save();
	 
	    // Add a first data
	    rodProject.addTempRec("Jeff", 23.0);
	    rodProject.addTempRec("Tom", 20.0);
	 
	    // Count things
	    assertEquals(1, User.count());
	    assertEquals(1, Project.count());
	    assertEquals(2, TempRec.count());
	 
	    // Retrieve Rod Byrne's post
	    rodProject = Project.find("byAuthor", rod).first();
	    assertNotNull(rodProject);
	 
	    // Navigate to temp recs
	    assertEquals(2, rodProject.recs.size());
	    assertEquals("Jeff", rodProject.recs.get(0).author);
	    
	    // Delete the post
	    rodProject.delete();
	    
	    // Check that all temp recs have been deleted
	    assertEquals(1, User.count());
	    assertEquals(0, Project.count());
	    assertEquals(0, TempRec.count());
	}

}
