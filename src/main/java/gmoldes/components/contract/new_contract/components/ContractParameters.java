package gmoldes.components.contract.new_contract.components;

public class ContractParameters {

    public static final Integer MAXIMUM_NUMBER_OF_EXTENSIONS_OF_A_CONTRACT = 1;
    public static final Long NUMBER_MONTHS_MAXIMUM_DURATION_INITIAL_CONTRACT_PLUS_ITS_EXTENSIONS = 12L;

    // (The sum of the days of three normal years plus those of a leap year divided by the 48 months of the four years = 30.4375 days of average
    public static final Double AVERAGE_OF_DAYS_IN_A_MONTH = (3.0 * 365.0 + 366.0)/48.0;

}
