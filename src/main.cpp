


#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <malloc.h> 
#include "device.h"
#include "serial.h"
#include <unistd.h>


#define BUFF_LEN		(16)

int main(int argc,char* argv[])
{ 

	char buff[BUFF_LEN]	 =  {0};
	if(argc <4) {
		printf("Usage: ./smartTest /dev/ttyS3 49 50");
		return -1 ;
	}
	Device* device = new Device() ;
	if(device == NULL) return -1; 
	int fd = device->open_tty_device(argv[1]) ;
	//printf("fd is %d\n",fd) ;
	int value ;
	value = device->set_speed_tty_device(115200);
	//printf("speed set %d \n",value) ;
	value = device->set_bits_tty_device(8) ;
	//printf("set bits %d\n",value) ;
	value = device->set_stop_bits(1) ;
	//printf("set stop bits :%d \n",value) ;
	device->set_parity_tty_device(100) ;
	device->set_flow_control_tty_device(100) ;
//	value = device->operator_device_cmd("00:2c:45:34:cd:ef:ec:ff",*argv[2],*argv[3]) ;
	//printf("operator device %d\n",value) ;
//	value = device->get_device_status() ;
	//printf("get device status %d \n",value) ;
	while(1) {
		device->read_tty_device(fd, buff, BUFF_LEN);
		printf("%s\n", buff);
	}

	device->close_tty_device() ;
	delete device ;
	return 0 ; 

}
