package br.com.itarocha.hospedagem.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.itarocha.hospedagem.validation.ResultError;

import br.com.itarocha.hospedagem.exception.ValidationException;
import br.com.itarocha.hospedagem.model.Pessoa;
import br.com.itarocha.hospedagem.repository.EnderecoRepository;
import br.com.itarocha.hospedagem.repository.PessoaRepository;

@ApplicationScoped
public class PessoaService {

	@Inject PessoaRepository repositorio;

	@Inject EnderecoRepository enderecoRepo;

	public PessoaService() {
	}

	public Pessoa create(Pessoa model) throws ValidationException {
		try{
			enderecoRepo.save(model.getEndereco());
			
			Long id = model.getId() == null ? 0L : model.getId();
			
			if (repositorio.pessoaCadastradaPorCampo(id, "cpf", model.getCpf())) {
				throw new ValidationException(new ResultError().addError("cpf", "CPF já casdastrado para outra pessoa"));
			}
			
			if (repositorio.pessoaCadastradaPorCampo(id, "rg", model.getRg())) {
				throw new ValidationException(new ResultError().addError("rg", "RG já casdastrado para outra pessoa"));
			}
			
			if (repositorio.pessoaCadastradaPorCampo(id, "cartao_sus", model.getCartaoSus())) {
				throw new ValidationException(new ResultError().addError("cartaoSus", "Cartão do SUS já casdastrado para outra pessoa"));
			}
			
			repositorio.save(model);
		} catch (ValidationException e) {
			throw e;
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
		return model;
	}

	public void remove(Long id) {
		Optional<Pessoa> model = find(id);
		if (model.isPresent()) {
			repositorio.delete(model.get());
		}
	}

	public Pessoa update(Pessoa model) {
		Optional<Pessoa> obj = find(model.getId());
		if (obj.isPresent()) {
			return repositorio.save(model);
		}
		return model;
	}

	public Optional<Pessoa> find(Long id) {
		return repositorio.findById(id);
	}

	public List<Pessoa> findByFieldNameAndValue(String campo, String valor){
		return repositorio.findByFieldNameAndValue(campo, valor, "nome");
	}
	
	public List<Pessoa> findAll() {
		return repositorio.getAll("nome");
	}

	public List<Pessoa> consultar(String texto) {
		return repositorio.findByFieldNameAndValue("nome", texto, "nome");
	}

}
