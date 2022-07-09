package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.GrupoController;
import pt.amane.ifoodapp.api.v1.modeldtos.GrupoModelDTO;
import pt.amane.ifoodapp.domain.model.Grupo;

@Component
public class GrupoModelAssembler 
		extends RepresentationModelAssemblerSupport<Grupo, GrupoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModelDTO.class);
	}
	
	@Override
	public GrupoModelDTO toModel(Grupo grupo) {
		GrupoModelDTO grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);
		
		grupoModel.add(apiLinks.linkToGrupos("grupos"));
		
		grupoModel.add(apiLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModelDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
				.add(apiLinks.linkToGrupos());
	}
	
}
