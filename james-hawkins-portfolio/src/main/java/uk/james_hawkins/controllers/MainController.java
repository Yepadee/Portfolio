package uk.james_hawkins.controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:otherprops.properties")
@Controller
public class MainController {

	
    @Value("${oauth}")
    private String oauth;
    
	Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@GetMapping("/")
	private String getMainPage(Model model) {
		RepositoryService service = new RepositoryService();
		try {
			List<Repository> repos = service.getRepositories("yepadee");
			repos.sort(new Comparator<Repository>() {
				public int compare(Repository r1, Repository r2) {
					return r2.getCreatedAt().compareTo(r1.getCreatedAt());
				}
			});
			model.addAttribute("repos", repos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("User visited main page.");
		return "index";
	}
}