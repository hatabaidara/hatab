import { useState } from "react";
import { MapPin, Phone, Mail, Send, Heart } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { siteContact } from "@/lib/site-config";
import { scrollToSection } from "@/lib/navigation";

const ContactSection = () => {
  const { toast } = useToast();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    subject: "",
    message: "",
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    
    // Simulate form submission
    await new Promise((resolve) => setTimeout(resolve, 1000));
    
    toast({
      title: "Message envoyé !",
      description: "Nous vous répondrons dans les plus brefs délais.",
    });
    
    setFormData({ name: "", email: "", subject: "", message: "" });
    setIsSubmitting(false);
  };

  return (
    <section id="contact" className="section-padding bg-muted">
      <div className="container-custom">
        {/* Section Header */}
        <div className="text-center mb-16">
          <p className="text-secondary uppercase tracking-[0.2em] text-sm mb-3">
            Contactez-Nous
          </p>
          <h2 className="font-serif text-3xl md:text-5xl font-bold text-foreground mb-6">
            Partenariat & Soutien
          </h2>
          <p className="text-muted-foreground max-w-2xl mx-auto">
            Intéressé par nos projets ? Contactez-nous pour devenir partenaire 
            ou soutenir le développement de Darsalam Chérif.
          </p>
        </div>

        <div className="grid lg:grid-cols-5 gap-12">
          {/* Contact Info */}
          <div className="lg:col-span-2 space-y-8">
            <div>
              <h3 className="font-serif text-2xl font-semibold text-foreground mb-6">
                Restons en contact
              </h3>
              <p className="text-muted-foreground mb-8">
                Que vous soyez une ONG, un partenaire potentiel, ou un membre de la diaspora 
                souhaitant contribuer, nous sommes à votre écoute.
              </p>
            </div>

            <div className="space-y-6">
              <div className="flex items-start gap-4">
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center flex-shrink-0">
                  <MapPin className="w-5 h-5 text-primary" />
                </div>
                <div>
                  <h4 className="font-medium text-foreground mb-1">Adresse</h4>
                  <p className="text-muted-foreground text-sm">
                    {siteContact.address.line1}<br />
                    {siteContact.address.line2}
                  </p>
                </div>
              </div>

              <div className="flex items-start gap-4">
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center flex-shrink-0">
                  <Phone className="w-5 h-5 text-primary" />
                </div>
                <div>
                  <h4 className="font-medium text-foreground mb-1">Téléphone</h4>
                  <a
                    href={`tel:${siteContact.phone.replace(/\s/g, "")}`}
                    className="text-muted-foreground text-sm hover:text-primary transition-colors"
                  >
                    {siteContact.phone}
                  </a>
                </div>
              </div>

              <div className="flex items-start gap-4">
                <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center flex-shrink-0">
                  <Mail className="w-5 h-5 text-primary" />
                </div>
                <div>
                  <h4 className="font-medium text-foreground mb-1">Email</h4>
                  <a
                    href={`mailto:${siteContact.email}`}
                    className="text-muted-foreground text-sm hover:text-primary transition-colors"
                  >
                    {siteContact.email}
                  </a>
                </div>
              </div>
            </div>

            {/* Support CTA */}
            <div className="bg-primary/5 border border-primary/20 rounded-xl p-6">
              <div className="flex items-center gap-3 mb-3">
                <Heart className="w-5 h-5 text-primary" />
                <h4 className="font-medium text-foreground">Faire un don</h4>
              </div>
              <p className="text-muted-foreground text-sm mb-4">
                Soutenez nos projets de développement agricole, éducatif et communautaire.
              </p>
              <button
                type="button"
                onClick={() => scrollToSection("#contact")}
                className="btn-primary w-full text-sm"
              >
                Contribuer maintenant
              </button>
            </div>
          </div>

          {/* Contact Form */}
          <div className="lg:col-span-3">
            <form onSubmit={handleSubmit} className="card-elevated p-8">
              <h3 className="font-serif text-xl font-semibold text-foreground mb-6">
                Envoyez-nous un message
              </h3>
              
              <div className="grid sm:grid-cols-2 gap-6 mb-6">
                <div>
                  <label htmlFor="name" className="block text-sm font-medium text-foreground mb-2">
                    Nom complet *
                  </label>
                  <input
                    type="text"
                    id="name"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                    className="w-full px-4 py-3 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-colors"
                    placeholder="Votre nom"
                  />
                </div>
                <div>
                  <label htmlFor="email" className="block text-sm font-medium text-foreground mb-2">
                    Email *
                  </label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                    className="w-full px-4 py-3 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-colors"
                    placeholder="votre@email.com"
                  />
                </div>
              </div>

              <div className="mb-6">
                <label htmlFor="subject" className="block text-sm font-medium text-foreground mb-2">
                  Sujet *
                </label>
                <select
                  id="subject"
                  name="subject"
                  value={formData.subject}
                  onChange={handleChange}
                  required
                  className="w-full px-4 py-3 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-colors"
                >
                  <option value="">Sélectionnez un sujet</option>
                  <option value="partenariat">Demande de partenariat</option>
                  <option value="financement">Proposition de financement</option>
                  <option value="visite">Organiser une visite</option>
                  <option value="information">Demande d'information</option>
                  <option value="autre">Autre</option>
                </select>
              </div>

              <div className="mb-6">
                <label htmlFor="message" className="block text-sm font-medium text-foreground mb-2">
                  Message *
                </label>
                <textarea
                  id="message"
                  name="message"
                  value={formData.message}
                  onChange={handleChange}
                  required
                  rows={5}
                  className="w-full px-4 py-3 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-colors resize-none"
                  placeholder="Décrivez votre demande..."
                />
              </div>

              <button
                type="submit"
                disabled={isSubmitting}
                className="btn-primary w-full flex items-center justify-center gap-2 disabled:opacity-70"
              >
                {isSubmitting ? (
                  "Envoi en cours..."
                ) : (
                  <>
                    Envoyer le message
                    <Send className="w-4 h-4" />
                  </>
                )}
              </button>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ContactSection;
