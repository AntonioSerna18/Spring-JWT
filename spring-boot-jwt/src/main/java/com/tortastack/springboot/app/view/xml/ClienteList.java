package com.tortastack.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.tortastack.springboot.app.models.entity.Cliente;

@XmlRootElement(name = "clientesList")
public class ClienteList {
	
	@XmlElement(name = "cliente")
	public List<Cliente> clientes;
	
	public ClienteList() {
	}

	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

}
