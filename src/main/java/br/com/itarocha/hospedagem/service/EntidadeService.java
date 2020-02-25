package br.com.itarocha.hospedagem.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.Entidade;
import br.com.itarocha.hospedagem.repository.EnderecoRepository;
import br.com.itarocha.hospedagem.repository.EntidadeRepository;
import br.com.itarocha.hospedagem.validation.ResultError;
import br.com.itarocha.hospedagem.exception.ValidationException;

@ApplicationScoped
public class EntidadeService {

	@Inject EntityManager em;

	@Inject EntidadeRepository repositorio;

	@Inject EnderecoRepository enderecoRepo;

	public EntidadeService() {
	}

	public Entidade create(Entidade model) throws ValidationException {
		try{
			
			Long id = model.getId() == null ? 0L : model.getId();
			
			if (this.entidadeCadastradaPorCampo(id, "cnpj", model.getCnpj())) {
				throw new ValidationException(new ResultError().addError("cnpj", "CNPJ já casdastrado para outra Entidade"));
			}
			
			enderecoRepo.save(model.getEndereco());
			repositorio.save(model);
		} catch (ValidationException e) {
			throw e;
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
		return model;
	}

	public void remove(Long id) {
		Optional<Entidade> model = find(id);
		if (model.isPresent()) {
			repositorio.delete(model.get());
		}
	}

	public Entidade update(Entidade model) {
		Optional<Entidade> obj = find(model.getId());
		if (obj.isPresent()) {
			return repositorio.save(model);
		}
		return model;
	}

	public Optional<Entidade> find(Long id) {
		return repositorio.findById(id);
	}

	public List<Entidade> findAll() {
		return repositorio.getAll("nome");
	}

	public List<Entidade> consultar(String texto) {
		return repositorio.consultar(texto);
	}

	public List<SelectValueVO> listSelect() {
		return repositorio.listSelect();
	}
	
	public boolean entidadeCadastradaPorCampo(Long entidadeId, String campo, String valor) {
		return repositorio.entidadeCadastradaPorCampo(entidadeId, campo, valor);
	}
}