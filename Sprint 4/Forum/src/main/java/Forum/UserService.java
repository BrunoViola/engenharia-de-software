package Forum;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public Conta getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ContaUserDetailsImpl) {
            return ((ContaUserDetailsImpl) principal).getConta();
        }
        return null;
    }
}
