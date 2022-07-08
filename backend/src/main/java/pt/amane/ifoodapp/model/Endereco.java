package pt.amane.ifoodapp.model;

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
@Embeddable
public class Endereco implements Serializable {

    /*@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    private String cep;
    private String lougradouro;
    private String numero;
    private String complemento;
    private String bairro;

    private Estado cidade;
}
