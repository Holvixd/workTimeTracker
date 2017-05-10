package fi.tamk.tiko;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Workdata is the object that contains info about the work and it's
 * put into the database.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */
@Entity
public class WorkData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    String name;
    String subject;
    String company;
    String startTime;
    String startDate;
    String endTime;
    String endDate;
    String userName;

   /**
    * Gets the id.
    *
    * @return  Id of workdata
    * @version 4.0
    * @since   1.0
    */
    public long getId(){
	return id;
    }

   /**
    * Gets the name.
    *
    * @return  Name of user
    * @version 4.0
    * @since   1.0
    */
    public String getName() {
        return name;
    }

   /**
    * Sets the name.
    *
    * @param name   Name of the user
    * @version      4.0
    * @since        1.0
    */
    public void setName(String name) {
        this.name = name;
    }

   /**
    * Gets the company.
    *
    * @return  Company of workdata
    * @version 4.0
    * @since   1.0
    */
    public String getCompany() {
        return company;
    }

   /**
    * Sets the company.
    *
    * @param company    Company of the workdata
    * @version          4.0
    * @since            1.0
    */
    public void setCompany(String company) {
        this.company = company;
    }

   /**
    * Gets the subject.
    *
    * @return  Subject of workdata
    * @version 4.0
    * @since   1.0
    */
    public String getSubject() {
        return subject;
    }

   /**
    * Sets the subject.
    *
    * @param subject    Subject of the workdata
    * @version          4.0
    * @since            1.0
    */
    public void setSubject(String subject) {
        this.subject = subject;
    }

   /**
    * Gets the staring time.
    *
    * @return  Starting time of workdata
    * @version 4.0
    * @since   1.0
    */
    public String getStartTime() {
        return startTime;
    }

   /**
    * Sets the staring time.
    *
    * @param startTime  Starting time of the workdata
    * @version          4.0
    * @since            1.0
    */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

   /**
    * Gets the start date.
    *
    * @return  Starting date of workdata
    * @version 4.0
    * @since   1.0
    */
    public String getStartDate() {
        return startDate;
    }

   /**
    * Sets the starting date.
    *
    * @param startDate  Starting date of the workdata
    * @version          4.0
    * @since            1.0
    */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

   /**
    * Gets the ending time.
    *
    * @return  End time of workdata
    * @version 4.0
    * @since   1.0
    */
    public String getEndTime() {
        return endTime;
    }

   /**
    * Sets the ending time.
    *
    * @param endTime    Ending time of the workdata
    * @version          4.0
    * @since            1.0
    */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

   /**
    * Gets the ending date.
    *
    * @return  End date of workdata
    * @version 4.0
    * @since   1.0
    */
    public String getEndDate() {
        return endDate;
    }

   /**
    * Sets the ending date.
    *
    * @param endDate    Ending date of the workdata
    * @version          4.0
    * @since            1.0
    */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
   /**
    * Gets the username.
    *
    * @return  Username of the user.
    * @version 4.0
    * @since   1.0
    */
    public String getUserName() {
        return userName;
    }

   /**
    * Sets the username.
    *
    * @param userName   Username of the user
    * @version          4.0
    * @since            1.0
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

   /**
    * Constructs the empty workdata.
    *
    * @version          4.0
    * @since            1.0
    */
    public WorkData(){
		
    }

   /**
    * Constructs the workdata.
    *
    * @param name   Name of the user
    * @param company    Company of the workdata
    * @param subject    Subject of the workdata
    * @param startTime  Starting time of the workdata
    * @param startDate  Starting date of the workdata
    * @param endDate    Ending date of the workdata
    * @param userName   Username of the user
    * @param endTime    Ending time of the workdata
    * @version          4.0
    * @since            1.0
    */
    public WorkData(String name, String company, String subject, String startTime, String startDate, String endTime, String endDate, String userName) {
        this.name = name;
	this.company = company;
        this.subject = subject;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
	this.userName = userName;
    }

   /**
    * Transforms the workdata into a json String.
    * 
    * @return           Workdata as json string
    * @version          4.0
    * @since            1.0
    */
    @java.lang.Override
    public java.lang.String toString() {
        return "WorkData{" +
                "name='" + name + '\'' +
		", company='" + company + '\'' +
                ", subject='" + subject + '\'' +
                ", startTime='" + startTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endDate='" + endDate + '\'' +
		", userName='" + userName + '\'' +
		", id='" + id + '\'' +
                '}';
    }
}