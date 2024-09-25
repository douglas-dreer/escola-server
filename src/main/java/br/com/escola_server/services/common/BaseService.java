package br.com.escola_server.services.common;

import br.com.escola_server.entities.common.BaseEntity;
import br.com.escola_server.models.common.BaseModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface BaseService<T extends BaseEntity, M extends BaseModel> {
    List<M> list();

    M findById(UUID id);

    M save(M model);

    M edit(M model) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;
}
