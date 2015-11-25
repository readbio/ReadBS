# ReadBS
A GUI packages to processing BS-seq data

### Prerequisites
* Perl
* R
* Java

###Features
Java source code "ReadBS-master.zip" can be found. Netbeans IDE was used.

## Introduction
### What is ReadBS? 
Mose bench researcher don't have any skilles of System terminal (Linux, Mac and Windows), Perl and R. ReadBS is developped to help these people analyze BS-seq data. ReadBS is a Java GUI application for analyzing DNA methylome data. ReadBS is a flexible tool for the analysis of BS-seq data and has multiple functions. ReadBS can perform read trimming, read mapping, methylation calling, differential methylation (Under construction). After finishing development, ReadBS could be considered as a BS-seq work bench. 
### How does ReadBS work?
For read trimming, ReadBS invoke SolexaQA to accomplish this task. Then ReadBS can invoke bismark to do read mapping and methylation calling. If we need to compare two methylomes, we can use ReadBS::DMR tool to identify differentially methylated regions. 
## Obtaining ReadBS
## Input files.
Input files can must be gzip-compressed (ending in .gz). 

