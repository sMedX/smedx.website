package app.web.GraphQL.Mutations;

import app.core.DB.DataModel.News;
import app.core.DB.DataModel.User;
import app.core.DB.Services.NewsService;
import app.core.DB.Services.UserService;
import app.web.GraphQL.Assemblers.NewsAssebler;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("gqlNewsMutationResolver")
public class GraphQLNewsMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    public Boolean updateNews(String news, String hash) {
        User user = userService.findUserByHash(hash);
        if (user != null) {
            News convertedNews = NewsAssebler.fromStringToNews(news);
            convertedNews = newsService.addNews(convertedNews);
            return convertedNews != null && convertedNews.getId() != null;
        }

        return null;
    }
}
