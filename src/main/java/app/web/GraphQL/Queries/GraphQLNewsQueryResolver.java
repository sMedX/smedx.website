package app.web.GraphQL.Queries;

import app.core.DB.DataModel.News;
import app.core.DB.DataModel.User;
import app.core.DB.Services.NewsService;
import app.core.DB.Services.UserService;
import app.core.Utils.MigrationUtil;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by anton_arakcheyev on 11/12/2018.
 */
@Component("gqlNewsQueryResolver")
public class GraphQLNewsQueryResolver implements GraphQLQueryResolver {
    protected static Logger logger = Logger.getLogger(GraphQLNewsQueryResolver.class.getName());

    @Resource(name = "newsService")
    private NewsService newsService;

    @Autowired
    private UserService userService;


    public List<News> getNews(int skip, int first) {
        logger.info("GQL requested news");
        return getOrCreateAllNews(skip, first);
    }

    private List<News> getOrCreateAllNews(Number skip, Number first) {
        List<News> news = newsService.getNews(skip.intValue(), first.intValue());
        if (news == null || news.size() == 0) {
            if (newsService.getAllNews() == null || newsService.getAllNews().size() == 0) {
                MigrationUtil.getInstance().migrateNews();
                news = newsService.getNews(skip.intValue(), first.intValue());
            }
        }
        return news;
    }

    public News newsByLink(String newsLink) {
        return newsService.newsByLink(newsLink);
    }

    public News getNewsForEditor(Long newsId, String hash) {
        System.out.println("newsId: " + newsId + " hash: " + hash);
        User user = userService.findUserByHash(hash);
        if (user != null) {
            return newsService.getNews(newsId);
        }
        return null;
    }
}
