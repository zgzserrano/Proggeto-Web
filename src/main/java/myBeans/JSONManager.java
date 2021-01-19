package myBeans;

import javax.json.stream.JsonGenerationException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.*;

import java.io.IOException;
import java.util.List;

public class JSONManager {

    ObjectMapper objectMapper = new ObjectMapper();

    // Traduzione da stringa JSON a oggetto Java di tipo generico T
    public <T> T parseJson(String json, Class<T> targetType) throws Exception {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.readValue(json, targetType);
    }
    public <T> String serializeJson(T o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String stringaJSON = "";
        // ora genero stringa JSON
        try {
            stringaJSON = mapper.writeValueAsString(o);
        } catch (JsonGenerationException e) {
            System.out.println(e.getMessage());
        } catch (JsonMappingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return stringaJSON;
    }
}
