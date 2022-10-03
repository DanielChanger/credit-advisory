package credit.advisory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applicants")
@Getter
@Setter
public class Applicant extends User {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "ssn", nullable = false)
    private String ssn;
    @Embedded
    private Address address;
    @ElementCollection
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @Embeddable
    @Getter
    @Setter
    public static class Address {
        @Column(name = "city", nullable = false)
        private String city;
        @Column(name = "street", nullable = false)
        private String street;
        @Column(name = "number", nullable = false)
        private String number;
        @Column(name = "zip", nullable = false)
        private String zip;
        @Column(name = "apt", nullable = false)
        private String apt;
    }

    @Embeddable
    @Getter
    @Setter
    @Table(name = "phone_numbers")
    public static class PhoneNumber {
        private String number;
        private Type type;
        public enum Type {
            HOME, WORK, MOBILE
        }
    }
}
