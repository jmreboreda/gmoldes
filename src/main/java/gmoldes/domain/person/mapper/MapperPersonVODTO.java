package gmoldes.domain.person.mapper;

import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.persistence.vo.PersonVO;

public class MapperPersonVODTO {


    public static PersonDTO mapPersonVODTO(PersonVO personVO){

        return PersonDTO.create()
                .withIdpersona(personVO.getIdpersona())
                .withApellidos(personVO.getApellidos())
                .withNom_rzsoc(personVO.getNom_rzsoc())
                .withNifcif(personVO.getNifcif())
                .withNifcifdup(personVO.getNifcifdup())
                .withNumafss(personVO.getNumafss())
                .withFechanacim(personVO.getFechanacim())
                .withEstciv(personVO.getEstciv())
                .withDireccion(personVO.getDireccion())
                .withLocalidad(personVO.getLocalidad())
                .withCodpostal(personVO.getCodpostal())
                .withNivestud(personVO.getNivestud())
                .withNacionalidad(personVO.getNacionalidad())
                .build();
    }
}
