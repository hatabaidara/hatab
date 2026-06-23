import { useState } from "react";
import { X } from "lucide-react";
import heroImage from "@/assets/hero-village.jpg";
import agricultureImage from "@/assets/agriculture.jpg";
import elevageImage from "@/assets/elevage.jpg";
import jeunesseImage from "@/assets/jeunesse.jpg";
import mosqueImage from "@/assets/mosque.jpg";

const galleryImages = [
  { src: heroImage, alt: "Vue aérienne du village", category: "Village" },
  { src: mosqueImage, alt: "Mosquée de Darsalam Chérif", category: "Spiritualité" },
  { src: agricultureImage, alt: "Champs agricoles", category: "Agriculture" },
  { src: elevageImage, alt: "Élevage traditionnel", category: "Élevage" },
  { src: jeunesseImage, alt: "Jeunesse du village", category: "Communauté" },
  { src: heroImage, alt: "Paysage du village", category: "Village" },
];

const GallerySection = () => {
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

  return (
    <section id="galerie" className="section-padding bg-muted">
      <div className="container-custom">
        {/* Section Header */}
        <div className="text-center mb-16">
          <p className="text-secondary uppercase tracking-[0.2em] text-sm mb-3">
            Notre Patrimoine
          </p>
          <h2 className="font-serif text-3xl md:text-5xl font-bold text-foreground mb-6">
            Galerie Photos
          </h2>
          <p className="text-muted-foreground max-w-2xl mx-auto">
            Découvrez la beauté de Darsalam Chérif à travers notre collection 
            d'images capturant la vie quotidienne, les événements et nos paysages.
          </p>
        </div>

        {/* Gallery Grid */}
        <div className="grid grid-cols-2 md:grid-cols-3 gap-4 md:gap-6">
          {galleryImages.map((image, index) => (
            <div
              key={index}
              className={`relative overflow-hidden rounded-xl cursor-pointer group ${
                index === 0 ? "md:col-span-2 md:row-span-2" : ""
              }`}
              onClick={() => setSelectedImage(image.src)}
            >
              <img
                src={image.src}
                alt={image.alt}
                className={`w-full object-cover transition-transform duration-500 group-hover:scale-110 ${
                  index === 0 ? "h-64 md:h-full" : "h-48 md:h-64"
                }`}
              />
              <div className="absolute inset-0 bg-foreground/0 group-hover:bg-foreground/40 transition-colors duration-300 flex items-end">
                <div className="p-4 translate-y-full group-hover:translate-y-0 transition-transform duration-300">
                  <span className="text-primary-foreground text-xs bg-primary/80 px-2 py-1 rounded">
                    {image.category}
                  </span>
                  <p className="text-primary-foreground text-sm mt-2 font-medium">
                    {image.alt}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>

      </div>

      {/* Lightbox */}
      {selectedImage && (
        <div
          className="fixed inset-0 bg-foreground/90 z-50 flex items-center justify-center p-4"
          onClick={() => setSelectedImage(null)}
        >
          <button
            className="absolute top-4 right-4 text-primary-foreground/80 hover:text-primary-foreground"
            onClick={() => setSelectedImage(null)}
          >
            <X size={32} />
          </button>
          <img
            src={selectedImage}
            alt="Gallery image"
            className="max-w-full max-h-[90vh] object-contain rounded-lg"
            onClick={(e) => e.stopPropagation()}
          />
        </div>
      )}
    </section>
  );
};

export default GallerySection;
