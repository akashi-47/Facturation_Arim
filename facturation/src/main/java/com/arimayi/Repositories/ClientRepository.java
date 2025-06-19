package com.arimayi.Repositories;
import com.arimayi.Entities.Client;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // Methode pour trouver un client par son email
    Optional<Client> findByEmail(String email);

  


    
}
