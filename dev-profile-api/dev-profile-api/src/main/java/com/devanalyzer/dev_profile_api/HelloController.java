package com.devanalyzer.dev_profile_api;

// IMPORTS : Spring Web pour les annotations REST
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devanalyzer.dev_profile_api.github.GitHubService;
import com.devanalyzer.dev_profile_api.github.GitHubRepository;

// IMPORT : Pour récupérer l'utilisateur connecté
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

// IMPORTS : Pour ResponseEntity et codes HTTP
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

// IMPORT : Pour l'injection de dépendance
import org.springframework.beans.factory.annotation.Autowired;

// ANNOTATION : Indique que cette classe est un contrôleur REST
@RestController
public class HelloController {
    
    // ENDPOINT 1 : Page d'accueil sur "/"
    // ANNOTATION : Mappe les requêtes GET sur la racine
    @GetMapping("/")
    public String home() {
        // TODO : Retourne un message de bienvenue qui confirme l'authentification
        return "Hello World";
    }
    
    // ENDPOINT 2 : Test de l'API sur "/api/test"  
    // ANNOTATION : Mappe les requêtes GET sur /api/test
    @GetMapping("/api/test")
    public String apiTest() {
        // TODO : Retourne un message confirmant que l'API fonctionne
        return "API Working Well";
    }

    // ENDPOINT 3 : Afficher les infos de l'utilisateur GitHub connecté
    // ANNOTATION : Mappe les requêtes GET sur /api/profile
    @GetMapping("/api/profile")
    public String userProfile(@AuthenticationPrincipal OAuth2User user) {
    
        // VÉRIFICATION : L'utilisateur est-il connecté via OAuth ?
        if (user == null) {
            // TODO : Retourne un message pour utilisateur non connecté via OAuth
            return "Utilisateur non connecté via oAuth";
        }
        
        // RÉCUPÉRATION : Nom d'utilisateur GitHub
        String username = user.getAttribute("login");
        
        // RÉCUPÉRATION : Nom complet (peut être null)
        String fullName = user.getAttribute("name");
        
        // TODO : Retourne les informations formatées
        return "L'utilisateur : " + username + " s'appelle " + fullName;
    }

    @Autowired
    private GitHubService gitHubService;

    // NOUVEL ENDPOINT : Récupérer les repos d'un utilisateur GitHub
    @GetMapping("/api/repos")
    public ResponseEntity<?> userRepositories(@AuthenticationPrincipal OAuth2User user) {
        
        // VÉRIFICATION : L'utilisateur est-il connecté via OAuth ?
        if (user == null) {
            // TODO : Retourner une réponse HTTP 401 avec message d'erreur
            return ResponseEntity.status(401).body("Utilisateur non connecté via oAuth, veuillez vous connecter pour voir la liste de vos repositories");
        }
        
        
        // RÉCUPÉRATION : Username GitHub de l'utilisateur connecté
        String username = user.getAttribute("login");
        
        // APPEL SERVICE : Récupérer les repos via le service
        try {
            // TODO : Appeler la méthode getUserRepositories du service
            GitHubRepository[] repos = gitHubService.getUserRepositories(username);
            // TODO : Retourner les repos
            return ResponseEntity.ok(repos);
        } catch (Exception e) {
            // TODO : Gestion d'erreur si l'API GitHub ne répond pas
            return ResponseEntity.status(401).body("Erreur lors de la récuperation des repos de l'utilisateur");
        }
    }
}