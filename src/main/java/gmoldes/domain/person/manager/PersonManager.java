package gmoldes.domain.person.manager;


import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.persistence.dao.PersonDAO;
import gmoldes.domain.person.persistence.vo.PersonVO;

import java.util.ArrayList;
import java.util.List;

public class PersonManager {

    public PersonManager() {
    }

    public List<PersonDTO> findAllPersonsByNamePatternInAlphabeticalOrder(String namePattern) {

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        List<PersonVO> personVOList = personDAO.findAllPersonsByNamePatternInAlphabeticalOrder(namePattern);
        for (PersonVO personVO : personVOList) {
            PersonDTO personDTO = PersonDTO.create()
                    .withIdpersona(personVO.getIdpersona())
                    .withApellidos(personVO.getApellidos())
                    .withNom_rzsoc(personVO.getNom_rzsoc())
                    .withCodpostal(personVO.getCodpostal())
                    .withDireccion(personVO.getDireccion())
                    .withEstciv(personVO.getEstciv())
                    .withNumafss(personVO.getNumafss())
                    .withFechanacim(personVO.getFechanacim())
                    .withLocalidad(personVO.getLocalidad())
                    .withNacionalidad(personVO.getNacionalidad())
                    .withNifcif(personVO.getNifcif())
                    .withNifcifdup(personVO.getNifcifdup())
                    .withNivestud(personVO.getNivestud())
                    .build();

            personDTOList.add(personDTO);
        }
        return personDTOList;
    }

    public PersonDTO findPersonById(Integer id){
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        PersonVO personVO = personDAO.findPersonById(id);
        PersonDTO personDTO = PersonDTO.create()
                .withIdpersona(personVO.getIdpersona())
                .withApellidos(personVO.getApellidos())
                .withNom_rzsoc(personVO.getNom_rzsoc())
                .withCodpostal(personVO.getCodpostal())
                .withDireccion(personVO.getDireccion())
                .withNumafss(personVO.getNumafss())
                .withEstciv(personVO.getEstciv())
                .withFechanacim(personVO.getFechanacim())
                .withLocalidad(personVO.getLocalidad())
                .withNacionalidad(personVO.getNacionalidad())
                .withNifcif(personVO.getNifcif())
                .withNifcifdup(personVO.getNifcifdup())
                .withNivestud(personVO.getNivestud())
                .build();

        return personDTO;
    }
}
