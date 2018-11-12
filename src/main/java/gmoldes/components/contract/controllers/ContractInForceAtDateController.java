package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ContractInForceAtDateController {

    private ContractManager contractManager = new ContractManager();

    public List<ContractNewVersionDTO> findAllContractNewVersionByContractNumber(Integer contractNumber){

        List<ContractNewVersionDTO> contractInForceDTOList = new ArrayList<>();

        ContractNewVersionDTO contractNewVersionDTO = contractManager.findInitialContractByContractNumber(contractNumber);
        ContractNewVersionDTO contractNewVersionInitialDTO = ContractNewVersionDTO.create()
                .withId(contractNewVersionDTO.getId())
                .withContractNumber(contractNewVersionDTO.getContractNumber())
                .withVariationType(contractNewVersionDTO.getVariationType())
                .withStartDate(Date.valueOf(contractNewVersionDTO.getStartDate().toString()))
                .withExpectedEndDate((Date.valueOf(contractNewVersionDTO.getExpectedEndDate().toString())))
                .withEndingDate(Date.valueOf(contractNewVersionDTO.getEndingDate().toString()))
                .withContractJsonData(contractNewVersionDTO.getContractJsonData())
                .build();
        contractInForceDTOList.add(contractNewVersionInitialDTO);

        List<ContractVariationDTO> contractVariationDTOList  = contractManager.findAllContractVariationByContractNumber(contractNumber);
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
            ContractNewVersionDTO contractNewVersionVariationDTO = ContractNewVersionDTO.create()
                    .withId(contractVariationDTO.getId())
                    .withContractNumber(contractVariationDTO.getContractNumber())
                    .withVariationType(contractVariationDTO.getVariationType())
                    .withStartDate(Date.valueOf(contractVariationDTO.getStartDate().toString()))
                    .withExpectedEndDate((Date.valueOf(contractVariationDTO.getExpectedEndDate().toString())))
                    .withEndingDate(Date.valueOf(contractVariationDTO.getEndingDate().toString()))
                    .withContractJsonData(contractVariationDTO.getContractJsonData())
                    .build();
            contractInForceDTOList.add(contractNewVersionVariationDTO);
        }

        return contractInForceDTOList;
    }

    public List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

    private ContractNewVersionDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractManager.findInitialContractByContractNumber(contractNumber);
    }

    private List<ContractVariationDTO> findContractVariationByContractNumber(Integer contractNumber){

        return contractManager.findAllContractVariationByContractNumber(contractNumber);
    }
}
