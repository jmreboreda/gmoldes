package gmoldes.domain.address;

public class Address {

    private String extendedStreetAddress;
    private String postalCode;
    private String location;
    private String municipality;

    public Address() {
    }

    public Address(String extendedStreetAddress,
                   String postalCode,
                   String location,
                   String municipality) {
        this.extendedStreetAddress = extendedStreetAddress;
        this.postalCode = postalCode;
        this.location = location;
        this.municipality = municipality;
    }

    public String getExtendedStreetAddress() {
        return extendedStreetAddress;
    }

    public void setExtendedStreetAddress(String extendedStreetAddress) {
        extendedStreetAddress = extendedStreetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public static Address.AddressBuilder create() {
        return new Address.AddressBuilder();
    }

    public static class AddressBuilder {

        private String extendedStreetAddress;
        private String postalCode;
        private String location;
        private String municipality;

        public Address.AddressBuilder withExtendedStreetAddress(String extendedStreetAddress) {
            this.extendedStreetAddress = extendedStreetAddress;
            return this;
        }

        public Address.AddressBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address.AddressBuilder withName(String location) {
            this.location = location;
            return this;
        }

        public Address.AddressBuilder withNifNie(String municipality) {
            this.municipality = municipality;
            return this;
        }

        public Address build() {
            return new Address(this.extendedStreetAddress, this.postalCode, this.location, this.municipality);
        }
    }

    @Override
    public String toString(){
        String locationToPrint = null;
        if(location != null){
            locationToPrint = getLocation().concat("   ");
        }
        return getExtendedStreetAddress().concat("  ")
                .concat(locationToPrint)
                .concat(getPostalCode()).concat("  ")
                .concat(getMunicipality());
    }
}
