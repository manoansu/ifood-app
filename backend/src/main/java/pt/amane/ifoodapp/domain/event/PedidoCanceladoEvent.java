package pt.amane.ifoodapp.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.amane.ifoodapp.domain.model.Pedido;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
	
}
