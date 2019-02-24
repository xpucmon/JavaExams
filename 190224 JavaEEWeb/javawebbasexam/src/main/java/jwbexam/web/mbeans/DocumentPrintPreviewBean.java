package jwbexam.web.mbeans;

import jwbexam.domain.models.service.DocumentServiceModel;
import jwbexam.service.DocumentService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class DocumentPrintPreviewBean {
    private DocumentService documentService;

    public DocumentPrintPreviewBean() {
    }

    @Inject
    public DocumentPrintPreviewBean(DocumentService documentService) {
        this.documentService = documentService;
    }

    public DocumentServiceModel getJobApplicationById(String id){
        return this.documentService.findDocumentById(id);
    }

    public void print(String id) throws IOException {
        this.documentService.printDocument(id);

        FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/jsf/home.xhtml");
    }
}
