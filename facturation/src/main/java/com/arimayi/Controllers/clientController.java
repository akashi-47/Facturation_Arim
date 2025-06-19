package com.arimayi.Controllers;

import com.arimayi.DTO.createClientDTO;
import com.arimayi.Entities.Client;
import com.arimayi.Repositories.ClientRepository;
import com.arimayi.Services.ClientService;
import com.arimayi.exceptions.EmailNotValidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("arimayi/client")
@Tag(name = "Clients", description = "Opérations liees aux clients")
public class clientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    //ca c pour Swagger
    @Operation(
        summary = "Liste tous les clients"
    )
  
    //liste de tous les clients avec les ids
    public List<Client> getListClients() {
        return clientRepository.findAll();
    }
// ajouter un client
    @PostMapping("/add")
    @Operation(
        summary = "Ajouter un nouveau client"
     
    )
 
    public Client addClient(@RequestBody createClientDTO clientDTO) {
        return clientService.addClient(clientDTO);
    }
//retourner un client 
    @GetMapping("/{id}")
    @Operation(
        summary = "Détails d'un client"
    )


    public Client detailsClient(@PathVariable Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id: " + id));
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<String> handleEmailNotValidException(EmailNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
