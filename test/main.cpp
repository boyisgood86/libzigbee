


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

	int ret = -1;
	char buff[BUFF_LEN]	 =  {0};
	char mac[8] = {0xcc, 0xcc, 0xcc, 0xcc,0xcc,0xcc,0xcc,0xcc};
	char type = 0x44;
	char cmd = 0x55;
	char val = 0x66;
	if(argc <2) {
		printf("Usage: ./a.out /dev/ttyUSBx \n");
		return -1 ;
	}
	Device* device = new Device() ;
	if(device == NULL) return -1; 
	int fd = device->open_tty_device(argv[1]) ;
	int value;
	value = device->set_speed_tty_device(115200);
	value = device->set_bits_tty_device(8) ;
	value = device->set_stop_bits(1) ;
	device->set_parity_tty_device(100) ;
	device->set_flow_control_tty_device(100) ;
	device->operator_device(mac,type,cmd,val);
	device->close_tty_device() ;
	delete device ;
	return 0 ; 

}
