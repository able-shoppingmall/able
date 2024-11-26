package com.sparta.able.security;

import com.sparta.able.entity.Owner;
import com.sparta.able.repository.OwnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OwnerDetailsServiceImpl implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public OwnerDetailsServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + name));


        return new OwnerDetailsImpl(owner);
    }
}
