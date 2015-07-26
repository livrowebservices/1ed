package br.com.livro.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Carro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String tipo;
	@Column(nullable = false, length = 100)
	private String nome;
	@Column(name = "descricao", nullable = true, length = 500)
	private String desc;
	@Column(nullable = true, length = 50)
	private String urlFoto;
	@Column(nullable = true, length = 50)
	private String urlVideo;
	@Column(nullable = true, length = 15)
	private String latitude;
	@Column(nullable = true, length = 15)
	private String longitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Carro [id=" + id + ", tipo=" + tipo + ", nome=" + nome
				+ ", desc=" + desc + ", urlFoto=" + urlFoto + ", urlVideo="
				+ urlVideo + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
}
