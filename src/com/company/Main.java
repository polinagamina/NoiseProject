package com.company;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;

public class Main {
    private static String UPLOAD_FOLDER = "Uploads/";
    private static String NOISE_FOLDER = "Noise/";
    public static void main(String[] args) throws Exception {
        String path = UPLOAD_FOLDER + "BadApple.wav";
        File musicFile = new File(path);
        byte[] bytes = Files.readAllBytes(musicFile.toPath());
        byte[] bytesWithNoise = new byte[bytes.length];
        for (int i=0; i<bytes.length; i++){
            bytesWithNoise[i]= (byte) (bytes[i]+getNoise());
        }
        writeAudioToWavFile(bytesWithNoise,new AudioFormat(44100, 16, 2, true,false),NOISE_FOLDER+"BadApple.wav");
        File noiseFile = new File(NOISE_FOLDER+"BadApple.wav");
        String mimeType = URLConnection.guessContentTypeFromName(noiseFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

    }

    public static double getNoise(){
        java.util.Random r = new java.util.Random();
        double noise = r.nextGaussian() * Math.sqrt(5000);
        return noise;
    }

    public static void writeAudioToWavFile(byte[] data, AudioFormat format, String fn) throws Exception {
        AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(data), format, data.length);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(fn));
    }

}
