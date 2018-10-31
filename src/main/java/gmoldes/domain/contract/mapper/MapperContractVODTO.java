package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.components.contract.persistence.vo.ContractVO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MapperContractVODTO {

    private static final String IS_WORK_DAY = "1";

    public ContractDTO mapContractVODTO(ContractVO contractVO) {

        Set<DayOfWeek> daysOfWorkSet = mapDaysOfWeekToWorkVODTO(contractVO);

        LocalDate dateTo = null;
        LocalDate dateOfIDC = null;
        LocalDate dateEndOfContractNotice = null;

        if(contractVO.getF_hasta() != null){
            dateTo = contractVO.getF_hasta().toLocalDate();
        }
        if(contractVO.getIdc() != null){
            dateOfIDC = contractVO.getIdc().toLocalDate();
        }
        if(contractVO.getPreavisofin() != null){
            dateEndOfContractNotice = contractVO.getPreavisofin().toLocalDate();
        }

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
                .withDateFrom(contractVO.getF_desde().toLocalDate())
                .withDateTo(dateTo)
                .withIdentificationContractNumberINEM(contractVO.getId_ctto_inem())
                .withContractInForce(contractVO.getEnvigor())
                .withNotesForManager(contractVO.getNotas_gestor())
                .withPrivateNotes(contractVO.getNotas_privadas())
                .withIndefiniteOrTemporalContract(contractVO.getDuracion())
                .withSurrogateContract(contractVO.getSubrogacion())
                .withQuoteDataReportIDC(dateOfIDC)
                .withEndOfContractNotice(dateEndOfContractNotice)
                .build();

        return contractDTO;
    }

    public Set<DayOfWeek> mapDaysOfWeekToWorkVODTO(ContractVO contractVO) {
        String daysOfWork = contractVO.getJor_trab_dias();
        DayOfWeek[] daysOfWeek = DayOfWeek.values();

        Set<DayOfWeek> daysOfWeekSet = new HashSet<>();

        for (int i = 0; i < daysOfWork.length(); i++) {
            if (daysOfWork.substring(i, i + 1).equals(IS_WORK_DAY)) {
                daysOfWeekSet.add(daysOfWeek[i]);
            }
        }

        /** Ordenar y obtener el nombre local de los días de la semana
        Set<DayOfWeek> sortedDaysOfWeekSet = new TreeSet<DayOfWeek>(daysOfWeekSet);
        Locale locale = Locale.getDefault();
        for(DayOfWeek dayOfWeek : sortedDaysOfWeekSet) {
             System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, locale));
        }
         */

        /**
         // Get DayOfWeek from int value
         DayOfWeek dayOfWeek = DayOfWeek.of(1);
         System.out.println("dayOfWeek = " + dayOfWeek);

         // Get DayOfWeek from string value
         dayOfWeek = DayOfWeek.valueOf("SATURDAY");
         System.out.println("dayOfWeek = " + dayOfWeek);

         // Get DayOfWeek of a date object
         LocalDate date = LocalDate.now();
         DayOfWeek dow = date.getDayOfWeek();

         System.out.println("Date  = " + date);
         System.out.println("Dow   = " + dow + "; value = " + dow.getValue());

         // Get DayOfWeek display name in different locale.
         Locale locale = new Locale("id", "ID");
         String indonesian = dow.getDisplayName(TextStyle.SHORT, locale);
         System.out.println("ID = " + indonesian);

         String germany = dow.getDisplayName(TextStyle.FULL, Locale.GERMANY);
         System.out.println("DE = " + germany);

         // Adding number of days to DayOfWeek enum.
         System.out.println("DayOfWeek.MONDAY.plus(4) = " + DayOfWee

        */

        return daysOfWeekSet;
    }
}
