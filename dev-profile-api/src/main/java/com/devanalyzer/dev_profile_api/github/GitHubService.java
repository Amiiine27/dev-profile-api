package com.devanalyzer.dev_profile_api.github;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {
    
    private final RestTemplate restTemplate;
    
    public GitHubService() {
        this.restTemplate = new RestTemplate();
    }
    
    // MÉTHODE MODIFIÉE : Retourne maintenant un array d'objets
    public GitHubRepository[] getUserRepositories(String username) {
        
        String url = "https://api.github.com/users/" + username + "/repos";
        
        // CONVERSION : JSON → Array de GitHubRepository
        // TODO : Changer String.class en GitHubRepository[].class
        GitHubRepository[] repositories = restTemplate.getForObject(url, GitHubRepository[].class);
        
        // TODO : Retourner l'array de repositories
        return repositories;
    }
}