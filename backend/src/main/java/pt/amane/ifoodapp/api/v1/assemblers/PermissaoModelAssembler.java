package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.modeldtos.PermissaoModelDTO;
import pt.amane.ifoodapp.domain.model.Permissao;

@Component
public class PermissaoModelAssembler 
		implements RepresentationModelAssembler<Permissao, PermissaoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;

	@Override
	public PermissaoModelDTO toModel(Permissao permissao) {
		PermissaoModelDTO permissaoModel = modelMapper.map(permissao, PermissaoModelDTO.class);
		return permissaoModel;
	}
	
	@Override
	public CollectionModel<PermissaoModelDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
		return RepresentationModelAssembler.super.toCollectionModel(entities)
				.add(apiLinks.linkToPermissoes());
	}
	
}
