package pt.amane.ifoodapp.domain.services;

import java.util.List;


import pt.amane.ifoodapp.domain.filters.VendaDiariaFilter;
import pt.amane.ifoodapp.domain.model.dto.VendaDiariaDTO;

public interface VendaQueryService {

	List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}
