package br.com.escola_server.utilitaries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Converter {
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final ObjectMapper mapper = new ObjectMapper();

    public Converter() {
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static <D> D convertTo(Object bean, Class<D> dto) {
        return modelMapper.map(bean, dto);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }

    public static String toJSON(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <D> D toObject(String jsonData, Class<D> targetClass) throws IOException {
        return mapper.readValue(jsonData, targetClass);
    }
}
