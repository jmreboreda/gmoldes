package gmoldes.domain.contract_schedule.dto;

import gmoldes.domain.contractjsondata.ContractDayScheduleJsonData;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContractScheduleDTO {

    private Integer id;
    private Integer contractNumber;
    private ContractScheduleJsonData contractScheduleJsonData;

    public ContractScheduleDTO(Integer id,
                               Integer contractNumber,
                               ContractScheduleJsonData contractScheduleJsonData) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.contractScheduleJsonData = contractScheduleJsonData;
    }

    public Integer getId() {
        return id;
    }

    public Integer getContractNumber() {
        return contractNumber;
    }

    public ContractScheduleJsonData getContractScheduleJsonData() {
        return contractScheduleJsonData;
    }

    public static ContractScheduleDTOBuilder create() {
        return new ContractScheduleDTOBuilder();
    }

    public static class ContractScheduleDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private ContractScheduleJsonData contractScheduleJsonData;

        public ContractScheduleDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractScheduleDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractScheduleDTOBuilder withContractScheduleJsonData(ContractScheduleJsonData contractScheduleJsonData) {
            this.contractScheduleJsonData = contractScheduleJsonData;
            return this;
        }

        public ContractScheduleDTO build() {
            return new ContractScheduleDTO(this.id, this.contractNumber, this.contractScheduleJsonData);
        }
    }
}
