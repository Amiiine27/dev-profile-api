package com.devanalyzer.dev_profile_api.github;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnalysisRepository extends JpaRepository<UserAnalysis, Long> {
    
    // MÉTHODE AUTOMATIQUE : Spring génère l'implémentation
    // Pattern de nommage : findBy + NomDuChamp
    List<UserAnalysis> findByUsername(String username);
    
    // MÉTHODE AUTOMATIQUE : Trouver la dernière analyse d'un utilisateur
    // Pattern : findTop1By + NomDuChamp + OrderBy + NomDuChamp + Desc
    Optional<UserAnalysis> findTop1ByUsernameOrderByCreatedAtDesc(String username);
}