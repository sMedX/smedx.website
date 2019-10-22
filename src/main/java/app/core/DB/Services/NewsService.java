package app.core.DB.Services;

import app.core.DB.DataModel.News;
import app.core.DB.Repositories.NewsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anton_arakcheyev on 11/12/2018.
 */
@Service("newsService")
@Transactional
public class NewsService {
    protected static Logger logger = Logger.getLogger(NewsService.class.getName());

    /*@Autowired
    private SessionFactory sessionFactory;*/

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        logger.info("Get all news");
        List<News> news = newsRepository.findAll();
        news.sort(new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                if (o1.getAdded().before(o2.getAdded())) {
                    return 1;
                } else if (o1.getAdded().after(o2.getAdded())) {
                    return -1;
                } else {
                    return 0;
                }
                //return o1.getAdded().compareTo(o2.getAdded());
            }
        });

        return news;
    }

    public List<News> getNews(int skip, int first) {
        // System.out.println("Get " + first + " news from " + skip);
        logger.info("Get " + first + " news from " + skip);
        int count = newsRepository.countArticles();
        List<News> res = new ArrayList<>();
        if (count < skip) {
            // System.out.println("Count: " + count);
            res = new ArrayList<>();
        } else if (count < skip + first) {
            // System.out.println("Count " + count + " <  skip " + skip + " + first" + first);
            List subres = newsRepository.getArticles().stream().skip(skip).limit(count - skip).collect(Collectors.toList());
            for (Object subre : subres) {
                res.add((News) subre);
            }
        } else {
            // System.out.println("Count " + count + " >  skip " + skip + " + first" + first);
            List<Object> subres = newsRepository.getArticles().stream().skip(skip).limit(first).collect(Collectors.toList());
            for (Object subre : subres) {
                res.add((News) subre);
            }

        }

        return res;


    }

    public News getNews(Long newsId) {
        System.out.println("Get news id=[" + newsId.toString() + "]");
        logger.info("Get news id=[" + newsId.toString() + "]");
        /*if (checkExistance("news")) {
            Session session = sessionFactory.getCurrentSession();
            News news = session.get(News.class, newsId);
            return news;
        } else {
            return null;
        }*/
        return newsRepository.findById(newsId).get();
    }

    public News newsByLink(String newsLink) {
        logger.info("Get news by link=[" + newsLink + "]");
        /*if (checkExistance("news")) {
            Session session = sessionFactory.getCurrentSession();
            return (News) session.createQuery("FROM News n WHERE n.link = :newsLink")
                    .setParameter("newsLink", newsLink).uniqueResult();
        } else {
            return null;
        }*/

        return newsRepository.findByLink(newsLink);
    }



    public News addNews(News news) {
        logger.info("Adding new news title=[" + news.getTitle() + "]");
        //Session session = sessionFactory.getCurrentSession();
        //session.save(news);
        News newNews = newsRepository.save(news);
        return newNews;
    }

}
