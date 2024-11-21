package com.titan.mbrDashboard.security;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService implements AuthenticationProvider {

	// Logger logger = LoggerFactory.getLogger(this.getClass());
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("in authentication service");
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		// logger.info("authentication user ={} ", username);

		logger.info(username);

		if (authenticateWithLdap(username, password)) {
			final List<GrantedAuthority> grantedAuths = new ArrayList<>();
			// grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			final UserDetails principal = new User(username, password, grantedAuths);
			Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
			return auth;
		} else {
			logger.info("user not authenticated");
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

//	public boolean authenticateWithLdap(String username, String password) {
//		String ldapUserName = username + "@titan.com";
//		String url = "ldap://172.25.7.23:389";
//
////		String url = "ldap://172.25.7.23:636";
//
//		Hashtable env = new Hashtable();
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.PROVIDER_URL, url);
//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
//		env.put(Context.SECURITY_PRINCIPAL, ldapUserName);
//		env.put(Context.SECURITY_CREDENTIALS, password);
//
//		try {
//			DirContext ctx = new InitialDirContext(env);
//			if (ctx != null) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}
//
//}
	
	public boolean authenticateWithLdap(String username, String password) {

		 
		//String ldapUserName = "masinenikrishnasai@titan.co.in";
		String ldapUserName = username;
		// String ldapUserName = username + "@titan.com";
		// String url = "ldap://172.25.7.23:389";
 
//		String url = "ldap://172.25.7.23:636";
 
		String url = "ldaps://TCLBLRCORPDC04.titan.com:636";
 
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ldapUserName);
		env.put(Context.SECURITY_CREDENTIALS, password);
 
		try {
			DirContext ctx = new InitialDirContext(env);
			if (ctx != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
 
	}
 
}
