package com.devanalyzer.dev_profile_api.github;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// ANNOTATION : Indique que cette classe est un contrôleur REST
@RestController
public class GitHubController {
    
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

        String avatar = user.getAttribute("profilePicture");
        
        // TODO : Retourne les informations formatées
        return "L'utilisateur : " + username + " s'appelle " + fullName + avatar;
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

    // INJECTION : Service d'analyse (comme pour gitHubService)
    @Autowired
    private UserAnalysisService analysisService;

    // ENDPOINT : Analyser le profil utilisateur
    @GetMapping("/api/analysis")
    public ResponseEntity<?> userAnalysis(@AuthenticationPrincipal OAuth2User user) {
        
        // TODO : Vérifier si l'utilisateur est connecté via OAuth
        if(user==null){
            // Si null, retourner ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("message")
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossible de trouver un utilisateur connecté, réésayez de vous connecter pour analyser vos données");
        }
        
        // TODO : Récupérer le username GitHub depuis l'utilisateur OAuth
        String username = user.getAttribute("login");
        
        try {
            // TODO : Appeler gitHubService.getUserRepositories(username) pour récupérer les repos
            GitHubRepository[] repos = gitHubService.getUserRepositories(username);
            
            // TODO : Appeler analysisService.analyzeLanguages(repos) pour analyser les langages
            Map<String, Integer> nbLangages = analysisService.analyzeLanguages(repos);
            
            // TODO : Appeler analysisService.calculateActivityScore(repos) pour calculer le score
            Integer score = analysisService.calculateActivityScore(repos);
            
            // TODO : Créer un String résultat qui affiche :
            // - Le username
            // - Le score d'activité  
            // - La répartition des langages (utiliser .toString() sur la Map)

            String resultat = String.format(("Bonjour Repositor ! Nom de code : %s | \t Votre score s'élève à %s ! \n Voici la répartition de vos langages :" 
                                + "\n" + "%s"), username, score, nbLangages.toString());
            
            // TODO : Retourner ResponseEntity.ok(resultat)
            return ResponseEntity.ok(resultat);
            
        } catch (Exception e) {
            // TODO : Retourner une erreur 500 avec message d'erreur approprié
            return ResponseEntity.status(500).body("Veuillez nous excuser, une erreur serveur nous empêche d'acceder a vos informations, réessayez s'il-vous-plait !");
        }
    }
}