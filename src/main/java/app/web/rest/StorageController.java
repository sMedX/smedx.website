package app.web.rest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/storage")
@PropertySource(value = {
        "classpath:resources/app.properties"
})
public class StorageController {

    @Autowired
    private Environment env;

    private static String STORAGE_FOLDER_PATH = "storage";
    private static String NEWS_BASE_FOLDER = "storage" + File.separator + "news";
    private static String MISC_BASE_FOLDER = "storage" + File.separator + "misc";


    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public void getFile(final HttpServletRequest request, final HttpServletResponse response) {
        System.out.println("requested file");
        if (prepareStorage()) {
            // System.out.println("Storage successfully created");
            final PathMatcher pathMatcher = new AntPathMatcher();
            String path = pathMatcher.extractPathWithinPattern(
                    (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE),
                    (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
            );
            try {
                path = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
                File returnFile = new File(STORAGE_FOLDER_PATH + File.separator + path);
                if (returnFile.exists()) {
                    response.setHeader("Content-disposition", "attachment; filename=" + returnFile.getName());
                    try {
                        FileUtils.copyFile(returnFile, response.getOutputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println(ex.getMessage());
                    }
                } else {
                    System.out.println("File " + returnFile.getAbsolutePath() + " requested for path [" + path + "] not found...");
                }
            } catch (UnsupportedEncodingException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            System.out.println("Storage not inited");
        }
        response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
    }

    private boolean prepareStorage() {
        try {
            File baseDir = new File(STORAGE_FOLDER_PATH);
            if (!baseDir.exists()) {
                createFolder(STORAGE_FOLDER_PATH);
            } else {
                System.out.println("Absolute path is:");
                System.out.println(baseDir.getAbsolutePath());
            }

            File newsContainer = new File(NEWS_BASE_FOLDER);
            if (!newsContainer.exists()) {
                createFolder(NEWS_BASE_FOLDER);
            }

            File miscContainer = new File(MISC_BASE_FOLDER);
            if (!miscContainer.exists()) {
                createFolder(MISC_BASE_FOLDER);
            }

        } catch (Exception ex) {
            System.out.println("Can't init storage");
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean createFolder(String path) {
        String fileSystemRoot = env.getProperty("filesystem.root") + File.separator;
        if (fileSystemRoot.equalsIgnoreCase("null/") || fileSystemRoot.equalsIgnoreCase(File.separator)) {
            fileSystemRoot = "";
        }
        try {
            File folder = new File(fileSystemRoot + path);
            folder.mkdir();
            return folder.exists();
        } catch (Exception ex) {
            System.out.println("Can't create folder with path: [" + fileSystemRoot + path + "]");
            ex.printStackTrace();
            throw ex;
        }
    }
}
