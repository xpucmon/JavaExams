package jwbexam.domain.models.binding;

import javax.validation.constraints.NotNull;

public class DocumentScheduleBindingModel {
    private String title;
    private String content;

    @NotNull
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
