package com.gokul.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gokul.entity.Student;
@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

	Student findByName(String name);
	@Query("select s from Student s where s.id=?1")
 	Optional<Student> findById(int id);

}
