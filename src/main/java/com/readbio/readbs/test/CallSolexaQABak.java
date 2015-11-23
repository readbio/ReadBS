/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.test;

import com.readbio.readbs.pipeline.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author Shaojun
 * @version v 0.0
 */
public class CallSolexaQABak {
    private final static Logger LOGGER = Logger.getLogger(CallSolexaQABak.class.getName());
    private String projectDir;

    public CallSolexaQABak(String proDir){
        this.setProjectDir(proDir);
    }
    
    //test
    public void callSolexaqaSingleEndRead(String fastqPath, 
                                          String temSampleName, 
                                          String temLengthCutoff,
                                          String ProbCutCutoff) throws IOException, InterruptedException{
        
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir: " + current);
        
        String solexaQAPath = current + File.separator + "com/readbio/readbs/perlScripts/SolexaQA";
        String dynamicTrimScript = solexaQAPath + File.separator + "DynamicTrim.pl";
        String outDir = this.getProjectDir() + File.separator + temSampleName;
        
        this.createOutputDir(outDir);
        
        System.out.println("Output directory: " + outDir);
        System.out.println("Project directory: " + projectDir);
        System.out.println("Project directory this.getProjectDir(): " + this.getProjectDir());
        LOGGER.info("Call SolexaQA: ");
        //String solexaQACMD = "perl " + dynamicTrimScript + " " + 
        //                      "-d " + outDir + " " + fastqPath;
        String[] solexaQACMD = new String[]{"perl", dynamicTrimScript, "-d",
                                            outDir, fastqPath};
        System.out.println("Command line for DynamicTrim.pl: " + Arrays.toString(solexaQACMD));
        //runCMD(solexaQACMD);
    }   
    
    private void runCMD(String commondLine) throws IOException, InterruptedException{
        Runtime rtSelexaQA = Runtime.getRuntime();
        Process prSelexaQA = null;
        prSelexaQA = rtSelexaQA.exec(commondLine);
        
        
        prSelexaQA.waitFor() ;
        
        if (prSelexaQA.exitValue() == 0) {
            System.out.println("Command Successful");
        } else {
            System.out.println("Command Failure");
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(prSelexaQA.getErrorStream()), 4096);  
        String line = null;
        while ((line = br.readLine()) != null) {  
            System.err.println("prSelexaQA:"  + line);
        }
        LOGGER.info("Call SolexaQA: ");
    }
    
    private void runCMDUseProcessBuilder(String[] commondLine){
        
    }
    
    private void runCMDUseRuntime(String[] commondLine) throws IOException, InterruptedException{
        Runtime rtSelexaQA = Runtime.getRuntime();
        Process prSelexaQA = null;
        prSelexaQA = rtSelexaQA.exec(commondLine);
        //String toString = prSelexaQA..toString();
        //System.out.println("Process string: " + toString);
        
        prSelexaQA.waitFor() ;
        
        if (prSelexaQA.exitValue() == 0) {
            System.out.println("Command Successful");
        } else {
            System.out.println("Command Failure");
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(prSelexaQA.getErrorStream()), 4096);  
        String line = null;
        while ((line = br.readLine()) != null) {  
            System.err.println("prSelexaQA:"  + line);
        }
        LOGGER.info("Call SolexaQA: ");
    }
    
    private void createOutputDir(String temOutDir){
        File f = new File(temOutDir);
        if(!f.exists() && f.mkdirs()){
            System.out.println("Creating outputdir: " + temOutDir + ". Code in: " + this.getClass().getName());
        }else{
            if(f.isDirectory()){
                System.out.println("Error: File already exists and is not a directory: " + temOutDir +
                                    ". Code in: " + this.getClass().getName() + ". "
                                    + "Line number: " + this.getLineNumber());
            }else{
                System.out.println("File already exists and is a directory: " + temOutDir +
                                    ". Code in: " + this.getClass().getName() + ". "
                                    + "Line number: " + this.getLineNumber());
            }
        }
        
        
    }
    
    public String getProjectDir() {
        return projectDir;
    }

    private void setProjectDir(String temProjectDir) 
    {
        projectDir = temProjectDir;
    }
    
    private int getLineNumber() {
      return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
}
