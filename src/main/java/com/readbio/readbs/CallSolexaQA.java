/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author Shaojun
 * @version v 0.0
 */
public class CallSolexaQA {
    private final static Logger LOGGER = Logger.getLogger(CallSolexaQA.class.getName());
    
    private File solexaQAPathFile;
    private File fastqPath;

    //solexaPath, fastq file
    public static void main(String[] args) throws IOException{
        String solexaQAPath = "/scratch/conte/x/xie186/software/SolexaQA_1.12";
        String temFastqPath = "test.fq";
        CallSolexaQA testCallQA = new CallSolexaQA(solexaQAPath, temFastqPath);
    }
    
    public CallSolexaQA(String solexaQAPath, String fastqPath) throws IOException{
        
        this.solexaQAPathFile = new File(solexaQAPath);
        this.fastqPath        = new File(fastqPath);
        
        Runtime rtSelexaQA = Runtime.getRuntime();
        
        LOGGER.info("Call SolexaQA: ");
        String solexaQACMD = solexaQAPath + "/DynamicTrim.pl " + fastqPath;
        Process prSelexaQA = rtSelexaQA.exec(solexaQACMD);
        LOGGER.info("Call SolexaQA: ");

    }
}
