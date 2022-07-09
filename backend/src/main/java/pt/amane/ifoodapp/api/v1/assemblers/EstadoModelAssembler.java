package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.EstadoController;
import pt.amane.ifoodapp.api.v1.models.EstadoModel;
import pt.amane.ifoodapp.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;


    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        /*
        O metodo createModelWithId(estado.getId(), estado) =>
        Creates a new resource with a self link to the given id.
        Params:
            id – must not be null.
            entity – must not be null.
         */
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado,estadoModel);
        // apenas passa o nome de endpoint que deseja aparecer na lista de link
        estadoModel.add(apiLinks.linkToEstados("estados"));
        return estadoModel;
    }

    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> estados){
        return super.toCollectionModel(estados).add(apiLinks.linkToEstados());
    }
}
