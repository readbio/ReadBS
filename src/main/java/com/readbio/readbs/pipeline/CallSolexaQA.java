/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author Shaojun
 * @version v 0.0
 */
public class CallSolexaQA {
    private final static Logger LOGGER = Logger.getLogger(CallSolexaQA.class.getName());
    private String projectDir;

    public CallSolexaQA(String proDir){
        this.setProjectDir(proDir);
    }
    
    //test
    public void callSolexaqaSingleEndRead(String fastqPath, 
                                          String temSampleName, 
                                          String temLengthCutoff,
                                          String ProbCutCutoff) throws IOException, InterruptedException{
        
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir: " + current);
        
        
        String solexaQAPath = current + File.separator + "com/readbio/readbs/" + OSValidator.getOS() + "/SolexaQA/";
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
        runCommand(solexaQACMD);
    }   
    
    private void runCommand(String[] commondLine) throws IOException, InterruptedException{
        ProcessBuilder ps = new ProcessBuilder(commondLine);
        Process processSelexaQA = ps.start();
        processSelexaQA.waitFor();
        if (processSelexaQA.exitValue() == 0) {
            System.out.println("Command Successful");
        } else {
            System.out.println("Command Failure");
        }
        getProcessOutput(processSelexaQA);
        getProcessInput(processSelexaQA);
        getProcessErr(processSelexaQA);
        LOGGER.info("Call SolexaQA: ");
    }
    
    private void getProcessInput(Process p) throws IOException{
        System.out.println("#InputStream" + ". Code in" + this.getClass().getName());
        InputStream inputStream = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
        System.out.println("#End" + ". Code in" + this.getClass().getName());
    }

    private void getProcessErr(Process p) throws IOException{
        System.out.println("ErrorStream" + ". Code in: " + this.getClass().getName());
        InputStream inputStream = p.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
        System.out.println("#End" + ". Code in: " + this.getClass().getName());
    }
    
    private void getProcessOutput(Process p){
        System.out.println("#OutputStream" + this.getClass().getName());
        OutputStream outputStream = p.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println();
        printStream.flush();
        printStream.close();
        System.out.println("#End" + ". Code in" + this.getClass().getName());
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
