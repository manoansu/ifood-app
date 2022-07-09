package pt.amane.ifoodapp.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VendaDiariaDTO {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
	
}
