
/** 
 * Project.java
 * @author Tyler Stacey, 201033446, Mar 29, 2013
 */
package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class Project extends Model {
	@Required
    public String title;
	@Required
    public Date postedAt;
    
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @Required
    @ManyToOne
    public User author;
    
    @OneToMany(mappedBy="project", cascade=CascadeType.ALL)
    public List<TempRec> recs;
    
    public Project(User author, String title, String content) {
    	this.recs = new ArrayList<TempRec>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
    
    public Project addTempRec(String author, double content) {
        TempRec newTempRec = new TempRec(this, author, content).save();
        this.recs.add(newTempRec);
        this.save();
        return this;
    }
    
    public Project previous() {
        return Project.find("postedAt < ? order by postedAt desc", postedAt).first();
    }
     
    public Project next() {
        return Project.find("postedAt > ? order by postedAt asc", postedAt).first();
    }
    
    public String toString() {
        return title;
    }
 
 
}