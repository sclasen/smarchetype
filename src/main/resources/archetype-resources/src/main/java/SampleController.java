package com.vmforce.samples;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vmforce.samples.dao.SampleDogOwnerDAO;
import com.vmforce.samples.entity.SampleDogOwner;

@Controller
public class SampleController {
	
	@Inject
	private SampleDogOwnerDAO dao; 
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String appRoot() {
		return "redirect:/secure/dogOwners";
	}
	
	@RequestMapping(value="/secure/dogOwners", method=RequestMethod.GET)
	public String listDogOwners (Model model) {
		List<SampleDogOwner> dogOwners = dao.getAllDogOwners();
		model.addAttribute("dogOwners", dogOwners);
		
		return "dogOwners";
	}
	
	@RequestMapping(value="/logoutSuccess", method=RequestMethod.GET)
	public String logoutSuccess () {
		return "logoutSuccess";
	}
}
