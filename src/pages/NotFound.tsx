import { Link } from "react-router-dom";
import { ArrowLeft } from "lucide-react";

const NotFound = () => {
  return (
    <div className="flex min-h-screen items-center justify-center bg-muted">
      <div className="text-center px-4">
        <h1 className="mb-4 font-serif text-4xl font-bold">404</h1>
        <p className="mb-6 text-xl text-muted-foreground">
          Cette page n'existe pas ou a été déplacée.
        </p>
        <Link
          to="/"
          className="inline-flex items-center gap-2 text-primary underline hover:text-primary/90"
        >
          <ArrowLeft className="w-4 h-4" />
          Retour à l'accueil
        </Link>
      </div>
    </div>
  );
};

export default NotFound;
