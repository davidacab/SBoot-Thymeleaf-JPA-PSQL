package it.thyjpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.thyjpa.dao.UserDao;
import it.thyjpa.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserDao dao;
	
	@GetMapping
	public String getAll(Model model){
	  model.addAttribute("listaUtenti", dao.getAllUsers());
	  return "index";
	}
	
	@GetMapping("/{email}")
	public List<User> getByEmail(@PathVariable String email) {
		System.out.println(email);
		User user = new User(0L,email,"");
		return dao.findByEmail(user);
	}
	
	@GetMapping("/add")
	public String add(User user) {
		return "add";
	}
	
	@PostMapping
	public String create(User user,Model model) {
		dao.createUser(user);
		model.addAttribute("listaUtenti", dao.getAllUsers());
		return "index";
	}
	
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable("id") Long id,Model model) {
		dao.removeUser(id);
		model.addAttribute("listaUtenti", dao.getAllUsers());
		return "index";
	}
	
	@GetMapping("/editform/{id}")
	public String edit(@PathVariable("id") Long id,Model model) {
		User user = dao.findById(id);
		model.addAttribute("user",user);
		return "update";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@PathVariable("id") Long id,User user,Model model) {
		user=new User(id,user.getEmail(),user.getPassword());
		dao.updateUser(user);
		model.addAttribute("listaUtenti", dao.getAllUsers());
		return "index";
	}
}
