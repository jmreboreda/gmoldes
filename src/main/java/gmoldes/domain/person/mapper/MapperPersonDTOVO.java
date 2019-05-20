package gmoldes.domain.person.mapper;

import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.persistence.vo.PersonVO;

import java.sql.Date;

public class MapperPersonDTOVO {


    public static PersonVO map(PersonDTO personDTO) {

        Date fechaNacim = personDTO.getFechanacim() != null ? Date.valueOf(personDTO.getFechanacim()): null;

        PersonVO personVO = new PersonVO();

        personVO.setIdpersona(personDTO.getIdpersona());
        personVO.setApellidos(personDTO.getApellidos());
        personVO.setNom_rzsoc(personDTO.getNom_rzsoc());
        personVO.setNifcif(personDTO.getNifcif());
        personVO.setNifcifdup(personDTO.getNifcifdup());
        personVO.setNumafss(personDTO.getNumafss());
        personVO.setFechanacim(fechaNacim);
        personVO.setEstciv(personDTO.getEstciv());
        personVO.setDireccion(personDTO.getDireccion());
        personVO.setLocalidad(personDTO.getLocalidad());
        personVO.setCodpostal(personDTO.getCodpostal());
        personVO.setNivestud(personDTO.getNivestud());
        personVO.setNacionalidad(personDTO.getNacionalidad());

        return personVO;
    }
}
