package br.com.itarocha.hospedagem.dto;

import java.io.Serializable;
import java.util.Objects;

public class SelectValueVO implements Serializable{

	private static final long serialVersionUID = -432146171536143461L;

	private Long value;
	
	private String text;

	public Long getValue() {
		return value;
	}

	public SelectValueVO() {
		
	}
	
	public SelectValueVO(Long value, String text) {
		this.value = value;
		this.text = text;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "SelectValueVO{" +
				"value=" + value +
				", text='" + text + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SelectValueVO that = (SelectValueVO) o;
		return value.equals(that.value) &&
				text.equals(that.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, text);
	}
}
