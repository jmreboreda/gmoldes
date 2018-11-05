package gmoldes.components.contract.new_contract.mapper;

import gmoldes.domain.contract.dto.OldContractToSaveDTO;
import gmoldes.components.contract.persistence.vo.ContractVO;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.Set;

public class MapperOldContractToSaveDTOVO {

    private static final String IS_WORK_DAY = "1";
    private static final String IS_NOT_WORK_DAY = "0";


    public ContractVO mapOldContractToSaveDTOVO(OldContractToSaveDTO oldContractToSaveDTO) {

        Date dateTo = null;
        if(oldContractToSaveDTO.getDateTo() != null){
            dateTo = Date.valueOf(oldContractToSaveDTO.getDateTo());
        }

        Date endOfContractNotice = null;
        if(oldContractToSaveDTO.getEndOfContractNotice() != null){
            endOfContractNotice = Date.valueOf(oldContractToSaveDTO.getEndOfContractNotice());
        }

        ContractVO contractVO = new ContractVO();
        contractVO.setNumcontrato(oldContractToSaveDTO.getContractNumber());
        contractVO.setNumvariacion(oldContractToSaveDTO.getVariationNumber());
        contractVO.setTipovariacion(oldContractToSaveDTO.getVariationType());
        contractVO.setIdcliente_gm(oldContractToSaveDTO.getClientGMId());
        contractVO.setClientegm_name(oldContractToSaveDTO.getClientGMName());
        contractVO.setContrato_ccc(oldContractToSaveDTO.getQuoteAccountCode());
        contractVO.setIdtrabajador(oldContractToSaveDTO.getWorkerId());
        contractVO.setTrabajador_name(oldContractToSaveDTO.getWorkerName());
        contractVO.setCategoria(oldContractToSaveDTO.getLaborCategory());
        contractVO.setJor_trab(oldContractToSaveDTO.getWeeklyWorkHours());
        contractVO.setJor_trab_dias(mapDaysOfWeekToWorkDTOVO(oldContractToSaveDTO.getDaysOfWeekToWork()));
        contractVO.setJor_tipo(oldContractToSaveDTO.getFullPartialWorkday());
        contractVO.setTipoctto(oldContractToSaveDTO.getContractType());
        contractVO.setF_desde(Date.valueOf(oldContractToSaveDTO.getDateFrom()));
        contractVO.setF_hasta(dateTo);
        contractVO.setEnvigor(oldContractToSaveDTO.getContractInForce());
        contractVO.setNotas_gestor(oldContractToSaveDTO.getNotesForManager());
        contractVO.setNotas_privadas(oldContractToSaveDTO.getPrivateNotes());
        contractVO.setIdc(null);
        contractVO.setPreavisofin(endOfContractNotice);

        return contractVO;
    }

    private String mapDaysOfWeekToWorkDTOVO(Set<DayOfWeek> daysOfWeek) {
        String daysOfWork = "";
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
