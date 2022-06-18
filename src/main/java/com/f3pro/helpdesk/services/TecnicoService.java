package com.f3pro.helpdesk.services;

import com.f3pro.helpdesk.domain.Pessoa;
import com.f3pro.helpdesk.domain.Tecnico;
import com.f3pro.helpdesk.domain.dtos.TecnicoDTO;
import com.f3pro.helpdesk.repositories.PessoaRepository;
import com.f3pro.helpdesk.repositories.TecnicoRepository;
import com.f3pro.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.f3pro.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return  obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! id: " + id));
    }

	public List<Tecnico> findAll() {
		
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}


	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
		
	}

	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico pussui ordens de serviço e não pode ser deletado");
		}
		repository.deleteById(id);
	
		}
		
		
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() !=objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
	
		}
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() !=objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
	
		}
	}



}

