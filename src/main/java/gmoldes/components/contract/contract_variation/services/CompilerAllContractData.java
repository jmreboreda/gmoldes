package gmoldes.components.contract.contract_variation.services;

import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.new_contract.components.*;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;

public class CompilerAllContractData {

    private ContractParts contractParts;
    private ContractData contractData;
    private ContractPublicNotes contractPublicNotes;
    private ContractPrivateNotes contractPrivateNotes;

    public CompilerAllContractData(
            ContractParts contractParts,
            ContractData contractData,
            ContractPublicNotes contractPublicNotes,
            ContractPrivateNotes contractPrivateNotes
    ) {

        this.contractParts = contractParts;
        this.contractData = contractData;
        this.contractPublicNotes = contractPublicNotes;
        this.contractPrivateNotes = contractPrivateNotes;
    }

    public ContractFullDataDTO retrieveContractFullData(){

        String quoteAccountCode = contractParts.getSelectedCCC() == null ? "" : contractParts.getSelectedCCC().getCccInss();

        ContractJsonData contractJsonData = ContractJsonData.create()
                .withIdentificationContractNumberINEM(null)
                .withDaysOfWeekToWork(contractData.getDaysOfWeekToWork().toString())
                .withWeeklyWorkHours(contractData.getHoursWorkWeek())
                .withNotesForContractManager(contractPublicNotes.getPublicNotes())
                .withPrivateNotes(contractPrivateNotes.getPrivateNotes())
                .withLaborCategory(contractData.getLaborCategory())
                .withContractType(contractData.getContractType().getContractCode())
                .withFullPartialWorkDay(contractData.getFullPartialWorkDay())
                .withWorkerId(contractParts.getSelectedEmployee().getIdpersona())
                .withQuoteAccountCode(quoteAccountCode)
                .withClientGMId(contractParts.getSelectedEmployer().getId())
                .build();

        Integer variationTypeId;
        ContractTypeDTO contractTypeDTO = contractData.getContractType();
        if(contractTypeDTO.getAdminPartnerSimilar()){
            variationTypeId = ContractParameters.INITIAL_CONTRACT_ADMIN_PARTNER_SIMILAR;

        }else if(contractTypeDTO.getSurrogate()){
            variationTypeId = ContractParameters.INITIAL_CONTRACT_SURROGATE_CONTRACT;
        }else{
            variationTypeId = ContractParameters.ORDINARY_INITIAL_CONTRACT;
        }

        ContractNewVersionDTO initialContractDTO = ContractNewVersionDTO.create()
                .withVariationType(variationTypeId)
                .withStartDate(contractData.getDateFrom())
                .withExpectedEndDate(contractData.getDateTo())
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractJsonData(contractJsonData)
                .build();

        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsController.findTypesContractVariationsById(variationTypeId);

        return ContractFullDataDTO.create()
                .withEmployer(contractParts.getSelectedEmployer())
                .withEmployee(contractParts.getSelectedEmployee())
                .withInitialContractDate(contractData.getDateFrom())
                .withContractNewVersionDTO(initialContractDTO)
                .withContractType(contractData.getContractType())
                .withTypesContractVariationsDTO(typesContractVariationsDTO)
                .build();
    }
}
