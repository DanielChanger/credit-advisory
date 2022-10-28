package credit.advisory.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "advisors")
@Getter
@Setter
public class Advisor extends SystemUser {

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "advisor")
    @Setter(AccessLevel.PRIVATE)
    private List<Application> applications = new ArrayList<>();

    public boolean hasAssignedApplication() {
        return applications.stream().anyMatch(Application::isAssigned);
    }

    public void assignApplication(Application application) {
        application.assignToAdvisor(this);
        applications.add(application);
    }

    public enum Role {
        ASSOCIATE, PARTNER, SENIOR
    }
}