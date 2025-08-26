package com.devanalyzer.dev_profile_api.github;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_analyses")
public class UserAnalysis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(name = "activity_score")
    private Integer activityScore;
    
    @Column(name = "languages_data", length = 1000)
    private String languagesData; // JSON en String
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // TODO : Constructeur vide (obligatoire pour JPA)
    public UserAnalysis(){}
    
    // TODO : Constructeur avec paramètres (username, activityScore, languagesData)
    public UserAnalysis(String username, Integer activityScore, String languagesData){
        this.username = username;
        this.activityScore = activityScore;
        this.languagesData = languagesData;
        // Initialiser createdAt avec LocalDateTime.now()
        this.createdAt = LocalDateTime.now();
    }
    
    
    // TODO : Getters et setters pour tous les champs
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(Integer activityScore) {
        this.activityScore = activityScore;
    }

    public String getLanguagesData() {
        return languagesData;
    }

    public void setLanguagesData(String languagesData) {
        this.languagesData = languagesData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}

    