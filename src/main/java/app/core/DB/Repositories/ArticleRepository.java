package app.core.DB.Repositories;

import app.core.DB.DataModel.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByLink(String link);
}
