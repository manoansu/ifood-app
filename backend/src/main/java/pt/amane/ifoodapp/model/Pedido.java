package pt.amane.ifoodapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pt.amane.ifoodapp.model.enums.StatusPedido;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Pedido implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private Instant dataCriacao;
    private Instant dataConfirmacao;
    private Instant cataCancelamento;
    private Instant dataEntrega;
    private FormaPagamento formaPagamento;
    private Restaurante restaurante;
    private Usuario cliente;

    @Embedded
    private Endereco enderecoEntrega;
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "itemPedido_id")
    private ItemPedido itemPedido;

}
