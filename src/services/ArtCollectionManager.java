package services;

import models.ArtPiece;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArtCollectionManager {
    private static final String FILE_NAME = "data/art_collection.txt";

    public static void addArtPiece(ArtPiece artPiece) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(artPiece.toString());
        writer.newLine();
        writer.close();
    }

    public static List<ArtPiece> searchArtPiece(String keyword) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        List<ArtPiece> results = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            ArtPiece artPiece = ArtPiece.fromString(line);
            if (artPiece.getTitle().contains(keyword) || artPiece.getArtist().contains(keyword)) {
                results.add(artPiece);
            }
        }
        reader.close();
        return results;
    }

    public static List<ArtPiece> getAllArtPieces() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        List<ArtPiece> artPieces = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            ArtPiece artPiece = ArtPiece.fromString(line);
            artPieces.add(artPiece);
        }
        reader.close();
        return artPieces;
    }
}
