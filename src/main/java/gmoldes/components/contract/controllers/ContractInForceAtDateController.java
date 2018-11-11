package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ContractInForceAtDateController {

    private ContractManager contractManager = new ContractManager();

    public List<ContractNewVersionDTO> findAllContractNewVersionByContractNumber(Integer contractNumber){

        List<ContractNewVersionDTO> contractInForceDTOList = new ArrayList<>();

        InitialContractDTO initialContractDTO = contractManager.findInitialContractByContractNumber(contractNumber);
        ContractNewVersionDTO contractNewVersionInitialDTO = ContractNewVersionDTO.create()
                .withId(initialContractDTO.getId())
                .withContractNumber(initialContractDTO.getContractNumber())
                .withVariationType(initialContractDTO.getVariationType())
                .withStartDate(Date.valueOf(initialContractDTO.getStartDate().toString()))
                .withExpectedEndDate((Date.valueOf(initialContractDTO.getExpectedEndDate().toString())))
                .withEndingDate(Date.valueOf(initialContractDTO.getEndingDate().toString()))
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
                    .build();
            contractInForceDTOList.add(contractNewVersionVariationDTO);
        }

        return contractInForceDTOList;
    }



    public List<InitialContractDTO> contractNewVersionDTOList = new ArrayList<>();

    private InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractManager.findInitialContractByContractNumber(contractNumber);

    }

    private List<ContractVariationDTO> findContractVariationByContractNumber(Integer contractNumber){

        return contractManager.findAllContractVariationByContractNumber(contractNumber);
    }

}
