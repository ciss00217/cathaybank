package tw.com.cathaybank.Demo.domain.currency.gateway.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.model.CurrencyMapping;

/** 幣別維護Repository */
@Repository
public interface CurrencyMappingRepository extends JpaRepository<CurrencyMapping, String> {}
