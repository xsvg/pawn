package cn.cnplay.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.cnplay.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{

	List<User> findByNameAndAddress(String name, String address);

	@Query(value = "from User u where u.name=:name")
	List<User> findByName1(@Param("name") String name);
//
//	@Query(value = "select * from #{#entityName} u where u.name=?1", nativeQuery = true)
//	List<User> findByName2(String name);

	List<User> findByName(String name);
	
	List<User> findByUsername(String username);
}
