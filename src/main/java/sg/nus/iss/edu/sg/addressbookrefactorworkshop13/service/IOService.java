package sg.nus.iss.edu.sg.addressbookrefactorworkshop13.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOService {
    private static final Logger logger = LoggerFactory.getLogger(IOService.class);

    /** 
     * Helper method to create directory on the local machine
     */
    public static void createDir(String path){
        File dir = new File(path);

        //mkdir returns false if directory already exists.
        boolean isCreated = dir.mkdir();
        logger.info("Is Dir exist ?" + isCreated);
        if(isCreated) {
            String osName = System.getProperty("os.name");
            if(!osName.contains("Windows")){
                String permission = "rwxrwx---";
                Set<PosixFilePermission>  permissions = 
                                PosixFilePermissions.fromString(permission);
                try {
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
        }
    }
}
