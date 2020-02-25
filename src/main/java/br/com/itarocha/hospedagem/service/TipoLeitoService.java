package br.com.itarocha.hospedagem.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.dto.TipoLeitoDTO;
import br.com.itarocha.hospedagem.model.TipoLeito;
import br.com.itarocha.hospedagem.repository.TipoLeitoRepository;

@ApplicationScoped
public class TipoLeitoService {

	@Inject TipoLeitoRepository repositorio;

	public TipoLeito create(TipoLeitoDTO model) {
		try{
			TipoLeito entity = new TipoLeito();
			entity.setId(model.getId());
			entity.setDescricao(model.getDescricao());
			entity.setQuantidadeCamas(model.getQuantidadeCamas());
			return repositorio.save(entity);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public boolean remove(Long id) {
		return repositorio.remove(id);
	}

	public TipoLeito update(TipoLeito model) {
		return repositorio.save(model);
	}
	  
  	public Optional<TipoLeito> find(Long id) {
		return repositorio.findById(id);
	}

	public List<TipoLeito> findAll() {
		return repositorio.getAll("descricao");
	}
	
	public List<SelectValueVO> listSelect() {
		return repositorio.getListSelect("id", "descricao", "descricao");
	}
}
