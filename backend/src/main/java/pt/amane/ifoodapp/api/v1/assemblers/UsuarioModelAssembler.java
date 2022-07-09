package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.UsuarioController;
import pt.amane.ifoodapp.api.v1.modeldtos.UsuarioModelDTO;
import pt.amane.ifoodapp.domain.model.Usuario;

@Component
public class UsuarioModelAssembler 
		extends RepresentationModelAssemblerSupport<Usuario, UsuarioModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModelDTO.class);
	}
	
	@Override
	public UsuarioModelDTO toModel(Usuario usuario) {
		UsuarioModelDTO usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);
		
		usuarioModel.add(apiLinks.linkToUsuarios("usuarios"));
		
		usuarioModel.add(apiLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
		
		return usuarioModel;
	}
	
	@Override
	public CollectionModel<UsuarioModelDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
			.add(apiLinks.linkToUsuarios());
	}
	
}
