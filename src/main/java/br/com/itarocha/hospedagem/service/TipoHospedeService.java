package br.com.itarocha.hospedagem.service;

import java.util.List;
import java.util.Optional;

import br.com.itarocha.hospedagem.dto.SelectValueVO;

import br.com.itarocha.hospedagem.dto.TipoHospedeDTO;
import br.com.itarocha.hospedagem.repository.TipoHospedeRepository;
import br.com.itarocha.hospedagem.model.TipoHospede;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TipoHospedeService {

	@Inject
	private TipoHospedeRepository repositorio;

	public TipoHospede create(TipoHospedeDTO model) {
		try{
			TipoHospede entity = new TipoHospede();
			entity.setId(model.getId());
			entity.setDescricao(model.getDescricao());
			return repositorio.save(entity);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public boolean remove(Long id) {
		return repositorio.remove(id);
	}

	public TipoHospede update(TipoHospede model) {
		return repositorio.save(model);
	}

	public Optional<TipoHospede> find(Long id) {
		return repositorio.findById(id);
	}

	public List<TipoHospede> findAll() {
		return repositorio.getAll("descricao");
	}
	
	public List<SelectValueVO> listSelect() {
		return repositorio.getListSelect("id", "descricao", "descricao");
	}
}
