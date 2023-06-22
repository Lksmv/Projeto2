package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.HotelCreateDTO;
import com.pog.projeto.dtos.HotelDTO;
import com.pog.projeto.dtos.VooCreateDTO;
import com.pog.projeto.dtos.VooDTO;
import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.PacoteEntity;
import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.entity.VooEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.HotelRepositoy;
import com.pog.projeto.repository.VooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VooService {

    private final ObjectMapper objectMapper;
    private final VooRepository vooRepository;

    public VooDTO create(VooCreateDTO voo) {
        return toDTO(vooRepository.save(toEntity(voo)));
    }

    public List<VooDTO> list() {
        return vooRepository.findAll().stream()
                .map(voo -> toDTO(voo))
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws BusinessException {
        VooEntity entity = findEntityById(id);
        vooRepository.delete(entity);
    }

    public List<VooDTO> findVooIda(Instant dataInstant) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dataInstant.atZone(zone);
        LocalDateTime data = zonedDateTime.toLocalDateTime();
        LocalDateTime dataInicio = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 0, 0, 0);
        LocalDateTime dataFinal = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 23, 59, 59);
        List<VooEntity> vooEntities = vooRepository.findVooEntitiesByDataPartidaBetweenAndDescricao(dataInicio, dataFinal, "IDA");
        return vooEntities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public List<VooDTO> findVooVolta(Instant dataInstant) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dataInstant.atZone(zone);
        LocalDateTime data = zonedDateTime.toLocalDateTime();
        LocalDateTime dataInicio = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 0, 0, 0);
        LocalDateTime dataFinal = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 23, 59, 59);
        List<VooEntity> vooEntities = vooRepository.findVooEntitiesByDataPartidaBetweenAndDescricao(dataInicio, dataFinal, "VOLTA");
        return vooEntities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VooEntity findEntityById(Integer id) throws BusinessException {
        return vooRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado voo"));
    }

    public VooDTO toDTO(VooEntity voo) {
        return objectMapper.convertValue(voo, VooDTO.class);
    }

    public VooEntity toEntity(VooCreateDTO voo) {
        return objectMapper.convertValue(voo, VooEntity.class);
    }

}
