package com.reframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Bridge class to execute Python video upscaling engine
 * and capture real-time progress updates
 */
public class Bridge {
    
    /**
     * Executes the Python reframe_core.py script with the given video path
     * 
     * @param videoPath Path to the video file to process
     * @throws IOException if the process cannot be started or read from
     * @throws InterruptedException if the process is interrupted
     */
    public void processVideo(String videoPath) throws IOException, InterruptedException {
        // Get the absolute path to the Python script
        String pythonScript = System.getProperty("user.dir") + File.separator + "engine" + File.separator + "reframe_core.py";
        
        // Build the process
        ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScript, videoPath);
        processBuilder.redirectErrorStream(true);
        
        // Start the process
        Process process = processBuilder.start();
        
        // Create a thread to read stdout in real-time
        Thread outputReader = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Capture and print PROGRESS lines
                    if (line.startsWith("PROGRESS:")) {
                        System.out.println("Captured: " + line);
                    } else {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading process output: " + e.getMessage());
            }
        });
        
        // Start the output reader thread
        outputReader.start();
        
        // Wait for the process to complete
        int exitCode = process.waitFor();
        
        // Wait for the output reader thread to finish
        outputReader.join();
        
        System.out.println("Process finished with exit code: " + exitCode);
    }
    
    /**
     * Main method for testing the Bridge
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: Bridge <video_path>");
            System.exit(1);
        }
        
        Bridge bridge = new Bridge();
        try {
            bridge.processVideo(args[0]);
        } catch (InterruptedException e) {
            System.err.println("Error processing video: " + e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error processing video: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
