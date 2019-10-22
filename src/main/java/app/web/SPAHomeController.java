package app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by anton_arakcheyev on 13/11/2018.
 */
@Controller
@RequestMapping(value="**", method = RequestMethod.GET)
public class SPAHomeController {

    @RequestMapping("*")
    public String index(){
        //System.out.println("entering root path\r\n");
        return "static/ng/index.html";
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exception(final HttpServletRequest request, final Exception exception){
        try(final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw)){
            System.out.println("!!! exception !!!");
            exception.printStackTrace(pw);
        } catch (final IOException e){
                e.printStackTrace();
        }
        return exception.getMessage();
    }
}
