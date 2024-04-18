package no.ntnu.idi.stud.savingsapp.controller;

import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling user related requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
@EnableAutoConfiguration
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;
}
