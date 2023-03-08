package sg.nus.iss.edu.sg.addressbookrefactorworkshop13;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static sg.nus.iss.edu.sg.addressbookrefactorworkshop13.service.IOService.*;

@SpringBootApplication
public class Addressbookrefactorworkshop13Application {

	private static final Logger logger = LoggerFactory.getLogger(Addressbookrefactorworkshop13Application.class);

	public static void main(String[] args) {
		
		// SpringApplication.run(Addressbookrefactorworkshop13Application.class, args);
		
		SpringApplication app = 
				new SpringApplication(Addressbookrefactorworkshop13Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		System.out.println("before createDir");
		if(null != opsVal){
			logger.info("" + (String)opsVal.get(0));
			System.out.println("inside create Dir");
			createDir((String)opsVal.get(0));
		}else{
			System.out.println("exit");
			System.exit(1);
		}

		app.run(args);
	}

}

