/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.pipeline;

/**
 *
 * @author xie186
 */
public class BismarkMapping {
    private String fastqPath1 = null;
    private String fastqPath2 = null;
    private String readFormat = null;
    private String readQualFormat = null;

    BismarkMapping(){
    
    }
    
    BismarkMapping(String fastqPath1,
                        String fastqPath2,
                        String readFormat,
                        String readQualFormat){
        
        String[] cmdBismarMap = getBismarkMap(fastqPath1, temSampleName, probCutCutoff);
    }
    
    public String[] getBismarkMap(String fastqPath1, ){
    //bismark -q --phred33-quals --bowtie2 --path_to_bowtie PATH  /scratch/conte/x/xie186/data/ara/bismark_index_bowtie2/ test_R1.fq.tar.gz
    }
}
