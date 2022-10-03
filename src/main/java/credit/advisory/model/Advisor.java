package credit.advisory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "advisors")
@Getter
@Setter
public class Advisor extends User {

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "advisor")
    private Application application;

    public Optional<Application> getApplication() {
        return Optional.ofNullable(application);
    }

    public void setApplication(Application application) {
        this.application = application;
        application.setAdvisor(this);
        application.setStatus(Application.Status.ASSIGNED);
        application.setAssignedAt(LocalDateTime.now());
    }

    public enum Role {
        ASSOCIATE, PARTNER, SENIOR
    }
}