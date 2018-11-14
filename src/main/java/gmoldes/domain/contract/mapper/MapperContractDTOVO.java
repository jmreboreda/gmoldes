package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractVO;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.Set;

public class MapperContractDTOVO {

    private static final String IS_WORK_DAY = "1";
    private static final String IS_NOT_WORK_DAY = "0";


    public ContractVO mapContractDTOVO(ContractDTO contractDTO) {

        ContractVO contractVO = new ContractVO();
        contractVO.setNumcontrato(contractDTO.getContractNumber());
        contractVO.setNumvariacion(contractDTO.getVariationNumber());
        contractVO.setTipovariacion(contractDTO.getVariationType());
        contractVO.setIdcliente_gm(contractDTO.getClientGMId());
        contractVO.setClientegm_name(contractDTO.getClientGMName());
        contractVO.setContrato_ccc(contractDTO.getQuoteAccountCode());
        contractVO.setIdtrabajador(contractDTO.getWorkerId());
        contractVO.setTrabajador_name(contractDTO.getWorkerName());
        contractVO.setCategoria(contractDTO.getLaborCategory());
        contractVO.setJor_trab(contractDTO.getWeeklyWorkHours());
        contractVO.setJor_trab_dias(mapDaysOfWeekToWorkDTOVO(contractDTO.getDaysOfWeekToWork()));
        contractVO.setJor_tipo(contractDTO.getFullPartialWorkDay());
        contractVO.setTipoctto(contractDTO.getContractType());
        contractVO.setF_desde(Date.valueOf(contractDTO.getDateFrom()));
        contractVO.setF_hasta(Date.valueOf(contractDTO.getDateTo()));
        contractVO.setEnvigor(contractDTO.getContractInForce());
        contractVO.setNotas_gestor(contractDTO.getNotesForManager());
        contractVO.setNotas_privadas(contractDTO.getPrivateNotes());
        contractVO.setIdc(Date.valueOf(contractDTO.getQuoteDataReportIDC()));
        contractVO.setPreavisofin(Date.valueOf(contractDTO.getEndOfContractNotice()));

        return contractVO;
    }

    private String mapDaysOfWeekToWorkDTOVO(Set<DayOfWeek> daysOfWeek) {
        String daysOfWork = null;
        if (daysOfWeek.contains(DayOfWeek.MONDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }
        if (daysOfWeek.contains(DayOfWeek.TUESDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }
        if (daysOfWeek.contains(DayOfWeek.WEDNESDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }
        if (daysOfWeek.contains(DayOfWeek.THURSDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }
        if (daysOfWeek.contains(DayOfWeek.FRIDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }
        if (daysOfWeek.contains(DayOfWeek.SATURDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }
        if (daysOfWeek.contains(DayOfWeek.SUNDAY)) {
            daysOfWork = daysOfWork + IS_WORK_DAY;
        }else{
            daysOfWork = daysOfWork + IS_NOT_WORK_DAY;
        }

        return daysOfWork;
    }
}
