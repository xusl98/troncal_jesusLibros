package com.journaldev.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuarios u")
public class Usuarios implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;

	private String password;
	
	private String email;

	//bi-directional many-to-one association to Libro
	@OneToMany(mappedBy="usuario")
	private List<Libros> libros;

	public Usuarios() {
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Libros> getLibros() {
		return this.libros;
	}

	public void setLibros(List<Libros> libros) {
		this.libros = libros;
	}

	public Libros addLibro(Libros libro) {
		getLibros().add(libro);
		libro.setUsuario(this);

		return libro;
	}

	public Libros removeLibro(Libros libro) {
		getLibros().remove(libro);
		libro.setUsuario(null);

		return libro;
	}

}