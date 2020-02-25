package br.com.itarocha.hospedagem.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.itarocha.hospedagem.dto.DestinacaoHospedagemDTO;
import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;
import br.com.itarocha.hospedagem.repository.DestinacaoHospedagemRepository;

@ApplicationScoped
public class DestinacaoHospedagemService {

    @Inject DestinacaoHospedagemRepository repositorio;

    public DestinacaoHospedagemService(DestinacaoHospedagemRepository repositorio){
    	this.repositorio = repositorio;
	}

	public DestinacaoHospedagem create(DestinacaoHospedagemDTO model) {
    	try {
			DestinacaoHospedagem entity = new DestinacaoHospedagem();
			entity.setId(model.getId());
			entity.setDescricao(model.getDescricao());
			return repositorio.save(entity);
		} catch (Exception e){
    		throw  new IllegalArgumentException(e.getMessage());
		}
	}

	public boolean remove(Long id) {
		return repositorio.remove(id);
	}

	public Optional<DestinacaoHospedagem> find(Long id) {
		return repositorio.findById(id);
	}

	public List<DestinacaoHospedagem> findAll() {
    	return repositorio.getAll();
	}

	public List<SelectValueVO> listSelect() {
    	return repositorio.getListSelect();
	}
	
}
