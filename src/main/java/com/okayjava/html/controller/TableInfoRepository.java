package com.okayjava.html.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TableInfoRepository extends JpaRepository<TableInfo, String> {

    @Query(value = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'", nativeQuery = true)
    List<String> findAllTableNames();
}
