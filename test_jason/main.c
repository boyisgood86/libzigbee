#include <stdlib.h> 
#include <time.h>
#include <string.h> 
#include <malloc.h> 
#include <unistd.h>
#include "util.h"


#define BUFF_LEN		(16)

int main(int argc,char* argv[])
{ 
	int fd = -1;
	long time = 0;
	long start = 0;
	long end = 0;
	int i = 20;
	struct timeval timeout;
	int op = atoi(argv[2]);

	if(argc < 3) {
		printf("Usage : a.out /dev/ttyS4 0\n");
		return 0;
	}

	fd = ngb_Open(argv[1]) ;
	printf("open device ...\n");
	if(fd < 0) {
		printf("fd  = %d\n", fd);
		return -1;
	}
	int value ;
	value = ngb_SetSpeed(fd, 115200);
	value = ngb_SetBits(fd, 8) ;
	if(value < 0) {
		printf("value = %d\n", value);
		return -1;
	}
	value = ngb_SetStopBits(fd, 1) ;
	ngb_SetParity(fd, 100) ;
	ngb_SetFlowControl(fd, 100) ;

//	while(i > 0) {
		i--;
		printf("+++++++++++++++++++++++++++++++++++++++++++++++\n");
		memset(&timeout, 0, sizeof(timeout));
		gettimeofday(&timeout, NULL);
		start  = (timeout.tv_sec*1000) + timeout.tv_usec/1000;

		time = operator_device(fd, op);

		if(time < 0) {
			printf("error...\n");
//			return -1;
		}
		gettimeofday(&timeout, NULL);
		end = (timeout.tv_sec*1000) + timeout.tv_usec / 1000;
		printf(" operator funciton waste time is %ld\n", (end-start));
		printf("-----------------------------------------------\n");
//	}
	return 0 ; 

}
