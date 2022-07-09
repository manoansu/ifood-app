package pt.amane.ifoodapp.api.v1.modeldtos.inputdtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import pt.amane.ifoodapp.core.validations.FileContentType;
import pt.amane.ifoodapp.core.validations.FileSize;

@Getter
@Setter
public class FotoProdutoInput {

	@ApiModelProperty(hidden = true)
	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;
	
	@ApiModelProperty(value = "Descrição da foto do produto", required = true)
	@NotBlank
	private String descricao;
	
}
