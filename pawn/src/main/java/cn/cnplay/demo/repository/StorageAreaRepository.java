package cn.cnplay.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.cnplay.demo.model.StorageArea;

public interface StorageAreaRepository extends JpaRepository<StorageArea, String> {
	
	@Query("from StorageArea d where d.parentArea.id = ?1 and d.deleted = 0")
	public List<StorageArea> findChildren(String id);
}
