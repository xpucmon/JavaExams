package jwbexam.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "documents")
public class Document extends BaseEntity {
    private String title;
    private String content;

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false, length = 4000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
