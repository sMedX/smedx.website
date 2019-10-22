package app.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@ControllerAdvice
public class NoHandlerFoundExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handlerNoHandlerFoundException(final HttpServletRequest request) {
        try {
            if (request.getPathInfo().startsWith("/api/") || request.getPathInfo().equals("favicon.ico")) {
                return ResponseEntity.notFound().build();
            } else {
                File indexFile;
                /*File f = new File("../");
                for (File file : f.listFiles()) {
                    System.out.println(file.getAbsolutePath());
                }*/
                indexFile = new File("/opt/tomcat/apache-tomcat-8.5.35/webapps/ROOT/static/ng/index.html");
                System.out.println(indexFile.getAbsolutePath());
                if (!indexFile.exists()) {
                    System.out.println("index.html not found search local...");
                    indexFile = new File("src/main/webapp/static/ng/index.html");
                }
                final FileInputStream inputStream = new FileInputStream(indexFile);
                final String body = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
                System.out.println("redirected");
                return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(body);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            // return  new ResponseEntity("redirect:/", HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
/*


# Load mod_jk module
# Update this path to match your modules location
LoadModule jk_module ../mods-available/mod_jk.so

# Where to find workers.properties
# Update this path to match your conf directory location
JkWorkersFile /opt/tomcat/mod_jk/conf/workers.properties

# Where to put jk logs
# Update this path to match your logs directory location
JkLogFile /opt/tomcat/mod_js/logs/mod_jk.log

# Set the jk log level [debug/error/info]
JkLogLevel info

# Select the log format
JkLogStampFormat "[%a %b %d %H:%M:%S %Y]"

# JkOptions indicate to send SSL KEY SIZE,
JkOptions +ForwardKeySize +ForwardURICompat -ForwardDirectories

# JkRequestLogFormat set the request format
JkRequestLogFormat "%w %V %T"

# Send everything for context /smedx to worker ajp13
JkMount /smedx ajp13
JkMount /smedx*/
/* ajp13*/
