package app.core.DB.Services;

import app.core.DB.DataModel.Article;
import app.core.DB.Repositories.ArticleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by anton_arakcheyev on 03/12/2018.
 */
@Service("articleService")
@Transactional
public class ArticleService {
    protected static Logger logger = Logger.getLogger(ArticleService.class.getName());

    // @Autowired
    // private SessionFactory sessionFactory;

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getArticles() {
        logger.info("Retrieving all articles");
        return articleRepository.findAll();
    }

    public Article getArticle(Long id) {
        logger.info("Retrieving article id=[" + id.toString() + "]");
        return articleRepository.getOne(id);
    }

    public Article getArticleByLink(String articleLink) {
        logger.info("Retrieving article by link=[" + articleLink + "]");
        return articleRepository.findByLink(articleLink);
    }

    public void addArticle(Article article) {
        logger.info("Adding new article title=[" + article.getTitle() + "]");
        Article newArticle = articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        logger.info("Deleting article id=[" + id.toString() + "]");
        articleRepository.deleteById(id);
    }

    public Article updateArticle(Article article) {
        logger.info("Editing article id=[" + article.getId().toString() + "]");
        return articleRepository.save(article);

    }

    /*public boolean checkExistance(String tableName) {
        logger.info("Checking existence of table Article");
        Session session = sessionFactory.getCurrentSession();
        Object result = session.createNativeQuery("SHOW TABLES").list();
        if (result != null && ((List) result).contains(tableName)) {
            return true;
        } else {
            return false;
        }
    }*/
}
