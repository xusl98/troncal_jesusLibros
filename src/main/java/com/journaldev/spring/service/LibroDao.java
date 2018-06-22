package com.journaldev.spring.service;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.journaldev.jpa.data.Libros;
import com.journaldev.jpa.data.Usuarios;
import com.journaldev.prime.faces.beans.UsuarioBean;

@Component
public class LibroDao {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Transactional
	public void register(Libros emp) {
		// Save employee
		
		Usuarios u = new Usuarios();
		Usuarios usuLogeado = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); 
		u = em.find(Usuarios.class, usuLogeado.getNombre());
		emp.setUsuario(u);
		this.em.persist(emp);


	}

	public List<Libros> listar() {

		Usuarios usuLogeado = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); 
		
		List<Libros> lst = em.createNativeQuery("SELECT * FROM Libros WHERE nombreusuario='" + usuLogeado.getNombre() + "'", Libros.class).getResultList();
		return lst;
	}


	@Transactional
	public void eliminar(Libros libro) {

		if (!em.contains(libro)) {
			libro = em.merge(libro);
		}

		em.remove(libro);
	}

	@Transactional
	public void modificar(Libros libro) {

		String key = libro.getCodigolibro();
		Libros libroMod = (Libros) em.find(Libros.class, key);
		em.merge(libroMod);
		
		libroMod.setCodigolibro(libro.getCodigolibro());
		libroMod.setTitulo(libro.getTitulo());
		libroMod.setAutor(libro.getAutor());
		libroMod.setAnio(libro.getAnio());
		libroMod.setDescripcion(libro.getDescripcion());
		libroMod.setImagen(libro.getImagen());
		
	}
	
	
	
	

}
