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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaojun
 * @version v 0.0
 */
public final class SolexaQACMD {
    private final static Logger LOGGER = Logger.getLogger(CallSolexaQA.class.getName());
    private String projectDir;
    private String solexaQAExe;

    public SolexaQACMD(String proDir) throws IOException{
        this.setProjectDir(proDir);
        this.solexaQAExe = getSolexaQAPath();
    }
    
    //test
    public String[] getDynamicTrimCMD(String fastqPath, 
                                          String temSampleName, 
                                          String probCutCutoff) throws IOException, InterruptedException{
        //Output directory
        String outDir = getOutDir(temSampleName);
        this.createOutputDir(outDir);
        
        System.out.println("Output directory: " + outDir);
        System.out.println("Project directory: " + projectDir);
        System.out.println("Project directory this.getProjectDir(): " + this.getProjectDir());
        LOGGER.info("Call SolexaQA dynamictrim!");
        //String solexaQACMD = "perl " + dynamicTrimScript + " " + 
        //                      "-d " + outDir + " " + fastqPath;
        String[] dynamicTrimCMD = new String[]{solexaQAExe, "dynamictrim", "-p", probCutCutoff, "-d",outDir, fastqPath};
        LOGGER.info("Command line for dynamictrim: " + Arrays.toString(dynamicTrimCMD));
        return dynamicTrimCMD;
    }   
    
    public String[] getLengthSortPECMD(String fastqPath1, 
                                        String fastqPath2,
                                        String temSampleName, 
                                        String lengthCutoff) {
        String trimmedRead1 = fastqPath1.substring(0, fastqPath1.lastIndexOf(".") +1) + "trimmed.gz";
        String trimmedRead2 = fastqPath2.substring(0, fastqPath2.lastIndexOf(".") +1) + "trimmed.gz";
        String outDir = getOutDir(temSampleName);
        String[] lengthSortCMD = new String[]{solexaQAExe, "lengthsort", trimmedRead1, trimmedRead2, "-l", lengthCutoff, "-d",outDir};
        LOGGER.log(Level.INFO, "Command line for lengthsort: {0}", Arrays.toString(lengthSortCMD));
        return lengthSortCMD;
    }
    
    public String[] getLengthSortPECMD(String fastqPath1, 
                                        String temSampleName, 
                                        String lengthCutoff) {
        String trimmedRead1 = fastqPath1.substring(0, fastqPath1.lastIndexOf(".") +1) + "trimmed.gz";
        String outDir = getOutDir(temSampleName);
        String[] lengthSortCMD = new String[]{solexaQAExe, "lengthsort", trimmedRead1, "-l", lengthCutoff, "-d",outDir};
        LOGGER.log(Level.INFO, "Command line for lengthsort: {0}", Arrays.toString(lengthSortCMD));
        return lengthSortCMD;
    }
    
    public String getOutDir(String temSampleName){
        String outDir = this.getProjectDir() + File.separator + temSampleName;
        return outDir;
    }
    public String getSolexaQAPath() throws IOException{
        //current directory
        String current = new java.io.File( "." ).getCanonicalPath();        
        //solexaQAPath 
        String solexaQAPath = current + File.separator + "com/readbio/readbs/" + OSValidator.getOS() + "/SolexaQA/";
        String solexaQAExePath = solexaQAPath + File.separator + "SolexaQA++";
        return solexaQAExePath;
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
        System.out.println("#End" + ". Code in" + this.getClass().getName());
    }

    private void getProcessErr(Process p) throws IOException{
        System.out.println("ErrorStream" + ". Code in: " + this.getClass().getName());
        InputStream inputStream = p.getErrorStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
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