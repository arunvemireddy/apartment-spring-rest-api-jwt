package com.example.Apartment.Batch;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;

/**
 * @author arun vemireddy
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Value("${app.name}")
	private String admin_name;
	@Value("${app.username}")
	private String admin_username;
	@Value("${app.password}")
	private String admin_password;
	@Value("${app.email}")
	private String admin_email;
	@Value("${app.role}")
	private String admin_role;

	private final EntityManager entityManager;

	private UserRepository userRepository;

	private OwnerDetailsDao ownerDetailsDao;

	private BCryptPasswordEncoder pwdencoder;

	@Autowired
	public JobCompletionNotificationListener(EntityManager entityManager, UserRepository userRepository,
			OwnerDetailsDao ownerDetailsDao, BCryptPasswordEncoder pwdencoder) {
		this.entityManager = entityManager;
		this.userRepository = userRepository;
		this.ownerDetailsDao = ownerDetailsDao;
		this.pwdencoder = pwdencoder;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			UserLogin userLogin = new UserLogin();

			userLogin.setName(admin_name);
			userLogin.setUsername(admin_username);
			userLogin.setPassword(pwdencoder.encode(admin_password));
			userLogin.setEmail(admin_email);
			Set<String> set = new HashSet<>();
			set.add(admin_role);
			userLogin.setRoles(set);

			userRepository.save(userLogin);

			List<OwnerDetails> ownerDetailsList = ownerDetailsDao.findAll();
			for (OwnerDetails ownerDetails : ownerDetailsList) {
				try {
					byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/P3.jpg"));
					ownerDetails.setImage(Base64.getEncoder().encodeToString(bytes));
					ownerDetailsDao.save(ownerDetails);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
