package com.gokul.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.gokul.dao.StudentDao;
import com.gokul.entity.Student;

@Service
@Transactional
public class StudentServiceImp implements StudentService,UserDetailsService
{

	@Autowired
	StudentDao dao;
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		Student d=dao.findByName(name);
 		return new UserPrincipal(d);
	}
	@Override
	public List<Student> all() {
 		return dao.findAll();
	}
	@Override
	public Student save(Student s) {
		// TODO Auto-generated method stub
		return dao.save(s);
	}
	 
	@Override
	public  Optional<Student> getById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}
	@Override
	public Student find(String name) {
		// TODO Auto-generated method stub
		return dao.findByName(name);
	}
	@Override
	public void delete(int id) {
		
		dao.deleteById(id);
	}
	@Override
	public void update(Student s) {
		   dao.save(s);	
	}

 
}
