package projetopi.finddevservice.mapper.custom;


import org.springframework.stereotype.Service;
import projetopi.finddevservice.dtos.v1.request.DevelopRequestDto;
import projetopi.finddevservice.dtos.v1.response.DevelopResponseDto;
import projetopi.finddevservice.models.DesenvolvedorModel;

@Service
public class DesenvolvedorMapper {

    public DevelopResponseDto convertEntityToDto(DesenvolvedorModel person){

        DevelopResponseDto dto = new DevelopResponseDto();
        dto.setKey(person.getId());
        dto.setNome(person.getNome());
        dto.setEmail(person.getEmail());
        dto.setSenha(person.recuperaSenha());
        dto.setEstado(person.getEstado());
        dto.setCidade(person.getCidade());
        dto.setTelefone(person.getTelefone());
        dto.setCpf(person.getCpf());
        return dto;

    }
    public DesenvolvedorModel convertDtoToEntity(DevelopRequestDto person){

        DesenvolvedorModel entity = new DesenvolvedorModel();
        entity.setId(person.getKey());
        entity.setNome(person.getNome());
        entity.setEmail(person.getEmail());
        entity.setSenha(person.getSenha());
        entity.setEstado(person.getEstado());
        entity.setCidade(person.getCidade());
        entity.setTelefone(person.getTelefone());
        entity.setCpf(person.getCpf());
        return entity;

    }

}
