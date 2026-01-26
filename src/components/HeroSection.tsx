import heroImage from "@/assets/hero-village.jpg";

const HeroSection = () => {
  const scrollToSection = (href: string) => {
    const element = document.querySelector(href);
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <section id="accueil" className="relative min-h-screen flex items-center justify-center overflow-hidden">
      {/* Background Image */}
      <div className="absolute inset-0">
        <img
          src={heroImage}
          alt="Village de Darsalam Chérif"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-b from-foreground/70 via-foreground/50 to-foreground/80" />
      </div>

      {/* Content */}
      <div className="relative z-10 container-custom px-4 text-center">
        <div className="max-w-4xl mx-auto animate-fade-in">
          <p className="text-primary-foreground/80 text-sm md:text-base uppercase tracking-[0.3em] mb-4">
            Bienvenue à
          </p>
          <h1 className="font-serif text-4xl md:text-6xl lg:text-7xl font-bold text-primary-foreground mb-6 leading-tight">
            Darsalam Chérif
          </h1>
          <p className="text-primary-foreground/90 text-lg md:text-xl max-w-2xl mx-auto mb-8 leading-relaxed">
            Un village d'histoire, de spiritualité et de développement. 
            Ensemble, construisons l'avenir de notre communauté.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <button
              onClick={() => scrollToSection("#apropos")}
              className="btn-primary text-base"
            >
              Découvrir notre histoire
            </button>
            <button
              onClick={() => scrollToSection("#contact")}
              className="bg-primary-foreground/20 backdrop-blur-sm text-primary-foreground border border-primary-foreground/30 px-6 py-3 rounded-lg font-medium hover:bg-primary-foreground/30 transition-all duration-300"
            >
              Devenir partenaire
            </button>
          </div>
        </div>
      </div>

      {/* Scroll Indicator */}
      <div className="absolute bottom-8 left-1/2 -translate-x-1/2 animate-bounce">
        <div className="w-6 h-10 border-2 border-primary-foreground/50 rounded-full flex justify-center pt-2">
          <div className="w-1.5 h-3 bg-primary-foreground/50 rounded-full" />
        </div>
      </div>
    </section>
  );
};

export default HeroSection;
