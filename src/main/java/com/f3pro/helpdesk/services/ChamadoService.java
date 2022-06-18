package com.f3pro.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.f3pro.helpdesk.domain.Cliente;
import com.f3pro.helpdesk.domain.Tecnico;
import com.f3pro.helpdesk.domain.dtos.ChamadoDTO;
import com.f3pro.helpdesk.domain.enums.Prioridade;
import com.f3pro.helpdesk.domain.enums.Status;
import com.f3pro.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f3pro.helpdesk.domain.Chamado;
import com.f3pro.helpdesk.repositories.ChamadoRepository;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado ID " + id));

    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }


    public Chamado create(ChamadoDTO objDTO) {
        return repository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);
    }

    private Chamado newChamado(ChamadoDTO obj) {
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());
        Chamado chamado = new Chamado();
        if (obj.getId() != null) {
            chamado.setId(obj.getId());
        }
        if(obj.getStatus().equals(2)){
            chamado.setDataFechamento(LocalDate.now());
        }
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulos(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;

    }


}
