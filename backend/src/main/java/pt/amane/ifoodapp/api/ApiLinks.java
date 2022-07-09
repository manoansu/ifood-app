package pt.amane.ifoodapp.api;

import static org.springframework.hateoas.TemplateVariable.VariableType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;
import pt.amane.ifoodapp.api.v1.controllers.EstadoController;

@Component
public class ApiLinks {

    public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

    public Link linkToEstado(Long estadoId, String rel){
        /*
        Codigo abaixo informa para o spring hateoas, que quero construir um link para o
        metodo findById da classe controller do estado e passando o nome de endpoint no
         withRel(rel) onde o string rel é o nome de endpoit do link por ex; estados
         o rel é o significado de link relation..
         esse parte de codigo linkTo(methodOn(EstadoController.class) cria um dummy ou seja nao faz nada apenas chama o metodo.
         */
        return linkTo(methodOn(EstadoController.class).findById(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId){
        /*
        Apenas chama o metodo linkToEstado(id, IanaLinkRelations.SELF.value())
        onde o IanaLinkRelations.SELF.value() Captura a relaçao de link padrao da base de IANA(Capture standard IANA-based link relations)
         */
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel){
        /*
        O metodo apenas linka o endpoit passado no metodo..
         */
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }
}
