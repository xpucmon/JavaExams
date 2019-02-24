package jwbexam.web.mbeans;

import jwbexam.domain.models.view.DocumentViewModel;
import jwbexam.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

@Named
@RequestScoped
public class DocumentDetailsBean {
    private DocumentService documentService;
    private ModelMapper modelMapper;
    private DocumentViewModel documentViewModel;

    public DocumentDetailsBean(){
        this.documentViewModel = new DocumentViewModel();
    }

    @Inject
    public DocumentDetailsBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        this.documentService = documentService;
        this.modelMapper = modelMapper;
        this.initModel();
    }

    private void initModel() {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        String id = requestParameterMap.get("id");

        if (this.documentService.findDocumentById(id) == null){
            throw new IllegalArgumentException("No such document...");
        }

        this.documentViewModel = this.modelMapper
                .map(this.documentService.findDocumentById(id), DocumentViewModel.class);
    }

    public DocumentViewModel getDocumentViewModel() {
        return documentViewModel;
    }

    public void setDocumentViewModel(DocumentViewModel documentViewModel) {
        this.documentViewModel = documentViewModel;
    }
}
