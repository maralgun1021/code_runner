package common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    // Enum to define how to handle existing files
    public enum SaveType {
        OVERRIDE,
        FAIL_IF_EXISTS
    }

    /**
     * Simple save file function.
     * Defaults to OVERRIDE if type is not specified.
     */
    public static boolean saveFile(String folderPath, String fileName, String content) {
        // Call the main function with OVERRIDE as default
        return saveFile(folderPath, fileName, content, SaveType.OVERRIDE);
    }

    /**
     * Save file with content to a folder, with specified save type.
     *
     * @param folderPath The folder to save the file in
     * @param fileName   The file name
     * @param content    The content to write
     * @param type       The save type (OVERRIDE or FAIL_IF_EXISTS)
     * @return true if file is saved successfully, false otherwise
     */
    public static boolean saveFile(String folderPath, String fileName, String content, SaveType type) {
        try {
            // Create folder if it doesn't exist
            Path folder = Paths.get(folderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            // Create file path
            Path filePath = folder.resolve(fileName);

            // Handle according to save type
            if (Files.exists(filePath)) {
                if (type == SaveType.FAIL_IF_EXISTS) {
                    System.err.println("File already exists: " + filePath.toAbsolutePath());
                    return false;
                }
                // else OVERRIDE → continue to write
            }

            // Write content to file
            Files.write(filePath, content.getBytes());

            System.out.println("File saved at: " + filePath.toAbsolutePath());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Example usage
    public static void main(String[] args) {
        String folder = "C:/temp/files";
        String fileName = "example.txt";
        String content = "Hello, world!";

        // Simple version → defaults to OVERRIDE
        saveFile(folder, fileName, content);

        // Full version with explicit type
        saveFile(folder, fileName, content, SaveType.FAIL_IF_EXISTS);
    }
}
