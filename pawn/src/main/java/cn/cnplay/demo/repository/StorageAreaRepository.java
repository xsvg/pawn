package cn.cnplay.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cnplay.demo.model.StorageArea;

public interface StorageAreaRepository extends JpaRepository<StorageArea, String> {
	
}
