package gmoldes.mappers;

import gmoldes.domain.dto.ContractDTO;
import gmoldes.persistence.vo.ContractVO;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class MapperContractVODTO {

    public ContractDTO mapContractVODTO(ContractVO contractVO) {

        Set<DayOfWeek> daysOfWorkSet = mapDaysOfWeekToWorkVODTO(contractVO);

        ContractDTO contractDTO = ContractDTO.create()
                .withId(contractVO.getId())
                .withContractNumber(contractVO.getNumcontrato())
                .withVariationNumber(contractVO.getNumvariacion())
                .withVariationType(contractVO.getTipovariacion())
                .withClientGMId(contractVO.getIdcliente_gm())
                .withClientGMName(contractVO.getClientegm_name())
                .withQuoteAccountCode(contractVO.getContrato_ccc())
                .withWorkerId(contractVO.getIdtrabajador())
                .withWorkerName(contractVO.getTrabajador_name())
                .withLaborCategory(contractVO.getCategoria())
                .withWeeklyWorkHours(contractVO.getJor_trab())
                .withDaysOfWeekToWork(daysOfWorkSet)
                .withFullPartialWorkday(contractVO.getJor_tipo())
                .withTypeOfContract(contractVO.getTipoctto())
                .withDateFrom(contractVO.getF_desde())
                .withDateTo(contractVO.getF_hasta())
                .withIdentificationContractNumberINEM(contractVO.getId_ctto_inem())
                .withCurrentContract(contractVO.getEnvigor())
                .withNotesForManager(contractVO.getNotas_gestor())
                .withPrivateNotes(contractVO.getNotas_privadas())
                .withIndefiniteOrTemporalContract(contractVO.getDuracion())
                .withSurrogateContract(contractVO.getSubrogacion())
                .withQuoteDataReportIDC(contractVO.getIdc())
                .withEndOfContractNotice(contractVO.getPreavisofin())
                .build();
        return contractDTO;
    }

    private Set<DayOfWeek> mapDaysOfWeekToWorkVODTO(ContractVO contractVO) {
        String daysOfWork = contractVO.getJor_trab_dias();
        DayOfWeek[] daysOfWeek = DayOfWeek.values();

        Set<DayOfWeek> daysOfWeekSet = new HashSet<>();

        for (int i = 0; i < daysOfWork.length(); i++) {
            if (daysOfWork.substring(i, i + 1).equals("1")) {
                daysOfWeekSet.add(daysOfWeek[i]);
            }
        }

        /** Ordenar y obtener el nombre local de los días de la semana
        * Set<DayOfWeek> sortedDaysOfWeekSet = new TreeSet<DayOfWeek>(daysOfWeekSet);
        * Locale locale = Locale.getDefault();
        * for(DayOfWeek dayOfWeek : sortedDaysOfWeekSet) {
        *     System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, locale));
        * }
        */

        return daysOfWeekSet;
    }
}
