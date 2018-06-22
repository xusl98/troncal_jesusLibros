package com.journaldev.prime.faces.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.journaldev.jpa.data.Libros;
import com.journaldev.spring.service.LibroDao;



@ManagedBean
@SessionScoped
public class LibroBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public List<Libros> lstLibros;

	@ManagedProperty("#{libroDao}")
	private LibroDao libroDao;

	private Libros libro = new Libros();

	public LibroDao getLibroDao() {
		return libroDao;
	}

	public void setLibroDao(LibroDao LibroService) {
		this.libroDao = LibroService;
	}

	public Libros getLibro() {
		return libro;
	}

	public void setLibro(Libros libro) {
		this.libro = libro;
	}
	
	

	public List<Libros> getLstLibros() {
		return lstLibros;
	}

	public void setLstLibros(List<Libros> lstLibros) {
		this.lstLibros = lstLibros;
	}
	
	public void listar(){
		
		lstLibros = libroDao.listar();
	}

	public String register() {
		// Calling Business Service
		libroDao.register(libro);
		String tit = libro.getTitulo();
		libro = new Libros();
		listar();
		// Add message
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage("El libro \""+tit+"\" ha sido registrado satisfactoriamente"));
		
		return "";
	}
	
	public boolean verificarSesion() {
		return true;
	}
	
	
	public void eliminar(Libros libro) {
		
		libroDao.eliminar(libro);
		listar();
	}
	
	public String leer(Libros libro) {
		this.libro = libro;
		return "modify";
	}
	public String home() {
		return "index";
	}
	
	public String cerrarS() {
		return "login";
	}
	
	public String modificar() {
		libroDao.modificar(libro);
		return "index";
	}
	
	public String cancelar() {
		
		return "index";
	}
	
	public void cerrarSesion() {
		
	}
	

}
