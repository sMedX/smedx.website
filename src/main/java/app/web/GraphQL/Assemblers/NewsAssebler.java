package app.web.GraphQL.Assemblers;

import app.core.DB.DataModel.News;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NewsAssebler {

    public static News fromStringToNews(String newsString) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            System.out.println("News Assembler:");
            System.out.println(newsString);
            System.out.println("************************************************************\r\n");
            News news = objectMapper.readValue(newsString, News.class);
            System.out.println(news.getBody());
            System.out.println("************************************************************\r\n");
            return news;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (JsonParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
