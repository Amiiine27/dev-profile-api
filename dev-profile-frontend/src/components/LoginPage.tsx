import btn from "../assets/GitHub.webp";

export default function LoginPage() {
  const handleGitHubLogin = () => {
    // TODO : Rediriger vers l'endpoint OAuth de ton backend Spring Boot
    // Pense à l'URL complète : protocol + host + port + path
    // L'endpoint est : /oauth2/authorization/github
    window.location.href = "http://localhost:8080/oauth2/authorization/github";
  };

  return (
    <div className="login-container">
      <h2>Authentification Requise</h2>
      <p>Connectez-vous pour analyser votre profil GitHub</p>

      {/* TODO : Bouton qui appelle handleGitHubLogin au clic */}
      <button onClick={handleGitHubLogin} className="github-login-btn">
        <img src={btn} alt="Se connecter avec GitHub" />
        Se connecter avec GitHub
      </button>

      <div className="alternative">
        <p>Ou utilisez le login temporaire : admin/password</p>
        {/* TODO : Lien vers la page de login Spring Boot classique */}
        <a href="http://localhost:8080/login">Se connecter</a>
      </div>
    </div>
  );
}
