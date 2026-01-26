import Navbar from "@/components/Navbar";
import HeroSection from "@/components/HeroSection";
import AboutSection from "@/components/AboutSection";
import FounderHistory from "@/components/FounderHistory";
import ProjectsSection from "@/components/ProjectsSection";
import GallerySection from "@/components/GallerySection";
import NewsSection from "@/components/NewsSection";
import ContactSection from "@/components/ContactSection";
import Footer from "@/components/Footer";

const Index = () => {
  return (
    <div className="min-h-screen bg-background">
      <Navbar />
      <HeroSection />
      <AboutSection />
      <FounderHistory />
      <ProjectsSection />
      <GallerySection />
      <NewsSection />
      <ContactSection />
      <Footer />
    </div>
  );
};

export default Index;
