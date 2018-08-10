package cn.cnplay.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.cnplay.demo.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
	
	@Query("from Department d where d.parent.id = ?1 and d.deleted = 0")
	public List<Department> findChildren(String id);
}
