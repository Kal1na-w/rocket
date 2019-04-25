package ua.od.atomspace.rocket.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.User;

import java.util.List;
import java.util.Map;

public interface NativeQueryRepository extends CrudRepository<User,Long> {
    @Query(value = "SELECT * FROM course",nativeQuery = true)
    List<Map<String,String>> userProgress();
}
