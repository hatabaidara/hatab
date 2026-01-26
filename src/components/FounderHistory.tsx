import { BookOpen, Calendar, MapPin, Heart } from "lucide-react";

const FounderHistory = () => {
  return (
    <section className="py-16 bg-background">
      <div className="container-custom">
        {/* Section Header */}
        <div className="text-center mb-12">
          <p className="text-secondary uppercase tracking-[0.2em] text-sm mb-3">
            Notre Fondateur
          </p>
          <h2 className="font-serif text-3xl md:text-4xl font-bold text-foreground mb-6">
            L'Histoire de Cheikh Mahfouz Aïdara
          </h2>
          <div className="w-24 h-1 bg-primary mx-auto rounded-full" />
        </div>

        <div className="grid lg:grid-cols-2 gap-12 items-start">
          {/* Left: Timeline */}
          <div className="space-y-8">
            <div className="relative pl-8 border-l-2 border-primary/30">
              <div className="absolute -left-2 top-0 w-4 h-4 bg-primary rounded-full" />
              <div className="mb-8">
                <div className="flex items-center gap-2 text-primary mb-2">
                  <Calendar className="w-4 h-4" />
                  <span className="text-sm font-medium">Les Origines</span>
                </div>
                <h3 className="font-serif text-xl font-semibold mb-3">Naissance et Formation</h3>
                <p className="text-muted-foreground leading-relaxed">
                  Cheikh Mahfouz Aïdara est né dans une famille de grande tradition religieuse et 
                  spirituelle. Dès son jeune âge, il s'est distingué par sa piété exceptionnelle, 
                  sa soif de connaissance et son dévouement à l'enseignement islamique. Il a 
                  étudié auprès des plus grands maîtres de son époque, acquérant une science 
                  profonde du Coran, du hadith et de la jurisprudence islamique.
                </p>
              </div>

              <div className="absolute -left-2 top-1/3 w-4 h-4 bg-secondary rounded-full" />
              <div className="mb-8">
                <div className="flex items-center gap-2 text-secondary mb-2">
                  <MapPin className="w-4 h-4" />
                  <span className="text-sm font-medium">La Fondation</span>
                </div>
                <h3 className="font-serif text-xl font-semibold mb-3">Création de Darsalam Chérif</h3>
                <p className="text-muted-foreground leading-relaxed">
                  Guidé par une vision spirituelle et un profond désir de créer un espace de paix 
                  et de savoir, Cheikh Mahfouz Aïdara a fondé le village de Darsalam Chérif. 
                  Le nom "Darsalam" signifie "Maison de la Paix", reflétant la volonté du fondateur 
                  de bâtir une communauté où règnent l'harmonie, la foi et l'entraide. Il a choisi 
                  cet emplacement pour y établir un centre d'enseignement religieux et de 
                  développement communautaire.
                </p>
              </div>

              <div className="absolute -left-2 top-2/3 w-4 h-4 bg-primary rounded-full" />
              <div>
                <div className="flex items-center gap-2 text-primary mb-2">
                  <Heart className="w-4 h-4" />
                  <span className="text-sm font-medium">L'Héritage</span>
                </div>
                <h3 className="font-serif text-xl font-semibold mb-3">Un Héritage Vivant</h3>
                <p className="text-muted-foreground leading-relaxed">
                  L'œuvre de Cheikh Mahfouz Aïdara continue de rayonner à travers les générations. 
                  Ses enseignements sur l'importance de l'éducation, de l'agriculture et de 
                  l'unité communautaire restent au cœur de la vie à Darsalam Chérif. Les khalifes 
                  qui lui ont succédé perpétuent sa vision, guidant la communauté avec sagesse 
                  et dévouement.
                </p>
              </div>
            </div>
          </div>

          {/* Right: Values & Legacy */}
          <div className="space-y-6">
            <div className="bg-muted rounded-2xl p-8">
              <div className="flex items-center gap-3 mb-6">
                <BookOpen className="w-8 h-8 text-primary" />
                <h3 className="font-serif text-2xl font-semibold">Ses Enseignements</h3>
              </div>
              <div className="space-y-4">
                <div className="flex items-start gap-4">
                  <div className="w-8 h-8 bg-primary/10 rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                    <span className="text-primary font-bold">1</span>
                  </div>
                  <div>
                    <h4 className="font-semibold mb-1">La Quête du Savoir</h4>
                    <p className="text-sm text-muted-foreground">
                      Il enseignait que la connaissance est le chemin vers Dieu et l'épanouissement 
                      de l'être humain. L'éducation était sa priorité absolue.
                    </p>
                  </div>
                </div>
                <div className="flex items-start gap-4">
                  <div className="w-8 h-8 bg-primary/10 rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                    <span className="text-primary font-bold">2</span>
                  </div>
                  <div>
                    <h4 className="font-semibold mb-1">L'Entraide Communautaire</h4>
                    <p className="text-sm text-muted-foreground">
                      La solidarité entre les membres de la communauté était un pilier fondamental 
                      de sa vision. Chacun devait contribuer au bien-être collectif.
                    </p>
                  </div>
                </div>
                <div className="flex items-start gap-4">
                  <div className="w-8 h-8 bg-primary/10 rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                    <span className="text-primary font-bold">3</span>
                  </div>
                  <div>
                    <h4 className="font-semibold mb-1">Le Travail de la Terre</h4>
                    <p className="text-sm text-muted-foreground">
                      Il valorisait l'agriculture comme une source de subsistance noble et un 
                      moyen d'autonomie pour la communauté.
                    </p>
                  </div>
                </div>
                <div className="flex items-start gap-4">
                  <div className="w-8 h-8 bg-primary/10 rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                    <span className="text-primary font-bold">4</span>
                  </div>
                  <div>
                    <h4 className="font-semibold mb-1">La Paix et la Tolérance</h4>
                    <p className="text-sm text-muted-foreground">
                      Darsalam, "Maison de la Paix", incarne sa philosophie d'harmonie, de respect 
                      mutuel et d'ouverture aux autres.
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <blockquote className="border-l-4 border-primary pl-6 py-4 bg-primary/5 rounded-r-lg">
              <p className="font-serif text-lg italic text-foreground mb-2">
                "La vraie richesse d'une communauté réside dans sa foi, son savoir et son unité."
              </p>
              <cite className="text-sm text-muted-foreground">
                — Cheikh Mahfouz Aïdara, Fondateur de Darsalam Chérif
              </cite>
            </blockquote>
          </div>
        </div>
      </div>
    </section>
  );
};

export default FounderHistory;
