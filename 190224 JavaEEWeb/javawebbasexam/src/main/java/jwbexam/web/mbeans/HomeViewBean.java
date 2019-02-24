package jwbexam.web.mbeans;

import jwbexam.domain.models.view.DocumentViewModel;
import jwbexam.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class HomeViewBean {

    private DocumentService documentService;
    private ModelMapper modelMapper;
    private DocumentViewModel documentViewModel;
    private List<DocumentViewModel> documents;

    public HomeViewBean() {
        this.documentViewModel = new DocumentViewModel();
        this.documents = new ArrayList<>();
    }

    @Inject
    public HomeViewBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        this.documentService = documentService;
        this.modelMapper = modelMapper;
        this.initDocumentsList();
    }

    public DocumentViewModel getDocumentViewModel() {
        return documentViewModel;
    }

    public void setDocumentViewModel(DocumentViewModel documentViewModel) {
        this.documentViewModel = documentViewModel;
    }

    public List<DocumentViewModel> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentViewModel> documents) {
        this.documents = documents;
    }

    private void initDocumentsList() {
        this.documents = Arrays.asList(this.modelMapper
                .map(this.documentService.getAllDocuments(), DocumentViewModel[].class));
    }
}
