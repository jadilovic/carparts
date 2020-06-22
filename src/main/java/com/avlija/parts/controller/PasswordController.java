package com.avlija.parts.controller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.avlija.parts.form.SampleInputs;
import com.avlija.parts.model.User;
import com.avlija.parts.service.EmailService;
import com.avlija.parts.service.UserService;

@Controller
public class PasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// Display forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		SampleInputs sampleInputs = new SampleInputs();
		ModelAndView model = new ModelAndView();
		model.addObject("sampleInputs", sampleInputs);
		model.setViewName("user/forgotPassword");
		return model;
    }
    
    // Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, SampleInputs sampleInputs, HttpServletRequest request) {

		// Lookup user in database by e-mail
		User user = userService.findUserByEmail(sampleInputs.getEmail());

		if (user == null) {
			modelAndView.addObject("message", "We didn't find an account for that e-mail address.");
		} else {
			
			// Generate random 36-character string token for reset password 
			// User user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.updateUser(user);

			String appUrl = request.getScheme() + "://" + request.getServerName();
			
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("j.adilovic@gmail.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
					+ "/reset?token=" + user.getResetToken());
			
			emailService.sendEmail(passwordResetEmail);

			// Add success message to view
			modelAndView.addObject("message", "A password reset link has been sent to " + sampleInputs.getEmail()
										+ ". Go to your email and click on the link.");
		}

		modelAndView.setViewName("user/login");
		return modelAndView;

	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		
		Optional<User> user = userService.findUserByResetToken(token);

		if (user.isPresent()) { // Token found in DB
			modelAndView.addObject("token", token);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link. Token not found in the database");
		}
		modelAndView.addObject("sampleInputs", new SampleInputs());
		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, SampleInputs sampleInputs) {

		// Find the user associated with the reset token
		Optional<User> user = userService.findUserByResetToken(sampleInputs.getToken());

		// This should always be non-null but we check just in case
		if (user.isPresent()) {
			
			User resetUser = user.get(); 
            
			// Set new password    
            resetUser.setPassword(bCryptPasswordEncoder.encode(sampleInputs.getPassword()));
            
			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			userService.updateUser(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			modelAndView.addObject("message", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("user/login");
			return modelAndView;
			
		} else {
			modelAndView.addObject("error", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");	
		}
		
		return modelAndView;
   }
   
    // Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}
}