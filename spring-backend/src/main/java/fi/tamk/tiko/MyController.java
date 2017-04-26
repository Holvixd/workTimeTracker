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
	
	@RequestMapping(value = "/workForm/{date}",  method=RequestMethod.GET)
    public WorkData fetchWorkData(@PathVariable String date) {
		System.out.println(date);
        for(WorkData c : database.findAll()) {
			System.out.println(c.getStartDate());
            if(c.getStartDate().equals(date)) {
				
                return c;
            }
        }
        return null;
    }
	
}
