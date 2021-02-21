package com.xantrix.webapp.service;

import com.xantrix.webapp.entities.DettListini;

public interface PrezziService {
	public DettListini SelPrezzo(String codiceArticolo, String idListino);

	public DettListini InsPrezzo(DettListini dettListini);

	public void DelPrezzo(String codiceArticolo, String idListino);
}
