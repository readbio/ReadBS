/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.readbio.readbs.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author Shaojun
 * @version v 0.0
 */
public class BismarkGenoIndexCMD {
    private final static Logger LOGGER = Logger.getLogger(CallSolexaQA.class.getName());

    public BismarkGenoIndexCMD(){
        
    }
    
    
    public String[] getBismarkGenoIndexCMD(String genoDir) throws IOException, InterruptedException{
        
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir: " + current);
        
        //bismarkPath 
        String bismarkPath = current + File.separator + "com/readbio/readbs/scripts/Bismark/";
        String genoIndexExe = bismarkPath + File.separator + "bismark_genome_preparation";
        
       //bowtie2 path
        String bowtieDir = current + File.separator + "com/readbio/readbs/" + OSValidator.getOS() + "/bowtie2/";
        
        LOGGER.info("bismarkPath directory: " + bismarkPath);
        LOGGER.info("Bowtie2 directory: " + bowtieDir);

        LOGGER.info("Call SolexaQA: ");
        
        String[] bismarkGenoIndexCMD = new String[]{genoIndexExe, "--path_to_bowtie", bowtieDir, "--bowtie2", genoDir};
        
        LOGGER.info("Command line for dynamictrim: " + Arrays.toString(bismarkGenoIndexCMD));
        return bismarkGenoIndexCMD;
    }   
}