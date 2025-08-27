import React, { useState, useEffect } from "react";
import ApiService, {
  type UserProfile,
  type GitHubRepository,
  type UserAnalysis,
} from "../services/api";

interface DashboardProps {
  user: UserProfile;
}

export default function Dashboard({ user }: DashboardProps) {
  // ÉTATS : Données à charger
  const [repositories, setRepositories] = useState<GitHubRepository[] | null>(null);
  const [analysis, setAnalysis] = useState<UserAnalysis | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const loadDashboardData = async () => {
      try {
        // // TODO : Appeler ApiService.getRepositories() et stocker dans setRepositories
        // const repos = await ApiService.getRepositories();
        // if (repos !== null){
        //     setRepositories(repos);
        // }
        // TODO : Appeler ApiService.getAnalysis() et stocker dans setAnalysis
        const analysis = await ApiService.getAnalysis();
        if(analysis !== null ){
            setAnalysis(analysis)
        }
        console.log(analysis, "analyses chargées")
        console.log("Dashboard chargé sans appels API");
      } catch (error) {
        console.error("Erreur chargement dashboard:", error);
      } finally {
        // TODO : Arrêter le loading
        setLoading(false)
      }
    };

    loadDashboardData();
  }, []);

  if (loading) {
    return <div>Chargement du dashboard...</div>;
  }

  return (
    <div>
      <h2>Dashboard de {user.username}</h2>

      <h3>Vous avez {user.publicRepos} repos !</h3>

      {/* TODO : Afficher le score d'analyse si disponible */}
      <h3>Suite a notre analyse votre score s'élève à {analysis?.activity_score}</h3>
    </div>
  );
}
