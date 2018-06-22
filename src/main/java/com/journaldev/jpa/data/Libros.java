package com.journaldev.jpa.data;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the libros database table.
 * 
 */
@Entity
@Table(name="libros")
@NamedQuery(name="Libro.findAll", query="SELECT l FROM Libros l")
public class Libros implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String codigolibro;

	private int anio;

	private String autor;

	private String titulo;
	
	private String imagen;
	
	private String descripcion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="nombreusuario")
	private Usuarios usuario;

	public Libros() {
	}

	public String getCodigolibro() {
		return this.codigolibro;
	}

	public void setCodigolibro(String codigolibro) {
		this.codigolibro = codigolibro;
	}

	public int getAnio() {
		return this.anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getAutor() {
		return this.autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuarios getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}