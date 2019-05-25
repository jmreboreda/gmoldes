package gmoldes.domain.person;


import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.mapper.MapperPersonDTOVO;
import gmoldes.domain.person.persistence.dao.PersonDAO;
import gmoldes.domain.person.persistence.vo.PersonVO;

import java.text.Collator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PersonManager {

    public PersonManager() {
    }

    public Integer createPerson(PersonDTO personDTO){
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();

        PersonVO personVO = MapperPersonDTOVO.map(personDTO);

        return personDAO.createPerson(personVO);
    }

    public Integer updatePerson(PersonDTO personDTO){
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();

        PersonVO personVO = MapperPersonDTOVO.map(personDTO);

        return personDAO.updatePerson(personVO);
    }

    public List<PersonDTO> findAllPersonInAlphabeticalOrder(){
        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        List<PersonVO> personVOList = personDAO.findAllPersonInAlphabeticalOrder();
        for (PersonVO personVO : personVOList) {
            LocalDate birthDate = personVO.getFechanacim() != null ? personVO.getFechanacim().toLocalDate() : null;
            PersonDTO personDTO = PersonDTO.create()
                    .withIdpersona(personVO.getIdpersona())
                    .withApellidos(personVO.getApellidos())
                    .withNom_rzsoc(personVO.getNom_rzsoc())
                    .withCodpostal(personVO.getCodpostal())
                    .withDireccion(personVO.getDireccion())
                    .withEstciv(personVO.getEstciv())
                    .withNumafss(personVO.getNumafss())
                    .withFechanacim(birthDate)
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

    public List<PersonDTO> findAllPersonsByNamePatternInAlphabeticalOrder(String namePattern) {

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        List<PersonVO> personVOList = personDAO.findAllPersonsByNamePattern(namePattern);
        for (PersonVO personVO : personVOList) {
            LocalDate fechaNacim = personVO.getFechanacim() != null ? personVO.getFechanacim().toLocalDate() : null;
            PersonDTO personDTO = PersonDTO.create()
                    .withIdpersona(personVO.getIdpersona())
                    .withApellidos(personVO.getApellidos())
                    .withNom_rzsoc(personVO.getNom_rzsoc())
                    .withCodpostal(personVO.getCodpostal())
                    .withDireccion(personVO.getDireccion())
                    .withEstciv(personVO.getEstciv())
                    .withNumafss(personVO.getNumafss())
                    .withFechanacim(fechaNacim)
                    .withLocalidad(personVO.getLocalidad())
                    .withNacionalidad(personVO.getNacionalidad())
                    .withNifcif(personVO.getNifcif())
                    .withNifcifdup(personVO.getNifcifdup())
                    .withNivestud(personVO.getNivestud())
                    .build();

            personDTOList.add(personDTO);
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        return personDTOList
                .stream()
                .sorted(Comparator.comparing(PersonDTO::toString, primaryCollator)).collect(Collectors.toList());
    }

    public PersonDTO findPersonById(Integer id){
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        PersonVO personVO = personDAO.findPersonById(id);
        LocalDate fechaNacim = personVO.getFechanacim() != null ? personVO.getFechanacim().toLocalDate() : null;

        return PersonDTO.create()
                .withIdpersona(personVO.getIdpersona())
                .withApellidos(personVO.getApellidos())
                .withNom_rzsoc(personVO.getNom_rzsoc())
                .withCodpostal(personVO.getCodpostal())
                .withDireccion(personVO.getDireccion())
                .withNumafss(personVO.getNumafss())
                .withEstciv(personVO.getEstciv())
                .withFechanacim(fechaNacim)
                .withLocalidad(personVO.getLocalidad())
                .withNacionalidad(personVO.getNacionalidad())
                .withNifcif(personVO.getNifcif())
                .withNifcifdup(personVO.getNifcifdup())
                .withNivestud(personVO.getNivestud())
                .build();
    }

    public List<PersonDTO> findPersonByNieNif(String nieNif){
        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        List<PersonVO> personVOList = personDAO.findPersonByNieNif(nieNif);

        for(PersonVO personVO : personVOList){
            LocalDate fechaNacim = personVO.getFechanacim() != null ? personVO.getFechanacim().toLocalDate() : null;
            PersonDTO personDTO = PersonDTO.create()
                    .withIdpersona(personVO.getIdpersona())
                    .withApellidos(personVO.getApellidos())
                    .withNom_rzsoc(personVO.getNom_rzsoc())
                    .withCodpostal(personVO.getCodpostal())
                    .withDireccion(personVO.getDireccion())
                    .withNumafss(personVO.getNumafss())
                    .withEstciv(personVO.getEstciv())
                    .withFechanacim(fechaNacim)
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
}
