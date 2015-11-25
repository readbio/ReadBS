/*
 * This program is written to prepare external scripts (perl or R).
 */
package com.readbio.readbs.pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author Shaojun
 */
public class PrepareExternalFiles {
    
    public PrepareExternalFiles(){
        
    }
    // Copy external scripts or program in to the path of running JAR file
    public void copyJarFile() throws URISyntaxException, IOException{
        
        // extract the absolute path of the running jar file
        String jarAbsPath = PrepareExternalFiles.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        JarFile jar = new JarFile(jarAbsPath);
        
        String jarDirScripts = "com/readbio/readbs/scripts/";
        // get the path of directory of jar file
        String jarDir = new File(".").getAbsolutePath();
        copyInputStreamByByteBuffer(jar, jarDir, jarDirScripts);
        
        String operationSystem = OSValidator.getOS();
        String jarDirExApp = "com/readbio/readbs/" + operationSystem + "/";
        copyInputStreamByByteBuffer(jar, jarDir, jarDirExApp);
        
    }
    public void copyInputStreamByLine(JarFile jar, String jarDir, String dir) throws IOException{
        Enumeration enumer = jar.entries();
        while (enumer.hasMoreElements()) {
            JarEntry file = (JarEntry) enumer.nextElement();            
            File f = new File(jarDir + File.separator + file.getName());
            
            if(file.isDirectory() && !f.exists()){
                f.mkdir();
                continue;
            }
            if(file.toString().startsWith(dir) && !f.exists()){
                
                // Create files
                f.createNewFile();
                
                //jugdge wether the file is executable or not
                if(!f.setExecutable(true, true)){
                    System.out.println("Set Executable failed!!!");
                }
                System.out.println("JarEntry: " + file + "\n"
                                   +"File: " + f 
                                   +"FileToString: " + f.toString());
                InputStream input = jar.getInputStream(file);
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(input));
                PrintWriter out = new PrintWriter(f);
                String str = "";
                while((str = buffRead.readLine()) != null){
                    out.print(str + "\n");
                }
                out.close();
                input.close();
            }
            
            
        }
    }
    
    public void copyInputStreamByByteBuffer(JarFile jar, String jarDir, String dir) throws IOException{
        Enumeration enumer = jar.entries();
        while (enumer.hasMoreElements()) {
            JarEntry file = (JarEntry) enumer.nextElement();            
            File f = new File(jarDir + File.separator + file.getName());
            f.setExecutable(true, true);
            if(file.isDirectory() && !f.exists()){
                f.mkdir();
                continue;
            }
            if(file.toString().startsWith(dir) && !f.exists()){
                
                // Create files
                f.createNewFile();
                
                //jugdge wether the file is executable or not
                if(!f.setExecutable(true, true)){
                    System.out.println("Set Executable failed!!!");
                }
                                
                System.out.println("JarEntry: " + file + "\n"
                                   +"File: " + f 
                                   +"FileToString: " + f.toString());
                FileOutputStream fos = new FileOutputStream(f);
                InputStream input = jar.getInputStream(file);
                byte[] buffer  = new byte[1024];
                int read = 0;
                while((read = input.read(buffer, 0, buffer.length)) != -1){
                    fos.write(buffer, 0, read);
                }
                fos.close();
                input.close();
            }
        }
    }
    
    public void copyInputStreamByByte(JarFile jar, String jarDir, String dir) throws IOException{
        Enumeration enumer = jar.entries();
        while (enumer.hasMoreElements()) {
            JarEntry file = (JarEntry) enumer.nextElement();            
            File f = new File(jarDir + File.separator + file.getName());
            f.setExecutable(true, true);
            if(file.isDirectory() && !f.exists()){
                f.mkdir();
                continue;
            }
            if(file.toString().startsWith(dir) && !f.exists()){
                
                // Create files
                f.createNewFile();
                
                //jugdge wether the file is executable or not
                if(!f.setExecutable(true, true)){
                    System.out.println("Set Executable failed!!!");
                }
                                
                System.out.println("JarEntry: " + file + "\n"
                                   +"File: " + f 
                                   +"FileToString: " + f.toString());
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
    
//    public static void main(String[] args) throws URISyntaxException, IOException{
//        PrepareExternalFiles preFiles = new PrepareExternalFiles();
//        preFiles.copyJarFile();
//    }
}
