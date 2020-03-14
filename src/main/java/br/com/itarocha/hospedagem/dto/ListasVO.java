package br.com.itarocha.hospedagem.dto;

import java.util.ArrayList;
import java.util.List;

public class ListasVO {
    private List<SelectValueVO> listaTipoLeito = new ArrayList<>();
    private List<SelectValueVO> listaDestinacaoHospedagem = new ArrayList<>();
    private List<SelectValueVO> listaSituacaoLeito = new ArrayList<>();
    private List<SelectValueVO> listaTipoHospede = new ArrayList<>();
    private List<SelectValueVO> listaTipoServico = new ArrayList<>();
    private List<SelectValueVO> listaEntidade = new ArrayList<>();


    public List<SelectValueVO> getListaTipoLeito() {
        return listaTipoLeito;
    }

    public void setListaTipoLeito(List<SelectValueVO> listaTipoLeito) {
        this.listaTipoLeito = listaTipoLeito;
    }

    public List<SelectValueVO> getListaDestinacaoHospedagem() {
        return listaDestinacaoHospedagem;
    }

    public void setListaDestinacaoHospedagem(List<SelectValueVO> listaDestinacaoHospedagem) {
        this.listaDestinacaoHospedagem = listaDestinacaoHospedagem;
    }

    public List<SelectValueVO> getListaSituacaoLeito() {
        return listaSituacaoLeito;
    }

    public void setListaSituacaoLeito(List<SelectValueVO> listaSituacaoLeito) {
        this.listaSituacaoLeito = listaSituacaoLeito;
    }

    public List<SelectValueVO> getListaTipoHospede() {
        return listaTipoHospede;
    }

    public void setListaTipoHospede(List<SelectValueVO> listaTipoHospede) {
        this.listaTipoHospede = listaTipoHospede;
    }

    public List<SelectValueVO> getListaTipoServico() {
        return listaTipoServico;
    }

    public void setListaTipoServico(List<SelectValueVO> listaTipoServico) {
        this.listaTipoServico = listaTipoServico;
    }

    public List<SelectValueVO> getListaEntidade() {
        return listaEntidade;
    }

    public void setListaEntidade(List<SelectValueVO> listaEntidade) {
        this.listaEntidade = listaEntidade;
    }
}
