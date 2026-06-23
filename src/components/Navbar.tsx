import { useState } from "react";
import { Menu, X } from "lucide-react";
import { navLinks } from "@/lib/site-config";
import { scrollToSection } from "@/lib/navigation";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  const handleNavClick = (href: string) => {
    scrollToSection(href);
    setIsOpen(false);
  };

  return (
    <nav className="fixed top-0 left-0 right-0 z-50 bg-background/95 backdrop-blur-md border-b border-border">
      <div className="container-custom">
        <div className="flex items-center justify-between h-16 md:h-20 px-4">
          {/* Logo */}
          <a 
            href="#accueil" 
            onClick={(e) => { e.preventDefault(); handleNavClick("#accueil"); }}
            className="font-serif text-xl md:text-2xl font-bold text-primary"
          >
            Darsalam Chérif
          </a>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-8">
            {navLinks.map((link) => (
              <a
                key={link.name}
                href={link.href}
                onClick={(e) => { e.preventDefault(); handleNavClick(link.href); }}
                className="text-sm font-medium text-foreground/80 hover:text-primary transition-colors link-underline"
              >
                {link.name}
              </a>
            ))}
          </div>

          {/* CTA Button */}
          <a
            href="#contact"
            onClick={(e) => { e.preventDefault(); handleNavClick("#contact"); }}
            className="hidden md:inline-flex btn-primary text-sm"
          >
            Nous Soutenir
          </a>

          {/* Mobile Menu Button */}
          <button
            className="md:hidden p-2 text-foreground"
            onClick={() => setIsOpen(!isOpen)}
            aria-label="Ouvrir le menu"
          >
            {isOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isOpen && (
          <div className="md:hidden bg-background border-t border-border animate-slide-up">
            <div className="px-4 py-4 space-y-3">
              {navLinks.map((link) => (
                <a
                  key={link.name}
                  href={link.href}
                  onClick={(e) => { e.preventDefault(); handleNavClick(link.href); }}
                  className="block py-2 text-foreground/80 hover:text-primary transition-colors"
                >
                  {link.name}
                </a>
              ))}
              <a
                href="#contact"
                onClick={(e) => { e.preventDefault(); handleNavClick("#contact"); }}
                className="block btn-primary text-center mt-4"
              >
                Nous Soutenir
              </a>
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
