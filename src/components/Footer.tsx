import { Facebook, Twitter, Instagram, Youtube, ArrowUp } from "lucide-react";
import { navLinks, siteContact, socialLinks } from "@/lib/site-config";
import { scrollToSection } from "@/lib/navigation";

const socialIcons = [
  { key: "facebook" as const, Icon: Facebook, label: "Facebook" },
  { key: "twitter" as const, Icon: Twitter, label: "Twitter" },
  { key: "instagram" as const, Icon: Instagram, label: "Instagram" },
  { key: "youtube" as const, Icon: Youtube, label: "YouTube" },
];

const Footer = () => {
  const activeSocialLinks = socialIcons.filter(({ key }) => socialLinks[key]);

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
            {activeSocialLinks.length > 0 && (
              <div className="flex gap-4">
                {activeSocialLinks.map(({ key, Icon, label }) => (
                  <a
                    key={key}
                    href={socialLinks[key]}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="w-10 h-10 bg-primary-foreground/10 rounded-lg flex items-center justify-center hover:bg-primary-foreground/20 transition-colors"
                    aria-label={label}
                  >
                    <Icon className="w-5 h-5" />
                  </a>
                ))}
              </div>
            )}
          </div>

          {/* Quick Links */}
          <div>
            <h4 className="font-semibold text-lg mb-4">Navigation</h4>
            <ul className="space-y-3">
              {navLinks.map((link) => (
                <li key={link.name}>
                  <a
                    href={link.href}
                    onClick={(e) => {
                      e.preventDefault();
                      scrollToSection(link.href);
                    }}
                    className="text-primary-foreground/70 hover:text-primary-foreground transition-colors"
                  >
                    {link.name}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h4 className="font-semibold text-lg mb-4">Contact</h4>
            <ul className="space-y-3 text-primary-foreground/70">
              <li>{siteContact.address.line1}</li>
              <li>{siteContact.address.line2}</li>
              <li>
                <a
                  href={`mailto:${siteContact.email}`}
                  className="hover:text-primary-foreground transition-colors"
                >
                  {siteContact.email}
                </a>
              </li>
              <li>
                <a
                  href={`tel:${siteContact.phone.replace(/\s/g, "")}`}
                  className="hover:text-primary-foreground transition-colors"
                >
                  {siteContact.phone}
                </a>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-primary-foreground/10 pt-8 flex flex-col md:flex-row items-center justify-between gap-4">
          <p className="text-primary-foreground/60 text-sm text-center md:text-left">
            © {new Date().getFullYear()} Darsalam Chérif. Tous droits réservés.
          </p>
          <button
            type="button"
            onClick={() => scrollToSection("#accueil")}
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
