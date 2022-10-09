package credit.advisory.service;

import credit.advisory.model.Advisor;
import credit.advisory.model.Application;
import credit.advisory.repository.AdvisorRepository;
import credit.advisory.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CreditAdvisoryService {

    public static final BigDecimal FIFTY_GRANDS = BigDecimal.valueOf(50_000);
    public static final BigDecimal TEN_GRANDS = BigDecimal.valueOf(10_000);
    private final ApplicationRepository applicationRepository;
    private final AdvisorRepository advisorRepository;

    @Transactional
    public Application assignApplication(Long id) {
        Advisor advisor = getAdvisor(id);
        checkNoApplicationIsAssigned(id, advisor);
        Application application = getApplication(id, advisor);
        advisor.assignApplication(application);
        return application;
    }

    private Application getApplication(Long id, Advisor advisor) {
        Optional<Application> applicationOptional = switch (advisor.getRole()) {
            case ASSOCIATE -> applicationRepository.findOldestNewApplicationByAmountRange(BigDecimal.ONE, TEN_GRANDS);
            case PARTNER -> applicationRepository.findOldestNewApplicationByAmountRange(TEN_GRANDS, FIFTY_GRANDS);
            case SENIOR -> applicationRepository.findOldestNewApplicationByAmountStartingFrom(FIFTY_GRANDS);
        };
        return applicationOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No application to assign is found for advisor with %s id".formatted(id)
                )
        );
    }

    private Advisor getAdvisor(Long id) {
        return advisorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Advisor with %s id is not found".formatted(id)));
    }

    private static void checkNoApplicationIsAssigned(Long id, Advisor advisor) {
        if (advisor.hasAssignedApplication()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Advisor with %s id already has application assigned"
                    .formatted(id));
        }
    }
}
