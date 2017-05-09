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

// This class acts as a controller.
// Usually when using @Controller, you will use also @RequestMapping
@RestController
public class MyController {
    @Autowired
    WorkDataRepository database;
    
	public MyController() {
    }
	
	// curl -H "Content-type: application/json" -X POST -d '{some json}'
	// http://localhost:8080/workForm
    @RequestMapping(value = "/workForm",  method=RequestMethod.POST)
    public void saveWorkData(@RequestBody WorkData c) {
        database.save(c);
    }
	
	@RequestMapping(value = "/workForm",  method=RequestMethod.GET)
    public Iterable<WorkData> fetchWorkData() {
        return database.findAll();
    }
	
	
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
	
	@RequestMapping(value = "/update/{id}",  method=RequestMethod.POST)
    public void updateWork(@PathVariable long id, @RequestBody WorkData data) {
		database.delete(id);
		database.save(data);
    }
	
	@RequestMapping(value = "/delete/{id}",  method=RequestMethod.DELETE)
    public void updateWork(@PathVariable long id) {
		database.delete(id);
    }
	
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
