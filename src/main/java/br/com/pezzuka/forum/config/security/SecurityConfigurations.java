package br.com.pezzuka.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // -> Habilitando o Web Security
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	AutenticacaoService autenticacaoService;//Implementa um 'UserDetailsService'
	
	
	//Aviso o Spring, pois usaremos a injeção de dependencia desse cara, que precisa ser sobrescrito pois o Spring não faz a injeção de dependencia dele
	@Override
	@Bean 
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	//Configurações de Autenticação (controle de acesso, login etc...)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//passwordEncoder(new BCryptPasswordEncoder() -> Adiciona a forma de encriptação da senha
		//userDetailsService(xxx) -> Indico qual meu UserDetails, configs, login etc....
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	//Configurações de Autorização (URL, quem pode acessar a URL, perfil de acesso etc...)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()//URL de Login
			.anyRequest().authenticated()//Para as outras URL ele deverá estar autenticado
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Setamos a sessão como STATELESS ao invés do default que é sessão
			
	}
	
	
	//Configurações de Recursos Estáticos (Acesso a CSS, JavaScript, Imagens etc...)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	
}
