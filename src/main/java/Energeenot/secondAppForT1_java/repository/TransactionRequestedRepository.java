package Energeenot.secondAppForT1_java.repository;

import Energeenot.secondAppForT1_java.model.TransactionRequested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRequestedRepository extends JpaRepository<TransactionRequested, Integer> {

    @Query("SELECT t FROM TransactionRequested t " +
            "WHERE t.clientId = :clientId " +
            "AND t.accountId = :accountId " +
            "AND t.timestamp >= :startTime " +
            "AND t.timestamp < :endTime " +
            "ORDER BY t.timestamp DESC")
    List<TransactionRequested> findRecentTransactions(@Param("clientId") String clientId,
                                                      @Param("accountId") String accountId,
                                                      @Param("startTime") LocalDateTime startTime,
                                                      @Param("endTime") LocalDateTime endTime);

}
