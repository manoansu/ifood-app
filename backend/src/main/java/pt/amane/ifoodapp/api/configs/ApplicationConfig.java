package pt.amane.ifoodapp.api.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.amane.ifoodapp.domain.services.FotoStorageService;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public FotoStorageService fotoStorageService(){
        return new FotoStorageService() {
            @Override
            public FotoRecuperada recuperar(String nomeArquivo) {
                return null;
            }

            @Override
            public void armazenar(NovaFoto novaFoto) {

            }

            @Override
            public void remover(String nomeArquivo) {

            }
        };
    }
}
