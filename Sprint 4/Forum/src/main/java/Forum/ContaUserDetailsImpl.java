package Forum;

import Forum.Conta;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ContaUserDetailsImpl implements UserDetails {
    private Conta conta;
    public ContaUserDetailsImpl(Conta conta){
        this.conta = conta;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return conta.getSenha();
    }

    @Override
    public String getUsername() {
        return conta.getEmail();
    }

    public Conta getConta(){
        return conta;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
