package ma.project.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        // Obtenez le répertoire de travail de l'application
        String currentWorkingDir = System.getProperty("user.dir");

        // Construisez le chemin complet vers le répertoire d'upload
        String uploadDirPath = currentWorkingDir + File.separator+ "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images" ;

        Path uploadDir = Paths.get(uploadDirPath);

        // Assurez-vous que le répertoire d'upload existe, sinon créez-le
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // Construisez le chemin complet vers le fichier
            Path filePath = uploadDir.resolve(fileName);

            // Copiez le fichier
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }
    }
}
