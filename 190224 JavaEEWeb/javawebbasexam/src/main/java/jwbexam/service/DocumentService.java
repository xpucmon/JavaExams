package jwbexam.service;

import jwbexam.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    void scheduleDocument(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findDocumentById(String id);

    List<DocumentServiceModel> getAllDocuments();

    void printDocument(String id);
}
