package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
;
import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.CozinhaController;
import pt.amane.ifoodapp.api.v1.modeldtos.CozinhaModelDTO;
import pt.amane.ifoodapp.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler 
		extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModelDTO.class);
	}
	
	@Override
	public CozinhaModelDTO toModel(Cozinha cozinha) {
		CozinhaModelDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		
		cozinhaModel.add(apiLinks.linkToCozinhas("cozinhas"));
		
		return cozinhaModel;
	}
	
}
