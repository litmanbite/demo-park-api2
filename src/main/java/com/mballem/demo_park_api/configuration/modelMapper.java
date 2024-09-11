package com.mballem.demo_park_api.configuration;
import com.mballem.demo_park_api.entity.User;
import com.mballem.demo_park_api.web.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class modelMapper {

    @Bean
    public ModelMapper mM() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuração adicional para o mapeamento do User para UserResponseDTO
        modelMapper.addMappings(new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getUsername()); // Supondo que o nome no DTO deve ser o username
            }
        });

        return modelMapper;
    }

}
