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
    String startTime;
    String startDate;
    String endTime;
    String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


	public WorkData(){
		
	}

    public WorkData(String name, String subject, String startTime, String startDate, String endTime, String endDate) {
        this.name = name;
        this.subject = subject;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "WorkData{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", startTime='" + startTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}