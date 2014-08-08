#include <stdio.h>
#include <string.h>


int write2file(const char*filename,char *data,int len)
{
	FILE* fd; 

	if(!filename)
		return -1; 
	fd = fopen(filename,"a+");
	if(!fd){
		return -1; 
	}   
	fwrite(data,1,len,fd);
	fclose(fd);
	return 0;
}
