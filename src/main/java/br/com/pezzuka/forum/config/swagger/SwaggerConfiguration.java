package br.com.pezzuka.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.pezzuka.forum.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.pezzuka.forum"))//Pacote base
				.paths(PathSelectors.ant("/**"))//Endereços que será analisado
				.build()
				.ignoredParameterTypes(Usuario.class)//Ignora os end-points que retorna a classe Usuário, pois não queremos que aparece a senha etc...
				
				//Configurando parametro global, que eu quero que apareca em todos os end-points do swagger
				//Nesse caso adc um 'Authorization'
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder()
						.name("Authorization")
						.description("Header para token JWT")//Descrição
						.modelRef(new ModelRef("string"))//Tipo do parametro
						.parameterType("header")//Tipo de parametro, vai no Header, não no corpo por exemplo
						.required(false)//Não é obrigatório
						.build()));
	}
	
	
}
