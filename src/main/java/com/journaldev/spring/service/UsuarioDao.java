package com.journaldev.spring.service;

import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

@Component
public class UsuarioDao {

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

	public String iniciarSesion(Usuarios usuario) {
		List<String> lstPass;

		String nombre = usuario.getNombre();
		String password = usuario.getPassword();

		lstPass = em.createNativeQuery("SELECT password FROM Usuarios WHERE nombre='" + nombre + "'").getResultList();
		if (lstPass.contains(password)) {
			// System.out.print("sdfgdgdfg");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
			return "index";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contraseña incorrecta"));
			return "";
		}
	}

	public String irRegistrar() {
		return "register";
	}

	@Transactional
	public String registrar(Usuarios usuario) {
		List<String> lstUsuarios;

		lstUsuarios = em.createNativeQuery("SELECT nombre FROM Usuarios").getResultList();

		if (lstUsuarios.contains(usuario.getNombre()) == false) {
			this.em.persist(usuario);
			return "login";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ese nombre de usuario ya existe"));
			return "";
		}

	}

	public void olvidar(Usuarios usuario) throws AddressException, MessagingException {

		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port", "587");

		// Nombre del usuario
		props.setProperty("mail.smtp.user", "webdelibroscorreo@gmail.com");

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		MimeMessage message = new MimeMessage(session);

		// Quien envia el correo
		message.setFrom(new InternetAddress("webdelibroscorreo@gmail.com"));

		// A quien va dirigido
		String email = usuario.getEmail();

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

		List<String> lstPass;
		List<String> lstUs;
		lstPass = em.createNativeQuery("SELECT password FROM Usuarios WHERE email='" + email + "'").getResultList();

		if (lstPass.size() < 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Ese correo no tiene una cuenta asociada"));
		} else {

			lstUs = em.createNativeQuery("SELECT nombre FROM Usuarios WHERE email='" + email + "'").getResultList();
			String pass = lstPass.get(0);
			String nombre = lstUs.get(0);
			message.setSubject("Olvidaste tu contraseña");
			message.setText("Esta es tu contraseña de WebDeLibros: '" + pass + "' para el usuario: '" + nombre + "'");

			Transport t = session.getTransport("smtp");

			t.connect("webdelibroscorreo@gmail.com", "polientes");

			t.sendMessage(message, message.getAllRecipients());

			t.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Correo Enviado"));
		}
	}

}
