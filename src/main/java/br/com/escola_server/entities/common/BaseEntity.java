package br.com.escola_server.entities.common;

import br.com.escola_server.utilitaries.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@AllArgsConstructor
@Getter
@Log4j2
public abstract class BaseEntity<T> {
    private Class<T> modelClass;

    protected BaseEntity() {
        this.modelClass = getModelClass();
    }

    @SuppressWarnings("unchecked")
    private Class<T> getModelClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        while (!(genericSuperclass instanceof ParameterizedType) && genericSuperclass != Object.class) {
            genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
        }

        if (genericSuperclass instanceof ParameterizedType paramType) {
            return (Class<T>) paramType.getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Class is not parametrized with generic type");
        }
    }

    public T toDTO() {
        return Converter.convertTo(this, modelClass);
    }

    public String toJSON() throws JsonProcessingException {
        return Converter.toJSON(this);
    }

    public T toObject(String dados) throws IOException {
        return Converter.toObject(dados, modelClass);
    }
}
