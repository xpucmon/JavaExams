package jwbexam.service;

import jwbexam.domain.entities.Document;
import jwbexam.domain.entities.User;
import jwbexam.domain.models.service.DocumentServiceModel;
import jwbexam.domain.models.service.UserServiceModel;
import jwbexam.repository.DocumentRepository;
import jwbexam.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    @Inject
    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void scheduleDocument(DocumentServiceModel documentServiceModel) {
        this.documentRepository.save(this.modelMapper.map(documentServiceModel, Document.class));
    }

    @Override
    public DocumentServiceModel findDocumentById(String id) {
        Document document = this.documentRepository.findById(id);

        if (document == null){
            return null;
        }

        return this.modelMapper.map(document, DocumentServiceModel.class);    }

    @Override
    public List<DocumentServiceModel> getAllDocuments() {
        return Arrays.asList(this.modelMapper
                .map(this.documentRepository.findAll(), DocumentServiceModel[].class));    }

    @Override
    public void printDocument(String id) {
        this.documentRepository.print(id);
    }
}
