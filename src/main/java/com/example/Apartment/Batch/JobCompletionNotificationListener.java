package com.example.Apartment.Batch;

import com.example.Apartment.Dao.OwnerDetailsDao;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.OwnerDetails;
import com.example.Apartment.Entity.UserLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author arun vemireddy
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerDetailsDao ownerDetailsDao;

    @Autowired
    private BCryptPasswordEncoder pwdencoder;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
           UserLogin userLogin = new UserLogin();
           userLogin.setName("arun");
           userLogin.setUsername("arun");
           userLogin.setPassword(pwdencoder.encode("arun"));
           Set<String> set = new HashSet<>();
           set.add("Admin");
           userLogin.setRoles(set);
           userLogin.setEmail("vemireddyarun0701@gmail.com");
           userRepository.save(userLogin);

           List<OwnerDetails> ownerDetailsList = ownerDetailsDao.findAll();
            for (OwnerDetails ownerDetails:ownerDetailsList
                 ) {
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
