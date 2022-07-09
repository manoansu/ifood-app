package pt.amane.ifoodapp.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.ApiLinks;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private ApiLinks apiLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(apiLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(apiLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(apiLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(apiLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(apiLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(apiLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(apiLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(apiLinks.linkToEstados("estados"));
		rootEntryPointModel.add(apiLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(apiLinks.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}
