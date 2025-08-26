package com.devanalyzer.dev_profile_api.github;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAnalysisService {
    
    // MÉTHODE : Analyser la répartition des langages
    public Map<String, Integer> analyzeLanguages(GitHubRepository[] repositories) {
        
        // TODO : Créer une Map pour compter les langages
        Map<String, Integer> compteur = new HashMap<>();

        // TODO : Boucler sur tous les repositories
        for (GitHubRepository repo : repositories){
            // TODO : Récupérer le langage du repo (attention aux null!)
            String langage = repo.getLanguage();
            if(langage!=null){
                // TODO : Si le langage existe et n'est pas vide
                if (langage != null && !langage.isEmpty()) {
                    // getOrDefault gère déjà le cas "pas encore dans la map"
                    compteur.put(langage, compteur.getOrDefault(langage, 0) + 1);
                }
            }
        }   
        // TODO : Retourner la map
        return compteur;
    }
    
    // MÉTHODE : Calculer un score d'activité global
    public int calculateActivityScore(GitHubRepository[] repositories) {
        
        // TODO : Initialiser le score à 0
        int score = 0;
        
        // TODO : Boucler sur tous les repositories
        for (GitHubRepository repo: repositories){
            // TODO : +1 point par repository
            score +=1; 
            
            // TODO : Récupérer le nombre d'étoiles (attention aux null!)
            Integer stars = repo.getStargazers_count();
            if (stars != null) {
                score += Math.min(stars, 10);
            }
            
            // TODO : Si des étoiles existent, ajouter au score (max 10 par repo)
            //        Utiliser Math.min() pour limiter
            score += Math.min(stars, 10);
        }
        
        // TODO : Retourner le score final
        return score;
    }
    // INJECTION : Repository pour sauvegarder
    @Autowired
    private UserAnalysisRepository userAnalysisRepository;

    // NOUVELLE MÉTHODE : Sauvegarder une analyse complète en base de données
    public UserAnalysis saveUserAnalysis(String username, GitHubRepository[] repositories) {
        
        // TODO : Calculer le score d'activité avec calculateActivityScore()
        Integer score = calculateActivityScore(repositories);
        
        // TODO : Analyser les langages avec analyzeLanguages()
        Map<String, Integer> langages = analyzeLanguages(repositories);
        
        // TODO : Convertir la Map des langages en String avec .toString()
        String allLangages = langages.toString();
        
        // TODO : Créer une nouvelle instance UserAnalysis avec ces données
        UserAnalysis userAnalysis = new UserAnalysis(username, score, allLangages);
        
        // TODO : Sauvegarder en base avec userAnalysisRepository.save()
        UserAnalysis savedEntity = userAnalysisRepository.save(userAnalysis);
        
        // TODO : Retourner l'entité sauvegardée
        return savedEntity;
    }
}