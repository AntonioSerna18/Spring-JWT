package com.tortastack.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tortastack.springboot.app.models.entity.Cliente;
import com.tortastack.springboot.app.models.service.IClienteService;
import com.tortastack.springboot.app.models.service.IUploadFileService;
import com.tortastack.springboot.app.util.paginator.PageRender;
import com.tortastack.springboot.app.view.xml.ClienteList;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileservice;
	
	@Autowired
	private MessageSource messageSource;
	
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource>	verFoto(@PathVariable String filename){
		
		Resource recurso = null;
		try {
			recurso = uploadFileservice.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle de cliente: " + cliente.getNombre());
		
		return "ver";
	}
	
	@GetMapping(value = "/listar-rest")
	public @ResponseBody ClienteList listarRest() {
		return new ClienteList(clienteService.findAll());
	}
	
	@RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, 
						 Authentication authentication, HttpServletRequest request, Locale locale) {
		
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: " .concat(authentication.getName()));
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado, username: " .concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("¡Hola " .concat(auth.getName()).concat(" tienes acceso!"));
		}else {
			logger.info("¡Hola " .concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		
		if(securityContext.isUserInRole("ADMIN")) {
			logger.info("¡Forma usando SecurityContextHolderAwareRequestWrapper: Hola " .concat(auth.getName()).concat(" tienes acceso!"));			
		}else {
			logger.info("¡Forma usando SecurityContextHolderAwareRequestWrapper: Hola " .concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("¡Forma usando HttpServletRequest: Hola " .concat(auth.getName()).concat(" tienes acceso!"));			
		}else {
			logger.info("¡Forma usando HttpServletRequest: Hola " .concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);
		
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		model.put("boton", "Guardar cliente");
		return "form";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;		
		if(id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "¡El id del cliente no existe en la Base de Datos!");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "¡El id del cliente no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		model.put("boton", "Actualizar datos");
		return "form";
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if(result .hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			model.addAttribute("boton", "Guardar cliente");
			return "form";
		}
		
		if(!foto.isEmpty()) {
			
			if(cliente.getId() != null 
					&& cliente.getId() > 0 
					&& cliente.getFoto() != null 
					&& cliente.getFoto().length() > 0) {
				
				uploadFileservice.delete(cliente.getFoto());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileservice.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			flash.addFlashAttribute("info", "Has subido correctamente: '" + uniqueFilename + "'");
			
			cliente.setFoto(uniqueFilename);
			
		}
		
		String mensajeFlash = (cliente.getId() != null)? "¡Cliente editado con éxito!" : "¡Cliente creado con éxito!";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";		
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			Cliente cliente = clienteService.findOne(id);
			clienteService.delete(id);
			flash.addFlashAttribute("success", "¡Cliente eliminado con éxito!");
			
			if(uploadFileservice.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "¡Foto: " + cliente.getFoto() + " eliminada con éxito!");
			}
			
		}
		return "redirect:/listar";
	}
	
	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context != null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("¡Hola usuario: " .concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		
		return false;*/
	}

}
