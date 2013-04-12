
/** 
 * TempRec.java
 * @author Tyler Stacey, 201033446, Mar 29, 2013
 */
package models;
 
import java.text.DateFormat;
import java.util.*;
import javax.persistence.*;
 
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class TempRec extends Model {
	@Required
    public String author;
	@Required
    public Date postedAt;
    @Required
    @Max(200)
    @Min(-200)
    public double content;
    
    @ManyToOne
    @Required
    public Project project;
    
    public TempRec(Project project, String author, double content) {
        this.project = project;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
    public String toString() {
        return content + " \u00b0C";
    }
 
 
}