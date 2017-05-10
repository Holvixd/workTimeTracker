package fi.tamk.tiko;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as a controller.
 *
 * @author  Vilho Stenman
 * @version 4.0
 * @since   1.0
 */
@RestController
public class MyController {
    @Autowired
    WorkDataRepository database;
    
   /**
    * Constructs the controller.
    *
    * @version      4.0
    * @since        1.0
    */
    public MyController() {
    }

   /**
    * Saves the workform to the database.
    *
    * @param c      Workdata
    * @version      4.0
    * @since        1.0
    */
    @RequestMapping(value = "/workForm",  method=RequestMethod.POST)
    public void saveWorkData(@RequestBody WorkData c) {
        database.save(c);
    }
	
   /**
    * Gets all workdata from the database.
    *
    * @return       All workdata
    * @version      4.0
    * @since        1.0
    */
    @RequestMapping(value = "/workForm",  method=RequestMethod.GET)
    public Iterable<WorkData> fetchWorkData() {
        return database.findAll();
    }
	
   /**
    * Gets all dates that a specific user has worked on from the database.
    *
    * @param userName   User's username
    * @return           All dates the user has work on
    * @version          4.0
    * @since            4.0
    */
    @RequestMapping(value = "/dates/{userName}",  method=RequestMethod.GET)
    public List<String> fetchWorkDates(@PathVariable String userName) {
		List<String> list = new ArrayList<>();
		for(WorkData c : database.findAll()) {
			if(userName.equals(c.getUserName())){
				list.add(c.getStartDate());
				System.out.println(c.getStartDate());
			}

        }
        return list;
    }
	
   /**
    * Updates workdata with a specific id.
    *
    * @param id     Id of the data
    * @param data   New data
    * @version      4.0
    * @since        3.0
    */
    @RequestMapping(value = "/update/{id}",  method=RequestMethod.POST)
    public void updateWork(@PathVariable long id, @RequestBody WorkData data) {
		database.delete(id);
		database.save(data);
    }
	
   /**
    * Deletes workdata with a specific id.
    *
    * @param id     Id of the data
    * @version      4.0
    * @since        3.0
    */
    @RequestMapping(value = "/delete/{id}",  method=RequestMethod.DELETE)
    public void updateWork(@PathVariable long id) {
		database.delete(id);
    }
	
   /**
    * Gets all workdata that a specific user has worked on a specific day.
    *
    * @param userName   User's username
    * @param date       The date
    * @version          4.0
    * @since            3.0
    */
    @RequestMapping(value = "/workForm/{userName}/{date}",  method=RequestMethod.GET)
    public List<WorkData> fetchWorkData(@PathVariable String userName, @PathVariable String date) {
		List<WorkData> list = new ArrayList<>();
        for(WorkData c : database.findAll()) {
            if(c.getStartDate().equals(date)&&userName.equals(c.getUserName())) {
				System.out.println(c.getStartDate());
                list.add(c);
            }
        }
        return list;
    }
	
}
