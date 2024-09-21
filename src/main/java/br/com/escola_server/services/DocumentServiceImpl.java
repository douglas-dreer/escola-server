package br.com.escola_server.services;

import br.com.escola_server.entities.Document;
import br.com.escola_server.models.DocumentDTO;
import br.com.escola_server.repositories.DocumentRepository;
import br.com.escola_server.utilitaries.Converter;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    public List<DocumentDTO> findAll() {
        try {
            return documentRepository
                    .findAll()
                    .stream()
                    .map(item -> Converter.convertTo(item, DocumentDTO.class))
                    .toList();

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public DocumentDTO findById(UUID id) {
        try {
            Optional<Document> documentId = documentRepository.findById(id);

            if (documentId.isEmpty()) {
                throw new NoResultException("Document not found");
            }
            return Converter.convertTo(documentId.get(), DocumentDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public DocumentDTO save(DocumentDTO document) {
        try {
            Document documentSaved = documentRepository.save(Converter.convertTo(document, Document.class));
            return Converter.convertTo(documentSaved, DocumentDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentDTO update(DocumentDTO document) {
        try {
            if (existsById(document.getId())) {
                throw new NoResultException("Document not found");
            }

            Document entity = Converter.convertTo(document, Document.class);
            Document documentEdited = documentRepository.save(entity);
            return Converter.convertTo(documentEdited, DocumentDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }


    @Override
    public void delete(UUID id) {
        try {
            if (existsById(id)) {
                throw new NoResultException("Document not found");
            }
            documentRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return documentRepository.existsById(id);
    }
}
