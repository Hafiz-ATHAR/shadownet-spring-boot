package com.shadow.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shadow.net.repository.PacketRepository;

@Controller
public class PacketController {

	@Autowired
	PacketRepository packetRepo;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("pronum", packetRepo.countByMaliciousType("PROBING"));
		model.addAttribute("malnum", packetRepo.countByMaliciousType("MALWARE"));
		model.addAttribute("webnum", packetRepo.countByMaliciousType("WEB"));
		model.addAttribute("sipnum", packetRepo.countByMaliciousType("SIP"));
		model.addAttribute("sshnum", packetRepo.countByMaliciousType("SSH"));
		model.addAttribute("dbnum", packetRepo.countByMaliciousType("DB"));

		
		return "index";
	}

	@RequestMapping(value = "/malicious-ips", method = RequestMethod.GET)
	public String maliciousips(Model model) {
		model.addAttribute("probing", packetRepo.findByMaliciousTypeOrderByTimeDesc("PROBING"));
		model.addAttribute("malware", packetRepo.findByMaliciousTypeOrderByTimeDesc("MALWARE"));
		model.addAttribute("web", packetRepo.findByMaliciousTypeOrderByTimeDesc("WEB"));
		model.addAttribute("sip", packetRepo.findByMaliciousTypeOrderByTimeDesc("SIP"));
		model.addAttribute("ssh", packetRepo.findByMaliciousTypeOrderByTimeDesc("SSH"));
		model.addAttribute("db", packetRepo.findByMaliciousTypeOrderByTimeDesc("DB"));

		return "malicious-ips";

	}

	@RequestMapping(value = "/urls", method = RequestMethod.GET)
	public String urls(Model model) {
		model.addAttribute("urls", packetRepo.findByUrlCheckOrderByTimeDesc(true));
		return "urls";

	}

	@RequestMapping(value = "/hashes", method = RequestMethod.GET)
	public String hashes(Model model) {
		model.addAttribute("hashes", packetRepo.findByHashStatusOrderByTimeDesc(true));

		return "hashes";

	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Model model) {
		packetRepo.deleteAll();

		return "redirect:/";

	}

}