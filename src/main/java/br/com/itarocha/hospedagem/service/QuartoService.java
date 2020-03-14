package br.com.itarocha.hospedagem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;
import br.com.itarocha.hospedagem.dto.EditLeitoVO;
import br.com.itarocha.hospedagem.dto.EditQuartoVO;
import br.com.itarocha.hospedagem.model.Leito;
import br.com.itarocha.hospedagem.model.Logico;
import br.com.itarocha.hospedagem.dto.NovoQuartoVO;
import br.com.itarocha.hospedagem.model.Quarto;
import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.SituacaoLeito;
import br.com.itarocha.hospedagem.model.TipoLeito;
import br.com.itarocha.hospedagem.repository.DestinacaoHospedagemRepository;
import br.com.itarocha.hospedagem.repository.LeitoRepository;
import br.com.itarocha.hospedagem.repository.QuartoRepository;
import br.com.itarocha.hospedagem.repository.SituacaoLeitoRepository;
import br.com.itarocha.hospedagem.repository.TipoLeitoRepository;

@ApplicationScoped
public class QuartoService {

	@Inject QuartoRepository quartoRepo;
	
	@Inject LeitoRepository leitoRepo;
	
	@Inject TipoLeitoRepository tipoLeitoRepo;
	
	@Inject SituacaoLeitoRepository situacaoLeitoRepo;
	
	@Inject DestinacaoHospedagemRepository destinacaoHospedagemRepo;

	public Quarto create(Quarto model) {
		try{
			return quartoRepo.save(model);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public Quarto create(NovoQuartoVO model) throws Exception{
		Quarto q = new Quarto();
		try {
			Optional<TipoLeito> tipoLeito = tipoLeitoRepo.findById(model.getTipoLeito());
			Optional<SituacaoLeito> situacao = situacaoLeitoRepo.findById(model.getSituacao());
			
			q.setNumero(model.getNumero());
			q.setDescricao(model.getDescricao());

			for (Long id : model.getDestinacoes()) {
				Optional<DestinacaoHospedagem> dh = destinacaoHospedagemRepo.findById(id);
				dh.ifPresent( d -> {
					q.getDestinacoes().add(d);
				});
			}

			q.setAtivo(Logico.S);
			quartoRepo.save(q);
			
			for (int i = 1; i <= model.getQuantidadeLeitos(); i++) {
				Leito leito = new Leito();
				leito.setQuarto(q);
				leito.setNumero(i);
				leito.setTipoLeito(tipoLeito.get());
				leito.setSituacao(situacao.get());
				
				leitoRepo.save(leito);
			}
		} catch (Exception e) {
			throw e;
		}
		return q;
	}
	
  	public Optional<Quarto> find(Long id) {
		return quartoRepo.findById(id);
	}

  	public Optional<Leito> findLeito(Long id) {
		return leitoRepo.findById(id);
	}

  	public Leito saveLeito(EditLeitoVO model) throws Exception{
		Leito leito;
		boolean isNovo = model.getId() == null; 
		if (isNovo) {
			leito = new Leito();
		} else {
			Optional<Leito> optLeito = leitoRepo.findById(model.getId());
			
			if (optLeito.isPresent()) {
				leito = optLeito.get();
			} else {
				throw new Exception("Leito inexistente: "+model.getId());
			}
		}
		
		try {
			Optional<TipoLeito> tipoLeito = tipoLeitoRepo.findById(model.getTipoLeito());
			Optional<SituacaoLeito> situacao = situacaoLeitoRepo.findById(model.getSituacao());
			leito.setNumero(model.getNumero());
			leito.setTipoLeito(tipoLeito.get());
			leito.setSituacao(situacao.get());
			
			if (isNovo) {
				Optional<Quarto> quarto = quartoRepo.findById(model.getQuartoId());
				leito.setQuarto(quarto.get());
			}
			leito = leitoRepo.save(leito);
		} catch (Exception e) {
			throw e;
		}
		return leito;
	}
	
	public boolean remove(Long id) {
		Optional<Quarto> model = quartoRepo.findById(id);
		if (model.isPresent()) {
			leitoRepo.deleteWhereQuartoId(model.get().getId());
			quartoRepo.delete(model.get());
			return true;
		}
		return false;
	}

	public boolean removeLeito(Long id){
		return leitoRepo.remove(id);
	}

	public Quarto update(EditQuartoVO model) {
		Optional<Quarto> oq = quartoRepo.findById(model.getId());
		if (oq.isPresent()) {
			Quarto q = oq.get();
			q.setDescricao(model.getDescricao());

			q.getDestinacoes().clear();
			for (Long id : model.getDestinacoes()) {
				Optional<DestinacaoHospedagem> dh = destinacaoHospedagemRepo.findById(id);
				dh.ifPresent(d -> {
					q.getDestinacoes().add(d);
				});
			}
			// FIXME: propriedade removida
			//DestinacaoHospedagem dest = destinacaoHospedagemRepo.getOne(model.getDestinacaoHospedagem());
			//obj.setDestinacaoHospedagem(dest);
			q.setNumero(model.getNumero());
			return quartoRepo.save(q);
		} else {
			return null;
		}
	}

	public List<Quarto> findAll() {
		return quartoRepo.findAllOrderByQuartoNumero();
	}

	public List<Quarto> findAllByDestinacaoHospedagem(Long id) {
		return quartoRepo.findByDestinacaoHospedagemId(id);
	}

	public List<Leito> findLeitosByQuarto(Long quartoId) {
		Optional<Quarto> q = quartoRepo.findById(quartoId);
		if (!q.isPresent()) return new ArrayList<Leito>();
		
		List<Leito> lst = leitoRepo.findByQuartoId(q.get().getId());
		return lst;
	}

	public List<Leito> findLeitosDisponiveis() {
		return leitoRepo.findAllWhereDisponivel(Logico.S);
	}

	public List<SelectValueVO> listTipoLeito() {
		List<SelectValueVO> retorno = new ArrayList<SelectValueVO>();
		
		List<TipoLeito> lst = tipoLeitoRepo.getAll("descricao");
		
		lst.forEach(x -> retorno.add(new SelectValueVO(x.getId(), x.getDescricao())));
		
		return retorno;
	}

	public boolean existeOutroLeitoComEsseNumero(Long leito_id, Long quartoId, Integer numero) {
		Collection<Leito> lst = quartoRepo.existeOutroLeitoComEsseNumero(leito_id, quartoId, numero);
		return lst.size() > 0;
	}

	public boolean existeOutroLeitoComEsseNumero(Long quartoId, Integer numero) {
		Collection<Leito> lst = quartoRepo.existeOutroLeitoComEsseNumero(quartoId, numero);
		return lst.size() > 0;
	}

	public boolean existeOutroQuartoComEsseNumero(Long id, Integer numero) {
		List<Quarto> lst = quartoRepo.existeOutroQuartoComEsseNumero(id, numero);
		return lst.size() > 0;
	}
	
	public boolean existeOutroQuartoComEsseNumero(Integer numero) {
		List<Quarto> lst = quartoRepo.existeOutroQuartoComEsseNumero(numero);
		return lst.size() > 0;
	}
}