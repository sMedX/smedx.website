package app.core.DB.Repositories;

import app.core.DB.DataModel.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("select count(id) from app.core.DB.DataModel.News")
    Integer countArticles();

    News findByLink(String link);

    @Query("select news from app.core.DB.DataModel.News as news order by news.added desc")
    List<News> getArticles();

    // List<News> findNewsOrderByAddedAsc();
}
