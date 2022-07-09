package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.CidadeController;
import pt.amane.ifoodapp.api.v1.modeldtos.CidadeModelDTO;
import pt.amane.ifoodapp.domain.model.Cidade;

@Component
public class CidadeModelAssembler 
		extends RepresentationModelAssemblerSupport<Cidade, CidadeModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModelDTO.class);
	}
	
	@Override
	public CidadeModelDTO toModel(Cidade cidade) {
		CidadeModelDTO cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(apiLinks.linkToCidades("cidades"));
		
		cidadeModel.getEstado().add(apiLinks.linkToEstado(cidadeModel.getEstado().getId()));
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModelDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(apiLinks.linkToCidades());
	}
	
}
