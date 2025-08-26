package com.devanalyzer.dev_profile_api.github;

// CLASSE : Représente un repository GitHub
public class GitHubRepository {
    
    // PROPRIÉTÉS : Données qu'on veut récupérer de l'API GitHub
    // TODO : Nom du repository
    private String name;
    
    // TODO : Description du repository (peut être null)
    private String description;
    
    // TODO : Langage principal (peut être null)
    private String language;
    
    // TODO : Nombre d'étoiles
    private Integer stargazers_count;
    
    // TODO : URL du repository
    private String html_url;
    
    // CONSTRUCTEURS : Constructeur vide obligatoire pour Spring
    public GitHubRepository() {}
    
    // GETTERS/SETTERS : Spring en a besoin pour la conversion JSON
    // TODO : Créer getter et setter pour name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    // TODO : Créer getter et setter pour description  
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    // TODO : Créer getters/setters pour language, stargazers_count, html_url
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
