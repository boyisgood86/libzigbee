#include <stdio.h> 
#include <stdlib.h>
#include <string.h> 
#include <malloc.h> 
//#include <utils/Log.h>
#include "serial.h" 
#include "util.h"
#include "device.h" 

#define	DEBUG			 (0)
#define WRITE			 (1)
#define READ			 (1)
#define NO_CRC_READ		 (1)
#define RE_BUFF_LEN		(16)

enum {
	MAC_LEN = 8,
};



Device::Device()
{
	this->fd = -1;

} 
Device::~Device()
{	
	this->fd = -1 ;
}
/*
** if success return this open device fd else error is -1 
*/
int Device::open_tty_device(const char* tty_name )
{	
	this->fd = ngb_Open((char*)tty_name) ;
	return this->fd ;
}

void Device::close_tty_device(void)
{
	ngb_Close(this->fd) ;
}
/*
** return 0 success -1 error 
*/
int Device::set_speed_tty_device(int speed) 
{	
	return ngb_SetSpeed(this->fd,speed) ;
}
/*
** return 0 success -1 error 
*/
int Device::set_parity_tty_device(int parity)
{
	return ngb_SetParity(this->fd,parity) ;
}
/*
** return 0 success -1 error
*/
int Device::set_bits_tty_device(int bits) 
{
	return ngb_SetBits(this->fd,bits) ;
}
/*
**  return 0 success -1 error 
*/
int Device::set_stop_bits(int stopbits) 
{
	return ngb_SetStopBits(this->fd,stopbits) ;
}

/*
** return 0 success -1 error
*/
int Device::set_flow_control_tty_device(int flow)
{
	return ngb_SetFlowControl(this->fd,flow) ;

}
/*
**  if cmd is write return value is data.val if value = -1 false ; 
**  if cmd is read return value is data.val 
*/




int Device::operator_device(const char* mac,char type,char cmd,char val)
{
	unsigned char size = 0;
	unsigned char i = 0;
	unsigned char mac_test[8] = {0};
	if((NULL == mac)||(strlen(mac)<1)) {
		printf("mac address is NULL\n");
		return -2 ;
	}
#if WRITE	
	ym_data_t out_data  ;
	init_ym_data(&out_data) ;
	/*copy mac type val cmd crc to struct ym_data*/
	COPY_STR2MAC(out_data.mac,(char*)mac) 	;
	out_data.type = type ;
	out_data.val = val ;
	out_data.cmd = cmd;
	out_data.crc = get_crc_result((uchar*)&out_data, (sizeof(ym_data_t) - 2)) ;
	if( (size = ngb_Write( this->fd,&out_data,sizeof(ym_data_t) ) ) < 0) {
		printf("write faild !\n");
		return -3 ;
	}

#endif /*WRITE*/

#if NO_CRC_READ
	ym_data_t *in_data = NULL ; //Input data buff. 
	int read_size = -1;
	int j = 0;
	char read_buff[16];
	char js_buff[1] = {0};

	memset(read_buff, 0, 16);

	for(j = 0; j < 16; j++) {
		if( (read_size = ngb_Read(this->fd, js_buff, 1))  < 0) {
			return read_size;
		}
		read_buff[j] = js_buff[0];
		if(read_buff[0] != 0xFE) {
			j = 0;
		}
	}
	if(read_buff[0] != 0xFE || read_buff[15] != 0x23) {
		return -500;
	}
	printf("%s\n ", read_buff);
	in_data = (ym_data_t *)read_buff;

#endif /*NO_CRC_READ*/
	return 0;
}

