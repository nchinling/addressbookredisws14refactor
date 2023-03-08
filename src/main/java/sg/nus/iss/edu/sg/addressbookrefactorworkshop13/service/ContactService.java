package sg.nus.iss.edu.sg.addressbookrefactorworkshop13.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import sg.nus.iss.edu.sg.addressbookrefactorworkshop13.model.Contact;

@Component("contacts")
public class ContactService {
    

    public void saveContact(Contact ctc, Model model, ApplicationArguments appArgs,
    String defaultDataDir){
    String datafilename = ctc.getId();
    PrintWriter printWriter = null;

    try {
        FileWriter fileWriter =new FileWriter(getDataDir(appArgs, defaultDataDir)
            + "/" + datafilename);
        printWriter = new PrintWriter(fileWriter);
        printWriter.println(ctc.getName());
        printWriter.println(ctc.getEmail());
        printWriter.println(ctc.getPhoneNumber());
        printWriter.println(ctc.getDateOfBirth());
        printWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    model.addAttribute("contact", new Contact(ctc.getId(), 
            ctc.getName(), ctc.getEmail(), ctc.getPhoneNumber(), ctc.getDateOfBirth()));
    }

    
    public void getContactById(Model model, String contactId, ApplicationArguments appArgs,
        String defaultDataDir){
        //An empty Contact object is instantiated to set retrieved file's contact info. 
        Contact ctc = new Contact();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            Path filePath=  new File(getDataDir(appArgs, defaultDataDir)
                                                + "/" + contactId).toPath(); 
            Charset charset= Charset.forName("UTF-8");
            List<String> stringValues = Files.readAllLines(filePath, charset);
            ctc.setId(contactId);
            ctc.setName(stringValues.get(0));
            ctc.setEmail(stringValues.get(1));
            ctc.setPhoneNumber(stringValues.get(2));
            LocalDate dob = LocalDate.parse(stringValues.get(3), formatter);
            ctc.setDateOfBirth(dob);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("contact", ctc);
    }

    public void getAllContacts(Model m, ApplicationArguments appArgs, String defaultDataDir){
        Set<String> dataFiles = listDirFiles(getDataDir(appArgs, defaultDataDir));
        m.addAttribute("contacts", dataFiles);
    }

    // private Set<String> listFiles(String directory){
    //     return Stream.of(new File(directory).listFiles())
    //         .filter(file -> ! file.isDirectory())
    //         .map(File::getName)
    //         .collect(Collectors.toSet());
    // }

    private Set<String> listDirFiles(String directory) {
        Set<String> fileNames = new HashSet<>();
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }
    

    private String getDataDir(ApplicationArguments appArgs, String defaultDataDir){
        String dataDirResult = "";
        List<String> optValues = null;
        String[] optValuesArr = null;
        Set<String> opsNames = appArgs.getOptionNames();
        String[] opsNamesArr = opsNames.toArray(new String[opsNames.size()]);
        if(opsNamesArr.length > 0){
            optValues = appArgs.getOptionValues(opsNamesArr[0]);
            optValuesArr = optValues.toArray(new String[optValues.size()]);
            dataDirResult = optValuesArr[0];
        }else{
            dataDirResult = defaultDataDir;
        }

        return dataDirResult;
    }
}
