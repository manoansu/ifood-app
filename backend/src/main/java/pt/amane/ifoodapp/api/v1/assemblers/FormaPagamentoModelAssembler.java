package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.FormaPagamentoController;
import pt.amane.ifoodapp.api.v1.modeldtos.FormaPagamentoModelDTO;
import pt.amane.ifoodapp.domain.model.FormaPagamento;


@Component
public class FormaPagamentoModelAssembler 
		extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModelDTO.class);
	}
	
	@Override
	public FormaPagamentoModelDTO toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModelDTO formaPagamentoModel =
				createModelWithId(formaPagamento.getId(), formaPagamento);
		
		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		formaPagamentoModel.add(apiLinks.linkToFormasPagamento("formasPagamento"));
		
		return formaPagamentoModel;
	}
	
	@Override
	public CollectionModel<FormaPagamentoModelDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities)
			.add(apiLinks.linkToFormasPagamento());
	}
	
}
