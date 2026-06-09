package com.shaoume.service;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        String ext = "";
        String original = file.getOriginalFilename();
        if (original != null && original.contains("."))
            ext = original.substring(original.lastIndexOf("."));

        // Forcer jpg pour les images
        String outputExt = ext.toLowerCase().matches("\\.(jpg|jpeg|png|webp)") ? ".jpg" : ext;
        String filename = UUID.randomUUID().toString() + outputExt;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path outputPath = uploadPath.resolve(filename);

        try {
            // Lire l'image originale
            BufferedImage original_img = ImageIO.read(file.getInputStream());

            if (original_img != null) {
                // Appliquer le watermark texte "Shaoume Global"
                BufferedImage watermarked = applyWatermark(original_img);

                // Sauvegarder avec Thumbnailator (optimisation qualité)
                Thumbnails.of(watermarked)
                    .size(800, 800)
                    .keepAspectRatio(true)
                    .outputQuality(0.85)
                    .outputFormat("jpg")
                    .toFile(outputPath.toFile());
            } else {
                // Fichier non-image, sauvegarder tel quel
                Files.copy(file.getInputStream(), outputPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            // En cas d'erreur, sauvegarder sans watermark
            Files.copy(file.getInputStream(), outputPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return "/uploads/" + filename;
    }

    private BufferedImage applyWatermark(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = result.createGraphics();

        // Activer l'antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Dessiner l'image originale
        g2d.drawImage(image, 0, 0, null);

        // Taille du texte proportionnelle à l'image
        int fontSize = Math.max(14, width / 25);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        String watermarkText = "© Shaoume Global";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(watermarkText);
        int textHeight = fm.getHeight();

        // Position : bas droite avec marge
        int margin = 16;
        int x = width - textWidth - margin;
        int y = height - margin;

        // Fond semi-transparent derrière le texte
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRoundRect(x - 8, y - textHeight + 4, textWidth + 16, textHeight + 4, 8, 8);

        // Texte blanc
        g2d.setColor(new Color(255, 255, 255, 210));
        g2d.drawString(watermarkText, x, y);

        g2d.dispose();
        return result;
    }
}
