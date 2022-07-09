package pt.amane.ifoodapp.domain.services;

import pt.amane.ifoodapp.domain.filters.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}
