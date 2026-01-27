import { Facebook, Twitter, Instagram, Youtube, ArrowUp, Lock } from "lucide-react";
import { Link } from "react-router-dom";

const Footer = () => {
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <footer className="bg-foreground text-primary-foreground">
      <div className="container-custom section-padding pb-8">
        <div className="grid md:grid-cols-4 gap-12 mb-12">
          {/* Brand */}
          <div className="md:col-span-2">
            <h3 className="font-serif text-2xl font-bold mb-4">
              Darsalam Chérif
            </h3>
            <p className="text-primary-foreground/70 leading-relaxed mb-6 max-w-md">
              Un village d'histoire, de spiritualité et de développement. 
              Ensemble, nous construisons l'avenir de notre communauté tout 
              en préservant nos valeurs ancestrales.
            </p>
            <div className="flex gap-4">
              <a
                href="#"
                className="w-10 h-10 bg-primary-foreground/10 rounded-lg flex items-center justify-center hover:bg-primary-foreground/20 transition-colors"
                aria-label="Facebook"
              >
                <Facebook className="w-5 h-5" />
              </a>
              <a
                href="#"
                className="w-10 h-10 bg-primary-foreground/10 rounded-lg flex items-center justify-center hover:bg-primary-foreground/20 transition-colors"
                aria-label="Twitter"
              >
                <Twitter className="w-5 h-5" />
              </a>
              <a
                href="#"
                className="w-10 h-10 bg-primary-foreground/10 rounded-lg flex items-center justify-center hover:bg-primary-foreground/20 transition-colors"
                aria-label="Instagram"
              >
                <Instagram className="w-5 h-5" />
              </a>
              <a
                href="#"
                className="w-10 h-10 bg-primary-foreground/10 rounded-lg flex items-center justify-center hover:bg-primary-foreground/20 transition-colors"
                aria-label="YouTube"
              >
                <Youtube className="w-5 h-5" />
              </a>
            </div>
          </div>

          {/* Quick Links */}
          <div>
            <h4 className="font-semibold text-lg mb-4">Navigation</h4>
            <ul className="space-y-3">
              {["Accueil", "À propos", "Projets", "Galerie", "Actualités", "Contact"].map(
                (link) => (
                  <li key={link}>
                    <a
                      href={`#${link.toLowerCase().replace("à ", "a").replace(" ", "")}`}
                      className="text-primary-foreground/70 hover:text-primary-foreground transition-colors"
                    >
                      {link}
                    </a>
                  </li>
                )
              )}
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h4 className="font-semibold text-lg mb-4">Contact</h4>
            <ul className="space-y-3 text-primary-foreground/70">
              <li>Village de Darsalam Chérif</li>
              <li>Région de Ziguinchor, Sénégal</li>
              <li>hatabaidara08@gmail.com</li>
              <li>+221 76 877 77 17</li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-primary-foreground/10 pt-8 flex flex-col md:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-6">
            <p className="text-primary-foreground/60 text-sm text-center md:text-left">
              © {new Date().getFullYear()} Darsalam Chérif. Tous droits réservés.
            </p>
            <Link 
              to="/login" 
              className="flex items-center gap-1 text-primary-foreground/40 hover:text-primary-foreground/60 transition-colors text-xs"
            >
              <Lock className="w-3 h-3" />
              Admin
            </Link>
          </div>
          <button
            onClick={scrollToTop}
            className="flex items-center gap-2 text-primary-foreground/60 hover:text-primary-foreground transition-colors text-sm"
          >
            Retour en haut
            <ArrowUp className="w-4 h-4" />
          </button>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
