package com.journaldev.prime.faces.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.journaldev.jpa.data.Libros;
import com.journaldev.jpa.data.Usuarios;
import com.journaldev.spring.service.LibroDao;
import com.journaldev.spring.service.UsuarioDao;



@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public List<Usuarios> lstUsuarios;
	

	@ManagedProperty("#{usuarioDao}")
	private UsuarioDao usuarioDao;

	private Usuarios usuario = new Usuarios();

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao UsuarioService) {
		this.usuarioDao = UsuarioService;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	
	
	



	public List<Usuarios> getLstUsuarios() {
		return lstUsuarios;
	}

	public void setLstUsuarios(List<Usuarios> lstusuarios) {
		this.lstUsuarios = lstUsuarios;
	}
	
	
	public String irRegistrar() {
		String res = usuarioDao.irRegistrar();
		
		return res;
	}
	
	public String iniciarSesion() {
		String res = usuarioDao.iniciarSesion(usuario);
		return res;
	}
	
	
	
	public String registrar() {
		
		String res = usuarioDao.registrar(usuario);
		return res;
	}
	
	public String irOlvidar() {
		return "olvidar";
	}
	

	public void olvidar() throws AddressException, MessagingException {
		
		usuarioDao.olvidar(usuario);
	}
	

	

}
