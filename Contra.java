package com.gokul.contra;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.entity.Student;
import com.gokul.filter.JwtVerifier;
import com.gokul.service.StudentService;
import static com.gokul.entity.Roles.*;

@CrossOrigin("*")
@RestController
public class Contra {
	
	@Autowired
	StudentService service;
	@Autowired
	JwtVerifier jwt;
	
	@GetMapping("/all")
	public ResponseEntity<List<Student>> all()
	{
		List<Student> s=service.all();
		
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	@PostMapping("/reg")
	public Student register(@RequestBody Student s)
	{
		String pass=s.getPassword();
		String hash=BCrypt.hashpw(pass,BCrypt.gensalt(12));
		s.setPassword(hash);
		return service.save(s);
	}
	
	
  
	@GetMapping("/log")
	public ResponseEntity<String> log()
	{
		Authentication au=SecurityContextHolder.getContext().getAuthentication();
		if(au!=null)
		{
			SecurityContextLogoutHandler s=new SecurityContextLogoutHandler();
			s.isInvalidateHttpSession();
			s.setClearAuthentication(true);
			System.out.println("hello");
		}
		return new ResponseEntity<>("deleted",HttpStatus.GONE);
	}	
 	@PreAuthorize("hasAnyAuthority('view')")
    @GetMapping("/single/{id}")
    public ResponseEntity<Student> view(@PathVariable int id)
    {
     
    	 String name = null;
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   	 System.out.println("i am  logged "+principal);

    	if (principal !=null) {
    	    name =  (String) principal;
    	    
    	   	 System.out.println("i am name log"+name);

    	  
    	}  


    	 
    	 Optional<Student> data=service.getById(id);
     	 Student cname=data.get();
    	 String check=cname.getName();
     	 System.out.println("i am check "+check);
    	  Student my=service.find(name);
    	  String role=my.getRole();
      	 System.out.println("i am role "+role);
      	 
      	 

    	 if(name.equals(check)||role.equals("ADMIN"))
    	 {
        	 System.out.println("if");

    		 return new ResponseEntity<>(cname,HttpStatus.ACCEPTED);
    	 }
    	 else {
        	 System.out.println("else");

    		 return new ResponseEntity<>(new Student(),HttpStatus.UNAUTHORIZED);
    	 }
    }

 		@PreAuthorize("hasAnyAuthority('delete')")
 		@DeleteMapping("/delete/{id}")
 		public String delete(@PathVariable int id)
 		{
 				service.delete(id);
 				return "Deleted";
 		}
 		 
 		@PreAuthorize("hasAnyAuthority('create')")
 		@PutMapping("/update")
 		public String up(@RequestBody Student s)
 		{
 			String pass=s.getPassword();
 			String hash=BCrypt.hashpw(pass,BCrypt.gensalt(12));
 			s.setPassword(hash);
 			 service.update(s);
 			return "updaed";
 		}

}
