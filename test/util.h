

/*
*@ just for android framework interface 
*@ list for manager devices(zigbee) 
*@ we need manager zigbee devices 
*/

#ifndef __UTIL_H__
#define __UTIL_H__
#include <stdio.h> 
#include <stdlib.h> 
#include <malloc.h> 
#include <string.h> 

typedef unsigned char uint8   ;
typedef unsigned char uchar   ;

#define YM_START     (0XFE) 
#define YM_END       ('#')
#define YM_READ      (0XF1) 
#define YM_WRITE     (0xF0) 
#define YM_RETURN    (0XF2)



typedef struct ym_data{
uint8 head ;
uint8 mac[8] ;
uint8 type ;
uint8 cmd ;
uint8 val ;
uint8 res[2] ;
uint8 crc ;
uint8 end ;
}ym_data_t ;
 
inline void init_ym_data(ym_data_t* data)
{
	memset(data,0,sizeof(ym_data_t)) ;
	data->head = YM_START ;
	data->end = YM_END ;
}

static unsigned char
a2x(const char c)
{
    switch(c) {
    case '0'...'9':
        return (unsigned char)atoi(&c);
    case 'a'...'f':
        return 0xa + (c-'a');
    case 'A'...'F':  
        return 0xa + (c-'A');  
    default:  
        goto error;  
    }   
error:  
    return 0;  
}  
  

/*
** Mac Format Like BC:52:C6:18:ED:FD:12:23
*/
#define MAC_LEN_IN_BYTE 8   
void COPY_STR2MAC(unsigned char* mac,char* str)
{
    do { 
        for(int i = 0 ; i < MAC_LEN_IN_BYTE; i++){
            mac[i] = (a2x(str[i*3]) << 4) + a2x(str[i*3 + 1]);
        }
    } while(0) ; 

}

uchar get_crc_result_bak(uchar* msg_ptr,uchar len) 
{
	uchar x = 0x0 ;
	uchar xoresult = 0x0 ;
	for(x;x<len;x++,msg_ptr++)
		xoresult = xoresult ^ (*msg_ptr) ;
	return xoresult ; 
}

uchar get_crc_result( uchar *msg_ptr, uchar len )
{
	uchar x;
	uchar xorResult;

	xorResult = 0;

	for ( x = 0; x < len; x++, msg_ptr++ )
		xorResult = xorResult ^ *msg_ptr;

	return ( xorResult );
}


#endif
