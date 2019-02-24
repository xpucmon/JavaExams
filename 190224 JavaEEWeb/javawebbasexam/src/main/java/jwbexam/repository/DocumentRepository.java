package jwbexam.repository;

import jwbexam.domain.entities.Document;

public interface DocumentRepository extends GenericRepository<Document, String> {

    void print(String id);
}
