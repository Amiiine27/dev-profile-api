import React, { useState, useEffect } from "react";
import ApiService, { type UserProfile } from "./services/api";
import LoginPage from "./components/LoginPage";
import Dashboard from "./components/Dashboard";

function App() {
  const [user, setUser] = useState<UserProfile | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  useEffect(() => {
    console.log("useEffect déclenché");
    
    // GESTION BOUCLE : Vérifier si retour depuis OAuth GitHub
    const urlParams = new URLSearchParams(window.location.search);
    const isAuthSuccess = urlParams.get('auth') === 'success';
    
    const checkAuth = async () => {
      console.log("checkAuth appelée");
      try {
        console.log("Avant appel ApiService");
        const userData = await ApiService.getProfile();
        console.log("Données reçues:", userData);

        setUser(userData);
        setIsAuthenticated(true);

      } catch (error) {
        console.log("Erreur dans checkAuth:", error);
        setIsAuthenticated(false);
        setUser(null);
      } finally {
        setIsLoading(false);
      }
    };

    // DÉLAI : Si retour OAuth, attendre établissement session
    if (isAuthSuccess) {
      console.log("Retour OAuth détecté, attente 500ms");
      setTimeout(() => checkAuth(), 500);
    } else {
      checkAuth();
    }
  }, []);

  if (isLoading) {
    return <div>Chargement...</div>;
  }

  const handleLogout = () => {
    window.location.href = 'http://localhost:8080/logout';
  };

  return (
    <div>
      <h1>Dev Profile Analyzer</h1>
      {isAuthenticated ? (
        <Dashboard user={user!} />
        // Dans le JSX authentifié
          
      ) : (
        <LoginPage/>
      )}
    </div>
  );
}

export default App;