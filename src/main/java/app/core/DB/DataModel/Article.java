package app.core.DB.DataModel;


import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by anton_arakcheyev on 02/12/2018.
 */

@Entity
@Table(name = "article")
public class Article implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "date_added")
    private Timestamp added;

    @Column(name = "link")
    private String link;

    @Column(name = "cover")
    private String coverImage;

    @Column(name = "cover_position")
    private String coverPosition;

    @Column(name = "title")
    private String title;

    @Column(name = "descr")
    private String description;

    @Column(name = "body", length = 65535, columnDefinition = "text")
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getAdded() {
        return added;
    }

    public void setAdded(Timestamp added) {
        this.added = added;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverPosition() {
        return coverPosition;
    }

    public void setCoverPosition(String coverPosition) {
        this.coverPosition = coverPosition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
