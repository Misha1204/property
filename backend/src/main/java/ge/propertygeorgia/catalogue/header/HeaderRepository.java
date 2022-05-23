package ge.propertygeorgia.catalogue.header;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeaderRepository  extends JpaRepository<Header, Long> {
}
