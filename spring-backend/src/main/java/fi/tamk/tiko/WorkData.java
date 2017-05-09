package fi.tamk.tiko;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	public long getId(){
		return id;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


	public WorkData(){
		
	}

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