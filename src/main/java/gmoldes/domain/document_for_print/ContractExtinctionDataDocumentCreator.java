package gmoldes.domain.document_for_print;

import com.lowagie.text.DocumentException;
import gmoldes.components.contract.contract_variation.controllers.ContractExtinctionController;
import gmoldes.components.contract.contract_variation.forms.ContractExtinctionDataSubfolder;
import gmoldes.components.contract.contract_variation.services.ContractExtinctionDataSubfolderPDFCreator;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ContractExtinctionDataDocumentCreator {

    private ContractExtinctionController contractExtinctionController;

    public ContractExtinctionDataDocumentCreator(ContractExtinctionController contractExtinctionController) {

        this.contractExtinctionController = contractExtinctionController;
    }

    public ContractExtinctionDataSubfolder createContractExtinctionDataSubfolder(String additionalData){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        ContractFullDataDTO contractFullDataDTO = this.contractExtinctionController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem();

        String notificationType = Parameters.CONTRACT_EXTINCTION_TEXT;

        LocalDate clientNotificationDate = this.contractExtinctionController.getContractVariationTypes().getDateNotification().getDate();
        LocalTime clientNotificationHour = LocalTime.parse(this.contractExtinctionController.getContractVariationTypes().getHourNotification().getText());

        String birthDate = contractFullDataDTO.getEmployee().getFechanacim() != null ? dateFormatter.format(contractFullDataDTO.getEmployee().getFechanacim()) : null;

        LocalDate startDate = this.contractExtinctionController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue();

        String daysOfWeek = contractFullDataDTO.getContractNewVersion().getContractJsonData().getDaysOfWeekToWork();
        Set<DayOfWeek> dayOfWeekSet = retrieveDayOfWeekSet(daysOfWeek);

        String address = contractFullDataDTO.getEmployee().getDireccion() != null ?  contractFullDataDTO.getEmployee().getDireccion() : "";
        String codPostal = contractFullDataDTO.getEmployee().getCodpostal() != null ? contractFullDataDTO.getEmployee().getCodpostal().toString() : "";
        String location = contractFullDataDTO.getEmployee().getLocalidad() != null ? contractFullDataDTO.getEmployee().getLocalidad() : "";
        String fullAddress = address + "   " + codPostal + "   " + location;

        StudyManager studyManager = new StudyManager();
        StudyDTO study = studyManager.findStudyById(contractFullDataDTO.getEmployee().getNivestud());

        ContractTypeController contractTypeController = new ContractTypeController();
        Integer contractTypeId = contractFullDataDTO.getContractNewVersion().getContractJsonData().getContractType();
        ContractTypeDTO contractTypeDTO = contractTypeController.findContractTypeById(contractTypeId);

        String contractTypeDescription = contractTypeDTO.getColloquial() + ", " + contractFullDataDTO.getContractType().getContractDescription();

        String fullPartialWorkDays = contractFullDataDTO.getContractNewVersion().getContractJsonData().getFullPartialWorkDay();
        if(fullPartialWorkDays.equals(ContractConstants.PARTIAL_WORKDAY)) {
            contractTypeDescription = contractTypeDescription + " [" + this.contractExtinctionController.getContractVariationParts().getContractSelector().getValue()
                    .getContractNewVersion().getContractJsonData().getWeeklyWorkHours() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() + "]";
        }

        return ContractExtinctionDataSubfolder.create()
                .withNotificationType(notificationType)
                .withOfficialContractNumber(contractFullDataDTO.getContractNewVersion().getContractJsonData().getIdentificationContractNumberINEM())
                .withEmployerFullName(contractFullDataDTO.getEmployer().toString())
                .withEmployerQuoteAccountCode(contractFullDataDTO.getContractNewVersion().getContractJsonData().getQuoteAccountCode())
                .withNotificationDate(clientNotificationDate)
                .withNotificationHour(clientNotificationHour)
                .withEmployeeFullName(contractFullDataDTO.getEmployee().getApellidos() + ", " + contractFullDataDTO.getEmployee().getNom_rzsoc())
                .withEmployeeNif(Utilities.formatAsNIF(contractFullDataDTO.getEmployee().getNifcif()))
                .withEmployeeNASS(contractFullDataDTO.getEmployee().getNumafss())
                .withEmployeeBirthDate(birthDate)
                .withEmployeeCivilState(contractFullDataDTO.getEmployee().getEstciv())
                .withEmployeeNationality(contractFullDataDTO.getEmployee().getNacionalidad())
                .withEmployeeFullAddress(fullAddress)
                .withContractTypeDescription(contractTypeDescription)
                .withEmployeeMaxStudyLevel(study.getStudyDescription())
                .withStartDate(null)
                .withEndDate(startDate)
                .withDayOfWeekSet(dayOfWeekSet)
                .withDurationDays("")
                .withSchedule(new HashSet<>())
                .withAdditionalData(additionalData)
                .withLaborCategory(contractFullDataDTO.getContractNewVersion().getContractJsonData().getLaborCategory())
                .build();
    }

    private Set<DayOfWeek> retrieveDayOfWeekSet(String daysOfWeek){

        Set<DayOfWeek> dayOfWeekSet = new HashSet<>();

        if(daysOfWeek.contains("MONDAY")){
            dayOfWeekSet.add(DayOfWeek.MONDAY);
        }

        if(daysOfWeek.contains("TUESDAY")){
            dayOfWeekSet.add(DayOfWeek.TUESDAY);
        }

        if(daysOfWeek.contains("WEDNESDAY")){
            dayOfWeekSet.add(DayOfWeek.WEDNESDAY);
        }


        if(daysOfWeek.contains("THURSDAY")){
            dayOfWeekSet.add(DayOfWeek.THURSDAY);
        }


        if(daysOfWeek.contains("FRIDAY")){
            dayOfWeekSet.add(DayOfWeek.FRIDAY);
        }

        if(daysOfWeek.contains("SATURDAY")){
            dayOfWeekSet.add(DayOfWeek.SATURDAY);
        }

        if(daysOfWeek.contains("SUNDAY")){
            dayOfWeekSet.add(DayOfWeek.SUNDAY);
        }

        return dayOfWeekSet;
    }

    public Path retrievePathToContractExtinctionDataSubfolderPDF(ContractExtinctionDataSubfolder contractExtinctionDataSubfolder){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataSubfolder = Paths.get(Parameters.USER_HOME, temporalDir, contractExtinctionDataSubfolder.toFileName().concat(Parameters.PDF_EXTENSION));
        try {
            Files.createDirectories(pathToContractDataSubfolder.getParent());
            pathOut = ContractExtinctionDataSubfolderPDFCreator.createContractExtinctionDataSubfolderPDF(contractExtinctionDataSubfolder, pathToContractDataSubfolder);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }
}
