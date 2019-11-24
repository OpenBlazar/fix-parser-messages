package pl.zankowski.fixparser.messages;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zankowski.fixparser.messages.fix.entity.FixMessageEntity;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<FixMessageEntity, Long> {

    List<FixMessageEntity> findByUserId(Long userId, Pageable pageable);

    int countByUserId(Long userId);

    void deleteAllByUserId(Long userId);

}
