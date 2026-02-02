import { BookOpen, Star, Users } from "lucide-react";
import mosqueImage from "@/assets/mosque.jpg";

const leaders = [
  {
    title: "Fondateur",
    name: "Cheikh Mahfouz Aïdara",
    description: "Fondateur de la communauté de Darsalam Chérif, guide spirituel et pionnier du développement du village.",
    icon: Star,
  },
  {
    title: "Khalife Actuel",
    name: "Cheikh Sidaty ould Cheikh Abba",
    description: "Guide spirituel de la communauté, poursuivant l'œuvre du fondateur avec sagesse et dévouement.",
    icon: Users,
  },
  {
    title: "Imam Ratib",
    name: "Cheikh Elhadj Mouhamed Limane ould Cheikh hatab Aïdara",
    description: "Responsable des prières et de l'enseignement religieux, pilier spirituel de la communauté.",
    icon: BookOpen,
  },
];

const AboutSection = () => {
  return (
    <section id="apropos" className="section-padding bg-muted">
      <div className="container-custom">
        {/* Section Header */}
        <div className="text-center mb-16">
          <p className="text-secondary uppercase tracking-[0.2em] text-sm mb-3">
            Notre Histoire
          </p>
          <h2 className="font-serif text-3xl md:text-5xl font-bold text-foreground mb-6">
            À Propos de Darsalam Chérif
          </h2>
          <div className="w-24 h-1 bg-primary mx-auto rounded-full" />
        </div>

        {/* Main Content */}
        <div className="grid lg:grid-cols-2 gap-12 items-center mb-20">
          <div className="relative">
            <img
              src={mosqueImage}
              alt="Mosquée de Darsalam Chérif"
              className="rounded-2xl shadow-xl w-full h-[400px] object-cover"
            />
            <div className="absolute -bottom-6 -right-6 bg-primary text-primary-foreground p-6 rounded-xl shadow-lg hidden md:block">
              <p className="font-serif text-3xl font-bold">100+</p>
              <p className="text-sm opacity-90">Années d'histoire</p>
            </div>
          </div>
          
          <div className="space-y-6">
            <h3 className="font-serif text-2xl md:text-3xl font-semibold text-foreground">
              Une communauté ancrée dans la foi et le progrès
            </h3>
            <p className="text-muted-foreground leading-relaxed">
              Darsalam Chérif est un village fondé sur les principes de spiritualité, 
              d'entraide et de développement durable. Notre communauté perpétue les 
              enseignements de nos guides spirituels tout en embrassant les défis modernes.
            </p>
            <p className="text-muted-foreground leading-relaxed">
              À travers l'agriculture, l'élevage et l'éducation de notre jeunesse, 
              nous construisons un avenir prospère pour les générations futures, 
              tout en préservant nos valeurs ancestrales.
            </p>
            <div className="flex gap-6 pt-4">
              <div className="text-center">
                <p className="font-serif text-3xl font-bold text-primary">500+</p>
                <p className="text-sm text-muted-foreground">Familles</p>
              </div>
              <div className="text-center">
                <p className="font-serif text-3xl font-bold text-primary">50+</p>
                <p className="text-sm text-muted-foreground">Hectares cultivés</p>
              </div>
              <div className="text-center">
                <p className="font-serif text-3xl font-bold text-primary">200+</p>
                <p className="text-sm text-muted-foreground">Jeunes formés</p>
              </div>
            </div>
          </div>
        </div>

        {/* Leaders Grid */}
        <div className="grid md:grid-cols-3 gap-8">
          {leaders.map((leader, index) => (
            <div
              key={index}
              className="card-elevated p-8 text-center group"
            >
              <div className="w-16 h-16 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:bg-primary/20 transition-colors">
                <leader.icon className="w-8 h-8 text-primary" />
              </div>
              <p className="text-secondary text-sm uppercase tracking-wider mb-2">
                {leader.title}
              </p>
              <h4 className="font-serif text-xl font-semibold text-foreground mb-3">
                {leader.name}
              </h4>
              <p className="text-muted-foreground text-sm leading-relaxed">
                {leader.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default AboutSection;

