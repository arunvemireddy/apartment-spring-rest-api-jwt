package com.example.Apartment.ServiceImpl;

import com.example.Apartment.DTO.UserLoginDTO;
import com.example.Apartment.Dao.UserRepository;
import com.example.Apartment.Entity.UserLogin;
import com.example.Apartment.Service.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.BiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author arun vemireddy
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Integer EXPIRE_MINS = 5;

    @Autowired
    private UserRepository userRepository;

    private LoadingCache<String, Integer> otpCache;

    public UserServiceImpl(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Override
    public String updateUserDetails(String userName, UserLoginDTO userLoginDTO) {
        Optional<UserLogin> userLogin=userRepository.findByUsername(userName);
        UserLogin userLogin1=userLogin.get();
        userLogin1.setName(userLoginDTO.getEname());
        userLogin1.setRoles(userLoginDTO.getRoles());
        userRepository.save(userLogin1);
        return "updated";
    }

    public Map<String,String> generateOTP(String key){
        Optional<UserLogin> userLogin=userRepository.findByUsername(key);
        UserLogin userLogin1=userLogin.get();
        int otp=0;
        String email=null;
        if(!userLogin.isEmpty()){
            email=userLogin1.getEmail();
            Random random = new Random();
            otp = 100000 + random.nextInt(900000);
        }
        Map<String,String> map = new HashMap<>();
        map.put("otp",String.valueOf(otp));
        map.put("email",email);
        otpCache.put(key,otp);
        return map;
    }

    public Boolean validateOtp(String key,String otp1)  {
        try{
        int otp=otpCache.get(key);
        int otp2=Integer.parseInt(otp1);
        if(otp==otp2){
            return true;
        }else {
           return false;
        }
        }catch (Exception exception){
            return false;
        }
    }
}
