package Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SenhaUtil {

    private static PasswordEncoder encoder;

    @Autowired
    public SenhaUtil(PasswordEncoder encoder) {
        SenhaUtil.encoder = encoder;
    }

    public static String encoder(String senha) {
        return encoder.encode(senha);
    }
}

