import { ArrowRight, Leaf, Users, Tractor } from "lucide-react";
import agricultureImage from "@/assets/agriculture.jpg";
import elevageImage from "@/assets/elevage.jpg";
import jeunesseImage from "@/assets/jeunesse.jpg";

const projects = [
  {
    title: "Agriculture",
    subtitle: "Cultiver l'avenir",
    description: "Développement de cultures vivrières modernes et durables pour assurer l'autosuffisance alimentaire de notre communauté.",
    image: agricultureImage,
    icon: Leaf,
    stats: "50+ hectares",
  },
  {
    title: "Élevage",
    subtitle: "Tradition et modernité",
    description: "Élevage bovin et ovin traditionnel combiné aux techniques modernes pour une production de qualité.",
    image: elevageImage,
    icon: Tractor,
    stats: "300+ têtes",
  },
  {
    title: "Jeunesse",
    subtitle: "La relève de demain",
    description: "Formation professionnelle, alphabétisation et accompagnement des jeunes vers l'emploi et l'entrepreneuriat.",
    image: jeunesseImage,
    icon: Users,
    stats: "200+ jeunes",
  },
];

const ProjectsSection = () => {
  return (
    <section id="projets" className="section-padding bg-background">
      <div className="container-custom">
        {/* Section Header */}
        <div className="text-center mb-16">
          <p className="text-secondary uppercase tracking-[0.2em] text-sm mb-3">
            Nos Initiatives
          </p>
          <h2 className="font-serif text-3xl md:text-5xl font-bold text-foreground mb-6">
            Projets & Développement
          </h2>
          <p className="text-muted-foreground max-w-2xl mx-auto">
            Découvrez nos projets de développement qui transforment Darsalam Chérif 
            en un modèle de réussite communautaire.
          </p>
        </div>

        {/* Projects Grid */}
        <div className="grid md:grid-cols-3 gap-8">
          {projects.map((project, index) => (
            <div
              key={index}
              className="group relative overflow-hidden rounded-2xl bg-card border border-border"
            >
              {/* Image */}
              <div className="relative h-56 overflow-hidden">
                <img
                  src={project.image}
                  alt={project.title}
                  className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-foreground/60 to-transparent" />
                <div className="absolute top-4 left-4 bg-primary text-primary-foreground px-3 py-1 rounded-full text-sm font-medium">
                  {project.stats}
                </div>
              </div>

              {/* Content */}
              <div className="p-6">
                <div className="flex items-center gap-3 mb-3">
                  <div className="w-10 h-10 bg-primary/10 rounded-lg flex items-center justify-center">
                    <project.icon className="w-5 h-5 text-primary" />
                  </div>
                  <div>
                    <h3 className="font-serif text-xl font-semibold text-foreground">
                      {project.title}
                    </h3>
                    <p className="text-secondary text-sm">{project.subtitle}</p>
                  </div>
                </div>
                <p className="text-muted-foreground text-sm leading-relaxed mb-4">
                  {project.description}
                </p>
                <button className="flex items-center gap-2 text-primary font-medium text-sm group-hover:gap-3 transition-all">
                  En savoir plus
                  <ArrowRight className="w-4 h-4" />
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* CTA */}
        <div className="text-center mt-12">
          <a
            href="#contact"
            onClick={(e) => {
              e.preventDefault();
              document.querySelector("#contact")?.scrollIntoView({ behavior: "smooth" });
            }}
            className="btn-outline inline-flex items-center gap-2"
          >
            Soutenir nos projets
            <ArrowRight className="w-4 h-4" />
          </a>
        </div>
      </div>
    </section>
  );
};

export default ProjectsSection;
