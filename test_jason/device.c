#include <stdio.h> 
#include <stdlib.h>
#include <string.h> 
#include <time.h>
#include <malloc.h> 
#include "util.h"

#define WRITE			 (1)
#define READ			 (0)
#define NO_CRC_READ		 (1)
#define RE_BUFF_LEN		(16)

#define ERR_SIZE		(-16)


#define		LOP		(15)
#define		UP		(0x00)


int operator_device(int fd, int op)
{
	unsigned char size = 0;
	unsigned char i = 0;
	unsigned char k = 0;
	char type = 0;
	char val = 0;
	char cmd = 0;
	long start = 0;
	long end = 0;
	char mac1[8];
	unsigned char mac_test[8] = {0};
	struct timeval timeout;
	unsigned char on_buff[16] =  {0xfe,0xcb,0x99,0xb1,0x04,0x00,0x4b,0x12,0x00,0x10,0xf0,0x30,0x00,0x00,0x00,0x23};
	unsigned char off_buff[16] = {0xfe,0xcb,0x99,0xb1,0x04,0x00,0x4b,0x12,0x00,0x10,0xf0,0x00,0x00,0x00,0x00,0x23};

	if( fd < 0 || op < 0) {
		printf(" fd < 0, op = %d\n",op);
		return -1;
	}
	memset(&timeout, 0, sizeof(timeout));
#if WRITE	

#if 0
	ym_data_t out_data  ;
	ptr = (char*)&out_data;
	init_ym_data(&out_data) ;
	/*copy mac type val cmd crc to struct ym_data*/
	for(i = 0; i < 8; i++){
		mac1[i] = 0x55; 
	}

	out_data.type = type ;
	out_data.val = val ;
	out_data.cmd = cmd;
	out_data.crc = 0x00 ;
#endif
#if 1
	//获取当前时间
//	gettimeofday(&timeout, NULL);
//	start  = (timeout.tv_sec*1000) + timeout.tv_usec/1000;
	if(op == 0) {
		if( (size = ngb_Write( fd,off_buff,16) ) < 0) {
			printf("------------>uart write faild !\n");
			return -3 ;
		}
	}else {
		if( (size = ngb_Write( fd,on_buff,16) ) < 0) {
			printf("------------>uart write faild !\n");
			return -3 ;
		}
	}
	//获取写完时间
//	gettimeofday(&timeout, NULL);
//	end = (timeout.tv_sec*1000) + timeout.tv_usec / 1000;

	if(size != 16) {
		printf("-------------->write faild : write size is %d\n", size);
		return ERR_SIZE;
	}

	printf("write data to uart waste of time is %ld\n", (end-start));
#endif/*write*/
//	return (int)(end - start);
#endif /*WRITE*/

#if 1	
#if NO_CRC_READ
//	ym_data_t *in_data=NULL ; //Input data buff. 
	int read_size = -1;
	int j = 0;
	int n = 0;
	int m = 0;
	char read_buff[16];
	char js_buff[1] = {0};

	memset(read_buff, 0, 16);
here:
	gettimeofday(&timeout, NULL);
	start  = (timeout.tv_sec*1000) + timeout.tv_usec/1000;
	for(j = 0; j < 16; j++) {
		if( (read_size = ngb_Read(fd, js_buff, 1))  < 0) {
			printf("------------------> read faild!\n");
			return (-1);
		}
		read_buff[j] = js_buff[0];
		if(read_buff[0] != 0xFE) {
			j = 0;
		}
	}
	gettimeofday(&timeout, NULL);
	end = (timeout.tv_sec*1000) + timeout.tv_usec / 1000;
	printf("read data from uart waste of time is %ld", (end - start));
	if(read_buff[0] != 0xFE || read_buff[15] != 0x23) {
		printf("--------------------> read data error!\n");
		return -500;
	}
	for(i = 1; i < 9; i++) {
		if(read_buff[i] != on_buff[i]) {
			printf("-------------> mac address error\n");
			return -1;
		}
	}
	printf("----------------->mac check right!\n");

	printf("read data is \n");
	for( i = 0; i < 16; i++) {
		printf("%2x ", read_buff[i]);
	}
	printf("\n");

#if 0	
	ptr = read_buff;
	snprintf(debug_buf,sizeof(debug_buf),"read data:%02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x \n",
			read_buff[0],read_buff[1],read_buff[2],read_buff[3],read_buff[4],read_buff[5],read_buff[6],read_buff[7],
			read_buff[8],read_buff[9],read_buff[10],read_buff[11],read_buff[12],read_buff[13],read_buff[14],read_buff[15]);
	DEBUG("==================%s\n",debug_buf);

	/*test mac address*/	
	for(k = 0; k < 8; k++) {
		if(mac_buff[k] != read_buff[k+1]) {
			if(n != 10) {
				n++;
				DEBUG("==================mac is err\n");
				goto here;
			}else{
				DEBUG("==================return -300\n");
				return -300;
			}
		}
	} /*end for*/

	if(UP == read_buff[12]) {
		m++;
		if(m == LOP)
			return -50;
		goto here;
	}

	in_data = (ym_data_t *)read_buff;
	return in_data->val;
#endif /*read*/
	
#endif /*NO_CRC_READ*/


	//	return (size * 100) ; //returen write_size 100 p, raoguo 0~100
#endif
	return 0;
}

#if 0
#define		RET_SIZE	(256)
#define		TMP_SIZE	(4)
#define		MAC_SIZE	(8)
#define		TYPE_LOCA	(9)
#define		CMD_LOCA	(10)
#define		VAL_LOCA	(11)
/*List COO device list*/
char list_buff[RET_SIZE] ;
char * Device::list_device(void)
{
	ym_data_t *in_data=NULL ; //Input data buff. 
	int read_size = -1;
	int j = 0;
	int n = 0;
	unsigned char read_buff[16];
	char temp_buff[TMP_SIZE];
	unsigned char js_buff[1] = {0};
	memset(read_buff, 0, 16);
	memset(list_buff, 0, RET_SIZE);

	for(j = 0; j < 16; j++) {
		if( (read_size = ngb_Read(this->fd, js_buff, 1))  < 0) {
			return (char*)"null";
		}
		read_buff[j] = js_buff[0];
		if(read_buff[0] != 0xFE) {
			j = 0;
		}
	}
	if(read_buff[0] != 0xFE || read_buff[15] != 0x23) {
		return (char*)"null";
	}
	/*test mac address*/	
	for(n = 0; n < MAC_SIZE; n++){
		memset(temp_buff, 0, TMP_SIZE);
		sprintf(temp_buff, "%02X", read_buff[n+1]);
		strcat(list_buff, temp_buff);
		if(n == 7) {
			strcat(list_buff, "_");
			/*type*/			
			memset(temp_buff, 0, TMP_SIZE);
			sprintf(temp_buff, "%d", read_buff[TYPE_LOCA]);
			strcat(list_buff, temp_buff);
			strcat(list_buff, "_");
			/*CMD*/
			memset(temp_buff, 0, TMP_SIZE);
			sprintf(temp_buff, "%d", read_buff[CMD_LOCA]);
			strcat(list_buff, temp_buff);
			strcat(list_buff, "_");
			/*VAL*/
			memset(temp_buff, 0, TMP_SIZE);
			sprintf(temp_buff, "%d", read_buff[VAL_LOCA]);
			strcat(list_buff, temp_buff);
			break;
		} 
		strcat(list_buff, ":");
	}
	return list_buff;

}

#endif

char * list_device_new(const char* mac,char type,char cmd,char val)
{
	unsigned char size = 0;

	if((NULL == mac)||(strlen(mac)<1)) {
		return (char*)"send mac error" ;
	}
	ym_data_t out_data  ;
	init_ym_data(&out_data) ;
	/*copy mac type val cmd crc to struct ym_data*/
	COPY_STR2MAC(out_data.mac,(char*)mac) 	;
	out_data.type = type ;
	out_data.val = val ;
	out_data.cmd = cmd;
	out_data.crc = 0x00 ;


	if( (size = ngb_Write( this->fd,&out_data,sizeof(ym_data_t) ) ) < 0) {
		return (char*)"write faild!" ;
	}

	if(size != 16) {
		return (char*)"<16 bytes";
	}

	char temp_buff[4]; 
	char read_buff[READ_LEN];
	char read_size = -1;
	char i = 0;

	memset(read_buff, 0, (READ_LEN));
	memset(list_buff, 0, sizeof(list_buff));

	read_size = ngb_Read(this->fd, read_buff, (READ_LEN));
	if(read_size < 0) {
		return (char*)"read faild!";
	}

	for(i = 0; i < read_size; i++) {
		memset(temp_buff, 0, sizeof(temp_buff));
		sprintf(temp_buff, "%02x", read_buff[i]);
		strcat(list_buff, temp_buff);
	}

	return (char*)list_buff;
}
