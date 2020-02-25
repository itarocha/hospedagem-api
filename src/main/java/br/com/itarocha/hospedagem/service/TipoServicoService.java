package br.com.itarocha.hospedagem.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.dto.TipoServicoDTO;
import br.com.itarocha.hospedagem.repository.TipoServicoRepository;
import br.com.itarocha.hospedagem.model.TipoServico;

@ApplicationScoped
public class TipoServicoService {

	@Inject
	TipoServicoRepository repositorio;

	public TipoServico create(TipoServicoDTO model) {
		try{
			TipoServico entity = new TipoServico();
			entity.setId(model.getId());
			entity.setDescricao(model.getDescricao());
			entity.setAtivo(model.getAtivo());
			return repositorio.save(entity);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public boolean remove(Long id) {
		return repositorio.remove(id);
	}

	public TipoServico update(TipoServico model) {
		return repositorio.save(model);
	}

  	public Optional<TipoServico> find(Long id) {
		return repositorio.findById(id);
	}

	public List<TipoServico> findAll() {
		return repositorio.getAll("descricao");
	}
	
	public List<SelectValueVO> listSelect() {
		//TODO DEVE TER CRITERIA
		return repositorio.getListSelect("id", "descricao", "descricao");
		/*
		List<SelectValueVO> retorno = new ArrayList<SelectValueVO>();
		em.createQuery("SELECT o FROM TipoServico o WHERE o.ativo = 'S' ORDER BY o.descricao",TipoServico.class)
			.getResultList()
			.forEach(x -> retorno.add(new SelectValueVO(x.getId(), x.getDescricao())));
		return retorno;
		 */
	}
}
