package com.elimarAfonso.auth;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.elimarAfonso.auth.Entity.Permission;
import com.elimarAfonso.auth.Entity.User;
import com.elimarAfonso.auth.Repository.PermissionRepository;
import com.elimarAfonso.auth.Repository.UserRepository;



@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
			BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, permissionRepository, passwordEncoder);
		};

	}

	private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository,
			BCryptPasswordEncoder passwordEncoder) {

		Permission permission = null;
		Permission findPermission = permissionRepository.findByDescription("Admin");
		if (findPermission == null) {
			permission = new Permission();
			permission.setDescription("Admin");
			permission = permissionRepository.save(permission);
		} else {
			permission = findPermission;
		}

		User admin = new User();
		admin.setUserName("elimar_afonso");
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setEnabled(true);
		admin.setPassWord(passwordEncoder.encode("elimarafonso"));
		admin.setPermissions(Arrays.asList(permission));

		User find = userRepository.findByUser("elimar_afonso");
		if (find == null) {
			userRepository.save(admin);
		}
	}

}