package projetopi.finddevservice.mapper.custom;


import org.springframework.stereotype.Service;
import projetopi.finddevservice.dtos.v1.request.EmpresaRequestDto;
import projetopi.finddevservice.models.EmpresaModel;

@Service
public class EmpresaMapper {

    public EmpresaRequestDto convertEntityToDto(EmpresaModel person){

        EmpresaRequestDto dto = new EmpresaRequestDto();
        dto.setKey(person.getId());
        dto.setNome(person.getNome());
        dto.setEmail(person.getEmail());
        dto.setSenha(person.recuperaSenha());
        dto.setEstado(person.getEstado());
        dto.setCidade(person.getCidade());
        dto.setTelefone(person.getTelefone());
        dto.setBairro(person.getBairro());
        dto.setEndereco(person.getEndereco());
        dto.setCnpj(person.getCnpj());
        return dto;

    }

    public EmpresaModel convertDtoToEntity(EmpresaRequestDto person){

        EmpresaModel entity = new EmpresaModel();
        entity.setId(person.getKey());
        entity.setNome(person.getNome());
        entity.setEmail(person.getEmail());
        entity.setSenha(person.getSenha());
        entity.setEstado(person.getEstado());
        entity.setCidade(person.getCidade());
        entity.setTelefone(person.getTelefone());
        entity.setBairro(person.getBairro());
        entity.setEndereco(person.getEndereco());
        entity.setCnpj(person.getCnpj());
        return entity;

    }

}
