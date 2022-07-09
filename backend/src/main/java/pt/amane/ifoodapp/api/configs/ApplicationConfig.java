package pt.amane.ifoodapp.api.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.amane.ifoodapp.domain.filters.VendaDiariaFilter;
import pt.amane.ifoodapp.domain.model.dto.VendaDiariaDTO;
import pt.amane.ifoodapp.domain.services.FotoStorageService;
import pt.amane.ifoodapp.domain.services.VendaQueryService;
import pt.amane.ifoodapp.domain.services.VendaReportService;

import java.util.List;

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

    @Bean
    public VendaQueryService vendaQueryService(){
        return new VendaQueryService() {
            @Override
            public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
                return null;
            }
        };
    }

    @Bean
    public VendaReportService vendaReportService(){
        return new VendaReportService() {
            @Override
            public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
                return new byte[0];
            }
        };
    }
}
