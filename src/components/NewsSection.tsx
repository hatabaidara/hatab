import { useState } from "react";
import { Calendar, ArrowRight } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { scrollToSection } from "@/lib/navigation";

const newsItems = [
  {
    date: "15 Janvier 2026",
    title: "Gamou Annuel de Darsalam Chérif",
    description: "La communauté se prépare pour la célébration annuelle du Gamou, un moment de spiritualité et de rassemblement.",
    category: "Événement spirituel",
  },
  {
    date: "10 Janvier 2026",
    title: "Lancement du programme agricole 2026",
    description: "Début des travaux de la nouvelle saison agricole avec l'introduction de techniques innovantes.",
    category: "Agriculture",
  },
  {
    date: "5 Janvier 2026",
    title: "Formation des jeunes en entrepreneuriat",
    description: "50 jeunes du village participent à une formation intensive en gestion d'entreprise.",
    category: "Jeunesse",
  },
];

const NewsSection = () => {
  const { toast } = useToast();
  const [email, setEmail] = useState("");

  const handleSubscribe = (e: React.FormEvent) => {
    e.preventDefault();

    if (!email.trim()) {
      return;
    }

    toast({
      title: "Inscription enregistrée",
      description: "Merci ! Vous recevrez nos actualités par email.",
    });
    setEmail("");
  };

  return (
    <section id="actualites" className="section-padding bg-background">
      <div className="container-custom">
        {/* Section Header */}
        <div className="text-center mb-16">
          <p className="text-secondary uppercase tracking-[0.2em] text-sm mb-3">
            Restez Informés
          </p>
          <h2 className="font-serif text-3xl md:text-5xl font-bold text-foreground mb-6">
            Actualités & Événements
          </h2>
          <p className="text-muted-foreground max-w-2xl mx-auto">
            Suivez les dernières nouvelles de notre communauté et ne manquez aucun événement important.
          </p>
        </div>

        {/* News Grid */}
        <div className="grid md:grid-cols-3 gap-8">
          {newsItems.map((news, index) => (
            <article
              key={index}
              className="card-elevated p-6 flex flex-col"
            >
              <div className="flex items-center gap-2 text-muted-foreground text-sm mb-3">
                <Calendar className="w-4 h-4" />
                <span>{news.date}</span>
              </div>
              <span className="inline-block bg-secondary/20 text-secondary px-3 py-1 rounded-full text-xs font-medium w-fit mb-4">
                {news.category}
              </span>
              <h3 className="font-serif text-xl font-semibold text-foreground mb-3 line-clamp-2">
                {news.title}
              </h3>
              <p className="text-muted-foreground text-sm leading-relaxed flex-grow">
                {news.description}
              </p>
              <button
                type="button"
                onClick={() => scrollToSection("#contact")}
                className="flex items-center gap-2 text-primary font-medium text-sm mt-4 hover:gap-3 transition-all"
              >
                Lire la suite
                <ArrowRight className="w-4 h-4" />
              </button>
            </article>
          ))}
        </div>

        {/* Subscribe CTA */}
        <div className="mt-16 bg-primary rounded-2xl p-8 md:p-12 text-center">
          <h3 className="font-serif text-2xl md:text-3xl font-bold text-primary-foreground mb-4">
            Restez connecté avec Darsalam Chérif
          </h3>
          <p className="text-primary-foreground/80 mb-6 max-w-xl mx-auto">
            Recevez nos actualités et annonces directement dans votre boîte mail.
          </p>
          <form
            onSubmit={handleSubscribe}
            className="flex flex-col sm:flex-row gap-4 max-w-md mx-auto"
          >
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Votre adresse email"
              required
              className="flex-grow px-4 py-3 rounded-lg bg-primary-foreground/10 border border-primary-foreground/20 text-primary-foreground placeholder:text-primary-foreground/50 focus:outline-none focus:border-primary-foreground/50"
            />
            <button
              type="submit"
              className="bg-primary-foreground text-primary px-6 py-3 rounded-lg font-medium hover:bg-primary-foreground/90 transition-colors whitespace-nowrap"
            >
              S'inscrire
            </button>
          </form>
        </div>
      </div>
    </section>
  );
};

export default NewsSection;
