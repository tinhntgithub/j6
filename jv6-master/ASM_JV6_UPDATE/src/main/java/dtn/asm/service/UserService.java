package dtn.asm.service;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dtn.asm.dao.AccountDAO;
import dtn.asm.dao.AuthoritiesDAO;
import dtn.asm.entity.Accounts;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	SessionService sessionService;

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	AuthoritiesDAO authoritiesDAO;

	@Autowired
	BCryptPasswordEncoder pe;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		Accounts accounts = null;
		String pass = null;
		String[] roles = null;
		try {
			accounts = accountDAO.findByIdAndActiveTrue(username, true).get();
			pass = accounts.getPassword();
			roles = accounts.getAuthorities().stream().map(map -> map.getRoleId().getId()).collect(Collectors.toList())
					.toArray(new String[0]);
			UserDetails userDetails = User.withUsername(username).password(pe.encode(pass)).roles(roles).build();
			return userDetails;
		} catch (Exception e) {
			if (accounts == null) {
				System.out.println("[==========| Username: " + username + " not found in database! |==========]");
			}
			if (pass == null) {
				System.out.println("[==========| Passwords: Value is null! |==========]");
			}
			if (roles == null) {
				System.out.println("[==========| Roles: Value is null! |==========]");
			}
			throw new UsernameNotFoundException(null);
		}
	}

}
