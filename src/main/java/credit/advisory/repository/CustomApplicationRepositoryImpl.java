package credit.advisory.repository;

import credit.advisory.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Application> findOldestNewApplicationByAmountRange(BigDecimal start, BigDecimal end) {
        return entityManager.createQuery("select a from Application a " +
                        "where a.status = 'NEW' " +
                        "and a.amount between :start and :end", Application.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<Application> findOldestNewApplicationByAmountStartingFrom(BigDecimal start) {
        return entityManager.createQuery("select a from Application a " +
                        "where a.status = 'NEW' " +
                        "and a.amount >= :start", Application.class)
                .setParameter("start", start)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }
}
