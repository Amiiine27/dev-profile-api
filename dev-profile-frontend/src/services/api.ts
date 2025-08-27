// IMPORTS : Client HTTP pour appels backend
import axios, { type AxiosResponse } from 'axios';

// CONFIGURATION : Client API avec base URL
const api = axios.create({
  baseURL: '/api',  // Proxy redirige vers localhost:8080/api
  withCredentials: true, // Inclut cookies de session Spring
  timeout: 10000  // Timeout 10s pour éviter les requêtes infinies
});

// INTERFACE : Structure du JSON retourné par GET /api/profile
interface UserProfile {
    username: string;        // user.getAttribute("login") côté Spring
    name: string | null;     // user.getAttribute("name") peut être null
    email: string | null;    // user.getAttribute("email") peut être null
    publicRepos: number;     // user.getAttribute("public_repos")
    followers: number;       // user.getAttribute("followers")
    avatarUrl: string;       // user.getAttribute("avatar_url")
}

interface GitHubRepository {
    name: string;
    description: string;
    language: string;
    stargazers_count: number;
    url: string;
}

interface UserAnalysis{
    username: string;
    activity_score: number;
    totalRepositories: number;
    createdOn: string;
    languages: { [key: string]: number };
    fromCache: boolean;

}

// SERVICE : Appels vers ton backend Spring Boot
class ApiService {
  
  // MÉTHODE : Récupérer le profil utilisateur OAuth
  async getProfile(): Promise<UserProfile> {
    //     ↑ Type de retour garanti
    try {
      const response: AxiosResponse<UserProfile> = await api.get<UserProfile>('/profile');
      //                            ↑ Type de la réponse
      return response.data; // TypeScript sait que c'est un UserProfile
    } catch (error) {
      throw new Error('Erreur lors de la récupération du profil');
    }
  }
  
  // MÉTHODE : Récupérer les repositories
  async getRepositories(): Promise<GitHubRepository[]> {
    // TODO : Appeler GET /api/repos et retourner response.data
    try{
        const response: AxiosResponse<GitHubRepository[]> = await api.get<GitHubRepository[]>('/repos');
        return response.data;
    } catch (e){
        throw new Error("Erreur lors de la récupération des Repos")
    }
  }
  
  // MÉTHODE : Récupérer l'analyse complète
  async getAnalysis(forceRefresh = false): Promise<UserAnalysis> {
    // TODO : Appeler GET /api/analysis?forceRefresh={forceRefresh}
    // Retourner response.data
    try{
    const response: AxiosResponse<UserAnalysis> = await api.get<UserAnalysis>(`/api/analysis`)
    return response.data;
    } catch (e){
        throw new Error("Erreur lors du chargement des Analyses");
    }
    
  }
  
  // MÉTHODE : Récupérer l'historique des analyses
  async getAnalysisHistory(): Promise<UserAnalysis[]> {
    // TODO : Appeler GET /api/analysis/history et retourner response.data
    try{
        const response: AxiosResponse<UserAnalysis[]> = await api.get<UserAnalysis[]>("/analysis/history");
        return response.data;
    } catch (e){
        throw new Error("Erreur lors du chargement de l'historique d'analyses")
    }
  }
}

// EXPORT : Instance unique du service
export default new ApiService();
export type { UserProfile, GitHubRepository, UserAnalysis };