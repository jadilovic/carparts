package com.avlija.parts.form;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }
    
    

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
    	
    	URL resource = this.getClass().getClassLoader().getResource("passay.properties");
    	Properties props = new Properties();
    	try {
			props.load(new FileInputStream(resource.getPath()));
		} catch (FileNotFoundException e) {
			System.out.println("OOOOPSSSSS");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("HOOPSSSOTO");
			e.printStackTrace();
		}

    	MessageResolver resolver = new PropertiesMessageResolver(props);
    	
        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
            // at least 8 characters
            new LengthRule(8, 60),

            // at least one upper-case character
            new CharacterRule(EnglishCharacterData.UpperCase, 1),

            // at least one lower-case character
            new CharacterRule(EnglishCharacterData.LowerCase, 1),

            // at least one digit character
            new CharacterRule(EnglishCharacterData.Digit, 1),

            // at least one symbol (special character)
            new CharacterRule(EnglishCharacterData.Special, 1),

            // no whitespace
            new WhitespaceRule()

        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = messages.stream()
            .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
    }
}
