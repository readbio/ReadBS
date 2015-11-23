#!/usr/bin/perl -w
use strict;
die usage() if @ARGV==0;
my %chr_coor;
foreach my $meth(@ARGV){
    open METH,$meth or die "$!";
    while(my $line=<METH>){
        next if $line =~ /Bismark/;
	my ($name, $meth_state, $chr, $pos, $meth_state2) = split("\t", $line);
        ${$chr_coor{"$chr\t$pos"}}[0]++;
        ${$chr_coor{"$chr\t$pos"}}[1]++ if $meth_state eq "+";
    } 
    close METH;
}

foreach(sort keys %chr_coor){
    ${$chr_coor{$_}}[1]=0 if !defined ${$chr_coor{$_}}[1];
    my $meth_lev = ${$chr_coor{$_}}[1]*100 / ${$chr_coor{$_}}[0];
    my ($chr,$pos)=split(/\t/,$_);
    print "$chr\t$pos\t${$chr_coor{$_}}[1]\t${$chr_coor{$_}}[0]\t$meth_lev\n";
}

sub usage{
  print <<EOF

  Usage: *.pl  <Bismark methylation caller output [N]>  > [output]

  The output file is a tab-delimited BedGraph file with the following information:
  <Chromosome> <Start Position> <sequenced cytosine> <Depth> <Methylation Percentage>

EOF
    ;
  exit 1;
}

