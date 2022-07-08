package pt.amane.ifoodapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FotoProduto implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "produto_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

    public Long getRestauranteId() {
        if (getProduto() != null) {
            return getProduto().getRestaurante().getId();
        }

        return null;
    }

}
