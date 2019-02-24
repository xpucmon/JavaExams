package jwbexam.web.mbeans;

import jwbexam.domain.models.binding.DocumentScheduleBindingModel;
import jwbexam.domain.models.binding.UserRegisterBindingModel;
import jwbexam.domain.models.service.DocumentServiceModel;
import jwbexam.domain.models.service.UserServiceModel;
import jwbexam.service.DocumentService;
import jwbexam.service.UserService;
import jwbexam.util.ValidationUtil;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@RequestScoped
public class DocumentScheduleBean {

    private DocumentScheduleBindingModel documentScheduleBindingModel;
    private DocumentService documentService;
    private ModelMapper modelMapper;

    public DocumentScheduleBean() {
        this.documentScheduleBindingModel = new DocumentScheduleBindingModel();
    }

    @Inject
    public DocumentScheduleBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    public DocumentScheduleBindingModel getDocumentScheduleBindingModel() {
        return documentScheduleBindingModel;
    }

    public void setDocumentScheduleBindingModel(DocumentScheduleBindingModel documentScheduleBindingModel) {
        this.documentScheduleBindingModel = documentScheduleBindingModel;
    }

    public void schedule() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        DocumentServiceModel map = this.modelMapper.map(this.documentScheduleBindingModel, DocumentServiceModel.class);

        this.documentService.scheduleDocument(map);

        DocumentServiceModel model = this.documentService
                .getAllDocuments().stream().filter(d -> d.getTitle().equals(map.getTitle())).collect(Collectors.toList()).get(0);

        context.redirect("/faces/jsf/details.xhtml?id=" + model.getId());
    }
}
