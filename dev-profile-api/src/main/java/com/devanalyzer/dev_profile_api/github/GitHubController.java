package com.devanalyzer.dev_profile_api.github;

import java.util.List;
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

    @Autowired
    private UserAnalysisRepository userAnalysisRepository;

    // ENDPOINT : Analyser le profil utilisateur
    @GetMapping("/api/analysis")
    public ResponseEntity<?> userAnalysis(@AuthenticationPrincipal OAuth2User user) {
        
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non connecté");
        }
        
        String username = user.getAttribute("login");
        
        try {
            // TODO : Récupérer les repositories GitHub avec gitHubService.getUserRepositories()
            GitHubRepository[] repos = gitHubService.getUserRepositories(username);
            
            // TODO : Sauvegarder l'analyse avec analysisService.saveUserAnalysis()
            // Cette méthode calcule ET sauvegarde en une fois
            UserAnalysis savedAnalysis = analysisService.saveUserAnalysis(username, repos);

            // AJOUT : Log pour debug
            System.out.println("Analyse sauvegardée avec ID: " + savedAnalysis.getId());

            // AJOUT : Vérification immédiate en base
            List<UserAnalysis> verification = userAnalysisRepository.findByUsername(username);
            System.out.println("Nombre d'analyses trouvées: " + verification.size());
            
            // TODO : Créer un message formaté avec les données de l'analyse sauvegardée
            // Utiliser savedAnalysis.getActivityScore() et savedAnalysis.getLanguagesData()
            String message = String.format(("Hello Repositor !" 
                                            + "<br/>"
                                            + "Nom de code %s"
                                            + "<br/>"
                                            + "Voici ton SCORE D'ACTIVITÉ : %s."
                                            + "<br/>"
                                            + "Ainsi que les differents langages que tu as pu expérimenter : %s "),
                                            username, savedAnalysis.getActivityScore(), savedAnalysis.getLanguagesData().toString());
            
            // TODO : Retourner ResponseEntity.ok() avec le message
            return ResponseEntity.ok().body(message);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'analyse");
        }
    }

    @GetMapping("/api/analysis/history")
    public ResponseEntity<?> analysisHistory(@AuthenticationPrincipal OAuth2User user) {
        
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non connecté");
        }
        
        String username = user.getAttribute("login");
        
        // TODO : Utiliser userAnalysisRepository.findByUsername() pour récupérer l'historique
        List<UserAnalysis> savedAnalysis = userAnalysisRepository.findByUsername(username);
        
        // TODO : Retourner la liste des analyses
        return ResponseEntity.ok().body(savedAnalysis);
    }
}