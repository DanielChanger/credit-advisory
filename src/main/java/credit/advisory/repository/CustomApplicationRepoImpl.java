package credit.advisory.repository;

import credit.advisory.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomApplicationRepoImpl implements CustomApplicationRepo {
    private final EntityManager entityManager;

    @Override
    public Optional<Application> findOldestNewApplicationByAmountRange(BigDecimal start, BigDecimal end) {
        return Optional.ofNullable(entityManager.createQuery("select a from Application a " +
                                "where a.status = :status " +
                                "and a.amount between :start and :end", Application.class)
                .setParameter("status", "NEW")
                .setParameter("start", start)
                .setParameter("end", end)
                .setMaxResults(1)
                .getSingleResult());
    }

    @Override
    public Optional<Application> findOldestNewApplicationByAmountRange(BigDecimal start) {
        return Optional.ofNullable(entityManager.createQuery("select a from Application a " +
                        "where a.status = :status " +
                        "and a.amount >= :start", Application.class)
                .setParameter("status", "NEW")
                .setParameter("start", start)
                .setMaxResults(1)
                .getSingleResult());
    }
}
