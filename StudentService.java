package com.gokul.service;

import java.util.List;
import java.util.Optional;

import com.gokul.entity.Student;

public interface StudentService {

	List<Student> all();

	Student save(Student s);

 
	Optional<Student> getById(int id);

	Student find(String name);

	void delete(int id);

	void update(Student s);

}
