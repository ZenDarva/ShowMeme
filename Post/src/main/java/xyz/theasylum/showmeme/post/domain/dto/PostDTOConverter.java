package xyz.theasylum.showmeme.post.domain.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostDTOConverter implements Converter<String, NewPostDTO> {
    @Override
    public NewPostDTO convert(String source) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(source, NewPostDTO.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
