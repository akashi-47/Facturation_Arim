package com.arimayi.Repositories;

import com.arimayi.Entities.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long> {

    List<LigneFacture> findByFactureId(Long factureId);
}
