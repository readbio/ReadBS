/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 *
 * @author Shaojun
 */
public class TestPerlScripts {
    
    public TestPerlScripts(){
    }
    
    public static void listDir(String resDir){
        File folder = new File(resDir);
        File[] listOfFiles = folder.listFiles();
        System.out.println(listOfFiles);
for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        System.out.println("File " + listOfFiles[i].getName());
      } else if (listOfFiles[i].isDirectory()) {
        System.out.println("Directory " + listOfFiles[i].getName());
      }
    }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException{
        System.out.println("Hello");
        String resourceDir = "com/readbio/readbs/";
        //testPerlScripts.listDir(resourceDir);
        
        //com\readbio\readbs\perlScripts\SolexaQA\
        TestPerlScripts testPerlScripts = new TestPerlScripts();
        //testPerlScripts.copyScriptsFromJar();
        testPerlScripts.copyJarFile();

    }
    
    private void copyJarFile() throws URISyntaxException, IOException{
        
        // extract the absolute path of the running jar file
        String jarAbsPath = TestPerlScripts.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        JarFile jar = new JarFile(jarAbsPath);
        
        // get the path of directory of jar file
        String jarDir = new File(".").getAbsolutePath();
        
        
        Enumeration enumer = jar.entries();
        while (enumer.hasMoreElements()) {
            JarEntry file = (JarEntry) enumer.nextElement();
            
            File f = new File(jarDir + File.separator + file.getName());
            f.setExecutable(true);
            if(file.isDirectory()){
                f.mkdir();
                continue;
            }
            if(file.toString().startsWith("com/readbio/readbs/perlScripts")){
                System.out.println("JarEntry: " + file);
                System.out.println("File: " + f);
                System.out.println("FileToString: " + f.toString());
                InputStream input = jar.getInputStream(file);
                FileOutputStream fos = new FileOutputStream(f);
                while(input.available() > 0){
                    fos.write(input.read());
                }
                fos.close();
                input.close();
            }
        }
    }
    
    private void copyScriptsFromJar() throws IOException, InterruptedException{
        InputStream script = ClassLoader.getSystemClassLoader()
              .getResourceAsStream("com/readbio/readbs/perlScripts/SolexaQA/DynamicTrim.pl");
       // OutputStream output = process.getOutputStream();
        String jarPath = new File(".").getAbsolutePath();
        System.out.println("jarPath1: "
                +   jarPath);
        File jarDir = new File(jarPath);
        File temFile = File.createTempFile("DynamicTrim", ".pl", jarDir);
        System.out.println("temFile path: "+temFile.getAbsolutePath());
        OutputStream outStream = new FileOutputStream(temFile);
        
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = script.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        script.close();
        outStream.close();
        temFile.setExecutable(true);
        //temFile.deleteOnExit();
        
        //test DynamicTrim.pl
        Runtime rtSelexaQA = Runtime.getRuntime();
        String fastqPath = jarDir + "\\test.fq";
        
        
        String osSys = System.getProperty("os.name");
        String solexaQACMD = null;
        if(osSys.contains("Linux") || osSys.contains("Mac")){
             solexaQACMD = "perl "+ temFile  + " " + fastqPath;
        }else if (osSys.contains("Windows")){
             solexaQACMD = "perl " + "\"" + temFile  + "\""  + " "+ "\"" + fastqPath + "\"";
            System.out.println(System.getProperty("os.name"));
        }
        
        System.out.println(solexaQACMD);
        
        Process prSelexaQA;
        prSelexaQA = rtSelexaQA.exec(solexaQACMD);
        prSelexaQA.waitFor();
        
        if (prSelexaQA.exitValue() == 0) {
            System.out.println("Command Successful");
        } else {
            InputStream errStream = prSelexaQA.getErrorStream();
            
            System.out.println("Command Failure");
        }
    }
}
