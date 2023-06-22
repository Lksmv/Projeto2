package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.*;
import com.pog.projeto.entity.*;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.PacoteRepository;
import com.pog.projeto.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PacoteService {

    private final ObjectMapper objectMapper;
    private final PacoteRepository repository;
    private final HotelService hotelService;
    private final PontoTuristicoService pontoTuristicoService;
    private final VooService vooService;
    private final PessoaRepository pessoaRepository;

    public PacoteDTO create() {
        PacoteEntity pacoteEntity = new PacoteEntity();
        pacoteEntity.setHoteis(Collections.emptySet());
        pacoteEntity.setVooEntities(Collections.emptySet());
        pacoteEntity.setPontoTuristicoEntities(Collections.emptySet());
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())).get();
        if (pessoaEntity.getCargoEntity().getNome().equals("ROLE_ADMIN")) {
            pacoteEntity.setPromocional("S");
        } else {
            pacoteEntity.setPromocional("N");
        }
        pacoteEntity.setDataPartida(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        pacoteEntity.setDataChegada(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        pacoteEntity.setNome("Pacote " + (pessoaEntity.getPacoteEntities().size() + 1));
        pacoteEntity.setValor(0.0);
        pacoteEntity.setPessoas(Collections.singleton(pessoaEntity));
        return toDTO(repository.save(pacoteEntity));
    }

    public List<PacoteDTO> listar() {
        List<PacoteEntity> lista = repository.findAll();
        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PacoteDTO atualizar(Integer idPacote, String nome, Date dataPartida, Date dataChegada, String cidade) throws BusinessException {
        PacoteEntity pacoteEntity = repository.findById(idPacote).orElseThrow(() -> new BusinessException("Id invalido"));
        pacoteEntity.setCidade(cidade == null ? pacoteEntity.getCidade() : cidade);
        pacoteEntity.setNome(nome == null ? pacoteEntity.getNome() : nome);
        pacoteEntity.setDataChegada(dataChegada == null ? pacoteEntity.getDataChegada() : dataChegada);
        pacoteEntity.setDataPartida(dataPartida == null ? pacoteEntity.getDataPartida() : dataPartida);
        return toDTO(repository.save(pacoteEntity));
    }

    public PacoteDTO getById(Integer idPacote) throws BusinessException {
        PacoteEntity pacoteEntity = repository.findById(idPacote).orElseThrow(() -> new BusinessException("Id invalido."));
        return toDTO(pacoteEntity);
    }

    public PacoteEntity getEntityById(Integer idPacote) throws BusinessException {
        return repository.findById(idPacote).orElseThrow(() -> new BusinessException("Id invalido."));
    }

    public void deletePacote(Integer idPacote) throws BusinessException {
        PacoteEntity pacoteEntity = getEntityById(idPacote);
        if (pacoteEntity.getPromocional() == "S") {
            String idPessoa = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(idPessoa)).get();
            Set<PacoteEntity> pacoteEntities = pessoaEntity.getPacoteEntities();
            pacoteEntities.remove(pacoteEntity);
            pessoaEntity.setPacoteEntities(pacoteEntities);
            pessoaRepository.save(pessoaEntity);
        } else {
            repository.deleteById(idPacote);
        }
    }

    public PacoteDTO toDTO(PacoteEntity pacoteEntity) {
        PacoteDTO pacoteDTO = objectMapper.convertValue(pacoteEntity, PacoteDTO.class);
        pacoteDTO.setVooDTOS(pacoteEntity.getVooEntities().stream()
                .map(vooEntity -> vooService.toDTO(vooEntity))
                .collect(Collectors.toSet()));
        pacoteDTO.setHoteis(pacoteEntity.getHoteis().stream()
                .map(hotel -> hotelService.toDTO(hotel))
                .collect(Collectors.toSet()));
        pacoteDTO.setPontoTuristicoDTOS(pacoteEntity.getPontoTuristicoEntities().stream()
                .map(pontoTuristicoEntity -> pontoTuristicoService.toDTO(pontoTuristicoEntity))
                .collect(Collectors.toSet()));
        return pacoteDTO;
    }

    public List<PacoteListagemDTO> pacotesUsuarioLogado() {
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())).get();
        return pessoaEntity.getPacoteEntities().stream()
                .map(pacoteEntity -> objectMapper.convertValue(pacoteEntity, PacoteListagemDTO.class))
                .collect(Collectors.toList());
    }

    public PacoteDTO adicionarHotel(Integer idHotel, Integer idPacote) throws BusinessException {
        PacoteEntity pacoteEntity = repository.findById(idPacote).orElseThrow(() -> new BusinessException("Não Encontrado pacote"));
        Set<HotelEntity> hotel = pacoteEntity.getHoteis();
        HotelEntity h = hotelService.findEntityById(idHotel);
        hotel.add(h);
        pacoteEntity.setHoteis(hotel);
        LocalDate d1 = h.getDataChegada().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = h.getDataPartida().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Double dias = (double) ChronoUnit.DAYS.between(d2, d1);
        pacoteEntity.setValor(pacoteEntity.getValor() + (dias * h.getDiaria()));
        pacoteEntity = repository.save(pacoteEntity);
        return toDTO(pacoteEntity);
    }

    public PacoteDTO adicionarPontoTuristico(Integer idPonto, Integer idPacote) throws BusinessException {
        PacoteEntity pacoteEntity = repository.findById(idPacote).orElseThrow(() -> new BusinessException("Não Encontrado pacote"));
        Set<PontoTuristicoEntity> pontoTuristicoEntities = pacoteEntity.getPontoTuristicoEntities();
        pontoTuristicoEntities.add(pontoTuristicoService.findEntityById(idPonto));
        pacoteEntity.setPontoTuristicoEntities(pontoTuristicoEntities);
        pacoteEntity = repository.save(pacoteEntity);
        return toDTO(pacoteEntity);
    }

    public PacoteDTO adicionarVoo(Integer idVoo, Integer idPacote) throws BusinessException {
        PacoteEntity pacoteEntity = repository.findById(idPacote).orElseThrow(() -> new BusinessException("Não Encontrado pacote"));
        Set<VooEntity> vooEntities = pacoteEntity.getVooEntities();
        VooEntity v = vooService.findEntityById(idVoo);
        vooEntities.add(vooService.findEntityById(idVoo));
        pacoteEntity.setVooEntities(vooEntities);
        pacoteEntity.setValor(pacoteEntity.getValor() + v.getValor());
        pacoteEntity = repository.save(pacoteEntity);
        return toDTO(pacoteEntity);
    }


}
