package credit.advisory.repository;

import credit.advisory.model.Application;

import java.math.BigDecimal;
import java.util.Optional;

public interface CustomApplicationRepository {
    Optional<Application> findOldestNewApplicationByAmountRange(BigDecimal start, BigDecimal end);
    Optional<Application> findOldestNewApplicationByAmountStartingFrom(BigDecimal start);
}
