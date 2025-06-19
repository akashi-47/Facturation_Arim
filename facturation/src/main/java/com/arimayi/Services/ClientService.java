package com.arimayi.Services;

import com.arimayi.DTO.createClientDTO;
import com.arimayi.Entities.Client;
import com.arimayi.Repositories.ClientRepository;
import com.arimayi.exceptions.EmailNotValidException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ClientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository clientRepository;
  //pour verifier l'email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public Client mapToEntity(createClientDTO dto) {
        return modelMapper.map(dto, Client.class);
    }

    public Client addClient(createClientDTO dto) {
        if (dto.getEmail() == null || !EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new EmailNotValidException("L'email fourni n'est pas valide.");
        }
        if (clientRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailNotValidException("Cet email est deja utilise.");
        }
        Client client = mapToEntity(dto);
        return clientRepository.save(client);
    }
}
