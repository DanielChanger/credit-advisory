package credit.advisory.repository;

import credit.advisory.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long>, CustomApplicationRepo {

}
